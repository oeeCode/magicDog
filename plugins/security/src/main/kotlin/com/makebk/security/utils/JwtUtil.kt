package com.makebk.security.utils

import cn.hutool.core.util.StrUtil
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTDecodeException
import com.makebk.common.constant.SecurityConstants
import com.makebk.common.permission.JwtProperties
import com.makebk.common.vo.IUser
import org.springframework.stereotype.Component
import java.util.Date


@Component
class JwtUtil (
    val jwtProperties: JwtProperties,
) {

    /**
     * 校验token是否正确
     *
     * @param token
     * @return
     */
    fun verify(token: String?): Boolean {
        val secret = getClaim(token, SecurityConstants.JWT_KEY_USER_ID) + jwtProperties.secretKey
        val algorithm = Algorithm.HMAC256(secret)
        val verifier = JWT.require(algorithm)
            .build()
        verifier.verify(token)
        return true
    }


    /**
     * 获得Token中的信息无需secret解密也能获得
     *
     * @param token
     * @param claim
     * @return
     */
    fun getClaim(token: String?, claim: String?): String? {
        return try {
            val jwt = JWT.decode(token)
            jwt.getClaim(claim).asString()
        } catch (e: JWTDecodeException) {
            e.printStackTrace()
            null
        }
    }

    /**
     * 生成签名,n分钟后过期
     *
     * @param jwtProperties
     * @param user
     * @param expire
     * @return
     */
    fun sign(jwtProperties: JwtProperties, user: IUser, expire: Long): String {
        val currentTimeMillis = System.currentTimeMillis()
        // 帐号加JWT私钥加密
        val secret: String = "${user.id}${jwtProperties.secretKey}"
        // 此处过期时间，单位：毫秒
        val date = Date(System.currentTimeMillis() + expire * 60 * 1000L)
        val algorithm = Algorithm.HMAC256(secret)
        return jwtProperties.bearerHeader + StrUtil.SPACE +
                JWT.create()
                    .withClaim(SecurityConstants.JWT_KEY_USER_ID, user.id.toString())
                    .withClaim(SecurityConstants.JWT_KEY_USER_NAME, user.username)
                    .withClaim(SecurityConstants.JWT_KEY_GROUP_ID, user.groupIds.toString())
                    .withClaim(SecurityConstants.JWT_KEY_USER_TYPE, user.type.toString())
                    .withClaim(SecurityConstants.CURRENT_TIME_MILLIS, currentTimeMillis)
                    .withExpiresAt(date).sign(algorithm)
    }
}