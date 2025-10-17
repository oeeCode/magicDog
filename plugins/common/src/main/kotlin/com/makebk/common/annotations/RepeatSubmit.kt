package com.makebk.common.annotations

import java.lang.annotation.Inherited

/**
 *  RepeatSubmit
 *  @description：TODO
 *  @author：gme 2023/7/12｜16:31
 *  @version：0.0.1
 */


@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@Inherited
@MustBeDocumented
annotation class RepeatSubmit(
    /**
     * 默认防重提交，是方法参数
     * @return
     */
    val limitType: Type = Type.PARAM,
    /**
     * 加锁过期时间，默认是5秒
     * @return
     */
    val lockTime: Long = 5000L,
) {
    companion object {
        enum class Type {
            PARAM,
            TOKEN
        }
    }
}