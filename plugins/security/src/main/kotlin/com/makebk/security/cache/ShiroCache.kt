package com.makebk.security.cache

import com.makebk.common.constant.SecurityConstants
import com.makebk.common.permission.JwtProperties
import com.makebk.security.utils.JwtUtil
import org.apache.shiro.cache.Cache
import org.apache.shiro.cache.CacheException
import org.redisson.api.RedissonClient
import java.time.Duration


class ShiroCache<K, V>(
    val jwtProperties: JwtProperties,
    val redissonClient: RedissonClient,
    val jwtUtil: JwtUtil
) : Cache<K, V> {

    override fun get(p0: K): V {
        val tempKey = getKey(p0)
        var result: Any? = null
        val temp = redissonClient.getBucket<V>(tempKey)
        if (temp.isExists) {
            result = temp.get()
        }
        return (result) as V
    }

    override fun put(p0: K, p1: V): V {
        val tempKey = getKey(p0)
        val temp = redissonClient.getBucket<V>(tempKey)
        temp.set(p1!!)
        temp.expire((Duration.ofMinutes((jwtProperties.expire * 60).toLong())))
        return p1
    }

    override fun remove(p0: K): V? {
        val tempKey = getKey(p0)
        val temp = redissonClient.getBucket<V>(tempKey)
        if (temp.isExists) {
            temp.delete()
        }
        return null
    }

    @Throws(CacheException::class)
    override fun clear() {
    }

    override fun size(): Int {
        return 20
    }

    override fun keys(): Set<K>? {
        return null
    }

    override fun values(): Collection<V?> {
        val keys = keys()
        val values = ArrayList<V?>()
        if (keys != null) {
            for (key in keys) {
                val temp = redissonClient.getBucket<V>(getKey(key))
                values.add(temp.get() as V?)
            }
        }
        return values
    }

    /**
     * @param key
     * @return
     */
    private fun getKey(key: K): String {
        return SecurityConstants.PREFIX_SHIRO_CACHE + jwtUtil.getClaim(
            key.toString(),
            SecurityConstants.JWT_KEY_USER_ID
        )
    }

}