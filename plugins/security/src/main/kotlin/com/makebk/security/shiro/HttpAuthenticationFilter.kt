package com.makebk.security.shiro

import cn.hutool.core.collection.CollUtil
import cn.hutool.core.util.StrUtil
import cn.hutool.json.JSONUtil
import cn.hutool.log.LogFactory
import com.auth0.jwt.exceptions.SignatureVerificationException
import com.auth0.jwt.exceptions.TokenExpiredException
import com.makebk.common.constant.SecurityConstants
import com.makebk.common.dto.resp.RespDto
import com.makebk.common.handler.UserContextHandler
import com.makebk.common.permission.JwtProperties
import com.makebk.common.vo.IUser
import com.makebk.security.utils.JwtUtil
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter
import org.redisson.api.RedissonClient
import org.springframework.http.HttpStatus
import java.io.IOException
import java.io.PrintWriter
import java.time.Duration
import java.util.concurrent.TimeUnit


class HttpAuthenticationFilter(
    val jwtUtil: JwtUtil,
    val jwtProperties: JwtProperties,
    var redissonClient: RedissonClient,
) : BasicHttpAuthenticationFilter() {

    /** logger */
    private val logger = LogFactory.get(javaClass)

    /**
     * 判断是否登录
     *
     * @param request
     * @param response
     * @return
     */
    override fun isLoginAttempt(request: ServletRequest, response: ServletResponse?): Boolean {
        val req: HttpServletRequest = request as HttpServletRequest
        val authorization = req.getHeader(jwtProperties.tokenHeader)
        return authorization != null
    }

    /**
     * 登录验证
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    override fun executeLogin(request: ServletRequest, response: ServletResponse): Boolean {
        val req: HttpServletRequest = request as HttpServletRequest
        val authorization = req.getHeader(jwtProperties.tokenHeader)

        val authorizationInfos = StrUtil.splitTrim(authorization, StrUtil.SPACE)
        var token: JwtToken? = null
        if (CollUtil.size(authorizationInfos) == 2 && StrUtil.equals(
                authorizationInfos[0],
                jwtProperties.bearerHeader
            )
        ) {
            token = JwtToken(authorizationInfos[1])
        }
        getSubject(request, response).login(token)
        val credentials = token?.credentials
        val userid = jwtUtil.getClaim(credentials, SecurityConstants.JWT_KEY_USER_ID)
        val username = jwtUtil.getClaim(credentials, SecurityConstants.JWT_KEY_USER_NAME)
        val type = jwtUtil.getClaim(credentials, SecurityConstants.JWT_KEY_USER_TYPE) ?: SecurityConstants.SALT
        val user = IUser(userid?.toInt(), token?.principal, username, type)
        user.groupIds = jwtUtil.getClaim(credentials, SecurityConstants.JWT_KEY_GROUP_ID)
        user.groupIds =
            if( user.groupIds == "null"){
                "0"
            }else{
                user.groupIds
            }
        UserContextHandler(user)
        val platformHeader = req.getHeader(jwtProperties.platformHeader)
        val tokenKey: String = SecurityConstants.TOKEN + "$platformHeader:$type:" + userid
        val tokenCache = redissonClient.getBucket<String>(tokenKey)
        if (!tokenCache.isExists) {
            throw TokenExpiredException("登录认证信息已过期,请重新登录", null)
        } else {
            if (authorization != tokenCache.get()) {
                throw TokenExpiredException("登录认证信息已过期,请重新登录", null)
            }
        }
        refreshTokenIfNeed(user, authorizationInfos[1], response, platformHeader)
        return true
    }


    /**
     * 检查是否需要,若需要则校验时间戳，刷新Token，并更新时间戳
     *
     * @param user
     * @param authorization
     * @param response
     * @return
     */
    private fun refreshTokenIfNeed(
        user: IUser,
        authorization: String,
        response: ServletResponse,
        platformHeader: String
    ): Boolean {
        val currentTimeMillis = System.currentTimeMillis()
        //检查刷新规则
        if (refreshCheck(authorization, currentTimeMillis)) {
            val lockName = SecurityConstants.PREFIX_SHIRO_REFRESH_CHECK + user.id
            val lock = redissonClient.getLock(lockName)
            if (lock.tryLock(SecurityConstants.ExpireTime.THIRTY_SEC, TimeUnit.SECONDS)) {
                val refreshTokenKey =
                    SecurityConstants.PREFIX_SHIRO_REFRESH_TOKEN + "$platformHeader:${user.type}:" + user.id
                val refreshTokenCache = redissonClient.getBucket<String>(refreshTokenKey)
                if (refreshTokenCache.isExists) {
                    //检查redis中的时间戳与token的时间戳是否一致
                    val refreshToken = refreshTokenCache.get()
                    if (!refreshCheck(refreshToken, System.currentTimeMillis())) {
                        throw TokenExpiredException("您的的令牌无效,请重新登录", null)
                    }
                }
                logger.debug(java.lang.String.format("为账户%s颁发新的令牌", user.username))
                val newToken = jwtUtil.sign(jwtProperties, user, jwtProperties.expire)

                //更新缓存中的token时间戳
                val time = jwtProperties.expire * 60L
                val tokenKey = SecurityConstants.TOKEN + "${jwtProperties.platform}:${user.type}:" + user.id
                val tokenCache = redissonClient.getBucket<String>(tokenKey)
                tokenCache.set(newToken)
                tokenCache.expire(Duration.ofMinutes(time))

                val httpServletResponse: HttpServletResponse = response as HttpServletResponse
                httpServletResponse.setStatus(HttpServletResponse.SC_OK)
                httpServletResponse.setHeader(jwtProperties.tokenHeader, newToken)
                httpServletResponse.setHeader("Access-Control-Expose-Headers", jwtProperties.tokenHeader)
            }
            lock.unlock()
        }
        return true
    }

    /**
     * 检查是否需要更新Token
     *
     * @param authorization
     * @param currentTimeMillis
     * @return
     */
    private fun refreshCheck(authorization: String, currentTimeMillis: Long): Boolean {
        val tokenMillis = jwtUtil.getClaim(authorization, SecurityConstants.CURRENT_TIME_MILLIS)
        if (tokenMillis != null) {
            return currentTimeMillis - tokenMillis.toLong() > jwtProperties.refreshCheckTime.times(60) * 1000L
        }
        return false
    }

    /**
     * 是否允许访问
     *
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    override fun isAccessAllowed(request: ServletRequest, response: ServletResponse, mappedValue: Any?): Boolean {
        if (isLoginAttempt(request, response)) {
            try {
                executeLogin(request, response)
            } catch (e: Exception) {
                var msg = e.message
                val throwable = e.cause
                if (throwable != null && throwable is SignatureVerificationException) {
                    msg = "Token或者密钥不正确"
                } else if (throwable != null && throwable is TokenExpiredException) {
                    msg = "Token已过期"
                } else {
                    if (throwable != null) {
                        msg = throwable.message
                    }
                }
                response401(response, msg)
                return false
            }
        }
        return true
    }


    /**
     * todo 待修改
     *
     * 401非法请求
     *
     * @param resp
     */
    private fun response401(resp: ServletResponse, msg: String?) {
        val httpServletResponse: HttpServletResponse = resp as HttpServletResponse
        httpServletResponse.status = HttpStatus.OK.value()
//        httpServletResponse.status = HttpStatus.UNAUTHORIZED.value()
        httpServletResponse.characterEncoding = "UTF-8"
        httpServletResponse.contentType = "application/json; charset=utf-8"
        var out: PrintWriter? = null
        try {
            out = httpServletResponse.writer
            out.append(JSONUtil.toJsonPrettyStr(RespDto<String>(401, msg)))
        } catch (e: IOException) {
            logger.error("返回Response信息出现IOException异常:" + e.message)
        } finally {
            out?.close()
        }
    }

    /**
     * 重写 onAccessDenied 方法，避免父类中调用再次executeLogin
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    override fun onAccessDenied(request: ServletRequest?, response: ServletResponse?): Boolean {
        sendChallenge(request, response)
        return false
    }
}