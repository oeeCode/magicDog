package com.makebk.security.cache

import com.makebk.common.permission.JwtProperties
import com.makebk.security.utils.JwtUtil
import jakarta.annotation.Resource
import org.apache.shiro.cache.Cache
import org.apache.shiro.cache.CacheManager
import org.redisson.api.RedissonClient
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Service

@Lazy
@Service
class ShiroCacheManager(
    val jwtProperties: JwtProperties,
    val jwtUtil: JwtUtil
) : CacheManager {

    @Lazy
    @Resource
    lateinit var redissonClient: RedissonClient

    override fun <K : Any?, V : Any?> getCache(p0: String?): Cache<K, V> {
        return ShiroCache(jwtProperties, redissonClient, jwtUtil)
    }
}