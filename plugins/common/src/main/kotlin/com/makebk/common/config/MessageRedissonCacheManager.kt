package com.makebk.common.config

import org.redisson.api.RedissonClient
import org.redisson.client.codec.Codec
import org.redisson.spring.cache.CacheConfig
import org.redisson.spring.cache.RedissonSpringCacheManager

class MessageRedissonCacheManager : RedissonSpringCacheManager {

    internal var defaultConfig: CacheConfig = CacheConfig(24 * 60 * 60 * 1000, 60 * 60 * 1000)

    constructor(redisson: RedissonClient) : super(redisson)
    constructor(redisson: RedissonClient, config: Map<String, CacheConfig>) : super(redisson, config)
    constructor(redisson: RedissonClient, config: Map<String, CacheConfig>, codec: Codec) : super(
        redisson,
        config,
        codec
    )

    constructor(redisson: RedissonClient, configLocation: String) : super(redisson, configLocation)
    constructor(redisson: RedissonClient, configLocation: String, codec: Codec) : super(redisson, configLocation, codec)

    override fun createDefaultConfig(): CacheConfig {
        return defaultConfig
    }
}