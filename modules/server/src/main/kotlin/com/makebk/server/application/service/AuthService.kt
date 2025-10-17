package com.makebk.server.application.service

import cn.hutool.core.lang.Assert
import cn.hutool.json.JSONObject
import cn.hutool.json.JSONUtil
import cn.hutool.log.LogFactory
import com.anji.captcha.model.common.ResponseModel
import com.anji.captcha.model.vo.CaptchaVO
import com.anji.captcha.service.CaptchaService
import com.makebk.common.handler.UserContextHandler
import com.makebk.common.permission.JwtProperties
import com.makebk.common.util.WebUtil
import com.makebk.common.vo.JwtRespBody
import com.makebk.domain.core.application.service.ISysStaffService
import com.makebk.security.utils.JwtUtil
import jakarta.annotation.Resource
import jakarta.servlet.http.HttpServletRequest
import kotlinx.coroutines.runBlocking
import org.redisson.api.RedissonClient
import org.springframework.stereotype.Service

interface AuthService {

    fun getCaptcha(data: CaptchaVO, request: HttpServletRequest): JSONObject

    fun check(data: CaptchaVO, request: HttpServletRequest): ResponseModel

    fun refreshToken(refreshToken: String): Any?

    fun systemInfo(): Map<String, Any>

    fun fetchUserPermissions(id: Int): Set<String>

    fun getUserInfo(): JwtRespBody
}

@Service
class AuthServiceImpl(
    var jwtUtil: JwtUtil,
    var jwtProperties: JwtProperties,
    var redissonClient: RedissonClient,
    var staffService: ISysStaffService,
) : AuthService {

    @Resource
    lateinit var captchaService: CaptchaService

    private val logger = LogFactory.get(javaClass)

    override fun getCaptcha(
        data: CaptchaVO,
        request: HttpServletRequest,
    ): JSONObject {
        logger.debug("获取验证码")
        data.browserInfo = WebUtil.getIpAddr(request) + WebUtil.getHeaders(request)["user-agent"]
        val responseModel = captchaService.get(data)
        return JSONUtil.parseObj(responseModel.repData)
    }

    override fun check(
        data: CaptchaVO,
        request: HttpServletRequest,
    ): ResponseModel {
        data.browserInfo = WebUtil.getIpAddr(request) + WebUtil.getHeaders(request)["user-agent"]
        return captchaService.check(data)
    }

    override fun refreshToken(refreshToken: String): Any? {
        Assert.notNull(refreshToken, "令牌刷新失败，请重新登录")
        return JwtRespBody(null, null, null, refreshToken, null)
    }

    override fun systemInfo(): Map<String, Any> {
        TODO("Not yet implemented")
    }

    override fun fetchUserPermissions(id: Int): Set<String> {
        val permissions: MutableList<String> = ArrayList()
        val user = staffService.getById(id)
        return runBlocking {
//            if (user != null && user.id == Constant.SUPER_MANAGER_ID) {
//                val menus = sysMenuService.queryList(
//                    mutableMapOf(
//                        "status" to 1
//                    )
//                )
//                permissions.addAll(java.util.HashSet<String>(menus.map { menu -> menu.permission }))
//            } else {
//                permissions.addAll(sysUserService.queryAllPerms(user))
//            }
            formatPerm(permissions.distinct())
        }
    }

    private fun formatPerm(permissions: List<String>): Set<String> {
        val permsSet = HashSet<String>()
        permissions.forEach { perm ->
            permsSet.addAll(perm.trim { it <= ' ' }.split(",".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray())
        }
        return permsSet
    }

    override fun getUserInfo(): JwtRespBody {
        val user = UserContextHandler.currentUser
        val staff = staffService.getById(user?.id)
        val jwt = JwtRespBody(
            staff.id, staff.name, user?.token, null, user?.groupIds
        )
        jwt.avatar = staff.avatar
        return jwt
    }
}