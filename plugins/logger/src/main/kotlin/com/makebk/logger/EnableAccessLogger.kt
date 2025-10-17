package com.makebk.logger

import com.makebk.logger.config.AopAccessLoggerSupportAutoConfiguration
import org.springframework.context.annotation.Import
import java.lang.annotation.Documented
import java.lang.annotation.Inherited

/**
 *
 * 　@类   名：EnableAccessLogger
 * 　@描   述：
 * 　@作   者：gme
 * 　@版   本：1.0
 * 　@创建时间：2022/10/21 11:27 AM
 *
 */
@Inherited
@Documented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
@Import(
    AopAccessLoggerSupportAutoConfiguration::class
)
annotation class EnableAccessLogger()