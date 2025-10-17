package com.makebk.common.permission


import org.springframework.boot.autoconfigure.condition.*
import org.springframework.boot.context.properties.*

/**
 *
 * 　@类   名：JwtProperties
 * 　@描   述：
 * 　@作   者：gme
 * 　@版   本：1.0
 * 　@创建时间：2022/10/18 5:54 PM
 *
 */

object DelayTaskType {

    const val REDIS = "REDIS"

    const val MYSQL = "MYSQL"

    const val PGSQL = "PGSQL"
}


@ConditionalOnProperty(havingValue = DelayTaskType.REDIS)
@ConfigurationProperties(prefix = "jwt")
data class JwtProperties (
    /**
     * token过期时间，单位分钟
     */
    var expire: Long,
    /**
     * 更新令牌时间，单位分钟
     */
    var refreshCheckTime: Long,
    /**
     * Shiro缓存有效期，单位分钟
     */
    var shiroCacheExpireTime: Long,
    /**
     * token加密密钥
     */
    var secretKey: String,
    /**
     * 请求头
     */
    var tokenHeader: String,
    /*
     * 平台请求头
     */
    var platformHeader: String,
    /*
     * 平台
     */
    var platform: String? = "server",
    /**
     * 请求头
     */
    var local: String,

    var bearerHeader: String = "Bearer"

)