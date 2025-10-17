package com.makebk.common.config

import com.baomidou.mybatisplus.core.toolkit.StringPool
import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.databind.ObjectMapper
import com.makebk.common.constant.SecurityConstants
import org.redisson.api.RedissonClient
import org.redisson.codec.JsonJacksonCodec
import org.redisson.spring.cache.CacheConfig
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.interceptor.KeyGenerator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer
import org.springframework.data.redis.core.RedisTemplate as RedisTemplate1

@Lazy
@EnableCaching
@Configuration(proxyBeanMethods = false)
class RedisCacheConfig {

    @Bean
    @ConditionalOnMissingBean(name = ["redisTemplate"])
    fun redisTemplate(redisConnectionFactory: RedisConnectionFactory): RedisTemplate1<Any, Any> {
        val template = RedisTemplate1<Any, Any>()
        template.keySerializer = StringRedisSerializer()
        template.hashKeySerializer = StringRedisSerializer()
        val om = ObjectMapper()
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY)
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL)
        val jacksonSeial = Jackson2JsonRedisSerializer(om, Any::class.java)
        template.valueSerializer = jacksonSeial
        template.hashValueSerializer = jacksonSeial
        template.connectionFactory = redisConnectionFactory
        return template
    }

    @Bean
    @ConditionalOnMissingBean(StringRedisTemplate::class)
    fun stringRedisTemplate(redisConnectionFactory: RedisConnectionFactory): StringRedisTemplate {
        val template = StringRedisTemplate()
        template.connectionFactory = redisConnectionFactory
        return template
    }

    @Bean
    fun cacheManager(redissonClient: RedissonClient): CacheManager {
        val cacheManager = MessageRedissonCacheManager(redissonClient, getCacheManagerConfigMap(), JsonJacksonCodec())
        cacheManager.defaultConfig = (CacheConfig(60 * 1000, 30 * 1000))
        cacheManager.setAllowNullValues(false)
        return cacheManager
    }


    fun getCacheManagerConfigMap(): Map<String, CacheConfig> {
        val configMap = HashMap<String, CacheConfig>()
        configMap[SecurityConstants.TOKEN] = CacheConfig(60 * 1000, 30 * 1000)
        configMap[SecurityConstants.PREFIX_SHIRO_REFRESH_TOKEN] = CacheConfig(24 * 60 * 60 * 1000, 24 * 60 * 1000)
        return configMap
    }

    @Bean("keyGenerator")
    fun keyGenerator(): KeyGenerator {
        return KeyGenerator { target, method, params ->
            val sb = StringBuilder()
            sb.append(target.javaClass.simpleName)
            sb.append(StringPool.DOT)
            sb.append(method.name)
            sb.append(StringPool.LEFT_SQ_BRACKET)
            params.forEach {
                sb.append(it)
            }
            sb.append(StringPool.RIGHT_SQ_BRACKET)
            sb.toString()
        }
    }
}