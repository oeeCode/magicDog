package com.makebk.captcha.service

import cn.hutool.extra.spring.SpringUtil
import com.anji.captcha.service.CaptchaCacheService
import com.makebk.captcha.permission.AjCaptchaProperties
import org.redisson.api.RedissonClient
import java.time.Duration

class CaptchaCacheServiceRedisImpl() : CaptchaCacheService {

    val redissonClient: RedissonClient = SpringUtil.getBean(RedissonClient::class.java)
    val config: AjCaptchaProperties = SpringUtil.getBean(AjCaptchaProperties::class.java)

    override fun set(key: String, value: String, expiresInSeconds: Long) {
        val cache = redissonClient.getBucket<String>(key)
        cache.set(value)
        cache.expire(Duration.ofSeconds(expiresInSeconds))
    }

    override fun exists(key: String): Boolean =
        redissonClient.getBucket<String>(key).isExists

    override fun delete(key: String) {
        val cache = redissonClient.getBucket<String>(key)
        cache.delete()
    }

    override fun get(key: String): String {
        val cache = redissonClient.getBucket<String>(key)
        return cache.get()
    }

    override fun type() = config.cacheType.name

    override fun increment(key: String?, `val`: Long): Long? {
        return super.increment(key, `val`)
    }

}