package com.makebk.common.util

import java.util.function.Supplier

/**
 *  ThreadLocalUtil
 *  @description：TODO
 *  @author：gme 2023/2/8｜16:31
 *  @version：0.0.1
 */
object ThreadLocalUtil {
    private val local = ThreadLocal.withInitial<MutableMap<String, Any>> { HashMap() }

    /**
     * @return threadLocal中的全部值
     */
    fun getAll(): Map<String, Any>? {
        return local.get()
    }

    /**
     * 设置一个值到ThreadLocal
     *
     * @param key   键
     * @param value 值
     * @param <T>   值的类型
     * @return 被放入的值
     * @see Map.put
    </T> */
    fun <T : Any> put(key: String, value: T): T {
        local.get()[key] = value
        return value
    }

    /**
     * 删除参数对应的值
     *
     * @param key
     * @see Map.remove
     */
    fun remove(key: String) {
        local.get().remove(key)
    }

    /**
     * 清空ThreadLocal
     *
     * @see Map.clear
     */
    fun clear() {
        local.remove()
    }

    /**
     * 从ThreadLocal中获取值
     *
     * @param key 键
     * @param <T> 值泛型
     * @return 值, 不存在则返回null, 如果类型与泛型不一致, 可能抛出[ClassCastException]
     * @see Map.get
     * @see ClassCastException
    </T> */
    operator fun <T> get(key: String): T? {
        return local.get()[key] as T?
    }

    /**
     * 从ThreadLocal中获取值,并指定一个当值不存在的提供者
     *
     * @see Supplier
     *
     * @since 3.0
     */
    operator fun <T : Any> get(key: String, supplierOnNull: Supplier<T>): T {
        return local.get().computeIfAbsent(
            key
        ) { k: String? -> supplierOnNull.get() } as T
    }

    /**
     * 获取一个值后然后删除掉
     *
     * @param key 键
     * @param <T> 值类型
     * @return 值, 不存在则返回null
     * @see this.get
     * @see this.remove
    </T> */
    fun <T> getAndRemove(key: String): T? {
        return try {
            get(key)
        } finally {
            remove(key)
        }
    }
}