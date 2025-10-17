package com.makebk.server.infrastructure.realm

import com.makebk.common.constant.SecurityConstants
import com.makebk.security.realm.ShiroRealm
import com.makebk.security.utils.JwtUtil
import com.makebk.server.application.service.AuthService
import jakarta.annotation.Resource
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.apache.shiro.authc.AuthenticationException
import org.apache.shiro.authc.AuthenticationInfo
import org.apache.shiro.authc.AuthenticationToken
import org.apache.shiro.authc.SimpleAuthenticationInfo
import org.apache.shiro.authz.AuthorizationInfo
import org.apache.shiro.authz.SimpleAuthorizationInfo
import org.apache.shiro.subject.PrincipalCollection
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Component


@Component
class MyShiroRealm(
    val jwtUtil: JwtUtil,
) : ShiroRealm() {

    @Lazy
    @Resource
    lateinit var authService: AuthService

    override fun doGetAuthorizationInfo(principals: PrincipalCollection): AuthorizationInfo? {
        return runBlocking {
            val token = principals.primaryPrincipal as String
            val userId = jwtUtil.getClaim(token, SecurityConstants.JWT_KEY_USER_ID)
            val info = SimpleAuthorizationInfo()
            val t1 = async {
                authService.fetchUserPermissions(userId?.toInt()!!)
            }
            info.stringPermissions = t1.await()
            info
        }
    }

    @Throws(AuthenticationException::class)
    override fun doGetAuthenticationInfo(token: AuthenticationToken): AuthenticationInfo? {
        val token = token.principal as String
        jwtUtil.getClaim(token, SecurityConstants.JWT_KEY_USER_ID)
            ?: throw AuthenticationException("操作失败，当前用户令牌已失效，请重新登录")
        if (jwtUtil.verify(token)) {
            return SimpleAuthenticationInfo(token, token, "shiroRealm")
        }
        throw AuthenticationException("操作失败，当前用户令牌已过期或不正确")
    }
}