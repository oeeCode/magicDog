package com.makebk.logger.annotations

import com.makebk.logger.events.LogEvent
import java.lang.annotation.Documented
import java.lang.annotation.Inherited

/**
 *
 * 　@类   名：AccessLogger
 * 　@描   述：
 * 　@作   者：gme
 * 　@版   本：1.0
 * 　@创建时间：2022/10/21 11:26 AM
 *
 */


@Inherited
@Documented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.PROPERTY, AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
annotation class AccessLogger(
    val value: String,
    val describe: String = "",
    val ignore: Boolean = false,
    val type: LogEvent.EventType = LogEvent.EventType.Usual,
)
