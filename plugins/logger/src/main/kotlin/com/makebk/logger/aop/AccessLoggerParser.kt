package com.makebk.logger.aop

import com.makebk.logger.content.MethodInterceptorHolder
import com.makebk.logger.vo.LoggerDefine
import java.lang.reflect.Method

/**
 *
 * 　@类   名：AccessLoggerParser
 * 　@描   述：
 * 　@作   者：gme
 * 　@版   本：1.0
 * 　@创建时间：2022/10/21 11:32 AM
 *
 */
interface AccessLoggerParser {
    suspend fun support(clazz: Class<*>?, method: Method?): Boolean
    suspend fun parse(holder: MethodInterceptorHolder?): LoggerDefine?
}