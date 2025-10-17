package com.makebk.security.shiro

import cn.hutool.core.util.StrUtil
import cn.hutool.json.JSONUtil
import cn.hutool.log.LogFactory
import com.makebk.common.constant.SecurityConstants
import com.makebk.common.dto.resp.RespDto
import com.makebk.common.handler.UserContextHandler
import com.makebk.common.permission.JwtProperties
import com.makebk.security.utils.JwtUtil
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import org.apache.shiro.web.filter.authc.LogoutFilter
import org.redisson.api.RedissonClient
import java.io.IOException
import java.io.PrintWriter


class SystemLogoutFilter(
    val jwtProperties: JwtProperties,
    val redissonClient: RedissonClient,
    val jwtUtil: JwtUtil,
) : LogoutFilter() {

    var log = LogFactory.get(this::class.java)

    @Throws(Exception::class)
    override fun preHandle(request: ServletRequest, response: ServletResponse): Boolean {
        // 获取登录对象
        val subject = getSubject(request, response)
        try {
            val httpServletRequest: HttpServletRequest = request as HttpServletRequest
            // 获取token 在登录后每次请求在请求头中携带
            val authorization = httpServletRequest.getHeader(jwtProperties.tokenHeader)
            // 从缓存中获取登录账号
            val userId = jwtUtil.getClaim(authorization, SecurityConstants.JWT_KEY_USER_ID)
            if (!StrUtil.isEmpty(userId)) {
                val tokenKey: String = SecurityConstants.TOKEN + userId
                val token = redissonClient.getBucket<String>(tokenKey)
                if (token.isExists) {
                    token.delete();
                }
            }
            if (!StrUtil.isEmpty(userId)) {
                val authorityKey: String = SecurityConstants.TOKEN + userId
                val authority = redissonClient.getBucket<String>(authorityKey)
                if (authority.isExists) {
                    authority.delete();
                }
            }
            subject.logout()
            UserContextHandler.remove()
        } catch (ex: Exception) {
            log.error("退出登录错误", ex)
        }
        writeResult(response)
        return false
    }

    // 返回结果给请求端
    private fun writeResult(response: ServletResponse) {
        var out: PrintWriter? = null
        try {
            response.characterEncoding = "UTF-8"
            out = response.writer
            out.append(JSONUtil.toJsonPrettyStr(RespDto<String>(200, "已退出")))
        } catch (e: IOException) {
            log.error("返回Response信息出现IOException异常:" + e.message)
        } finally {
            out?.close()
        }
    }

}