package com.makebk.logger.aop

import com.makebk.common.util.CoroutineUtil
import com.makebk.logger.annotations.AccessLogger
import com.makebk.logger.content.MethodInterceptorHolder
import com.makebk.logger.vo.LoggerDefine
import kotlinx.coroutines.*
import org.springframework.core.annotation.*
import java.lang.reflect.*
import java.util.*
import java.util.stream.*

/**
 *
 * 　@类   名：DefaultAccessLoggerParser
 * 　@描   述：
 * 　@作   者：gme
 * 　@版   本：1.0
 * 　@创建时间：2022/10/21 11:38 AM
 *
 */
class DefaultAccessLoggerParser : AccessLoggerParser {
    override suspend fun support(clazz: Class<*>?, method: Method?): Boolean {
        val ann = method?.let { AnnotationUtils.findAnnotation(it, AccessLogger::class.java) }
        return null != ann && !ann.ignore
    }

    override suspend fun parse(holder: MethodInterceptorHolder?): LoggerDefine? {
        val methodAnn = holder!!.findMethodAnnotation(AccessLogger::class.java)
        val classAnn = holder.findClassAnnotation(AccessLogger::class.java)
        val scope = CoroutineUtil.VirtualThread
        return withContext(scope.coroutineContext) {
            val action = scope.async {
                val action = Stream.of(classAnn, methodAnn)
                    .filter { obj -> Objects.nonNull(obj) }
                    .map { it?.value }
                    .reduce { t, u -> "$t-$u" }
                    .orElse("").toString()
                action
            }

            val describe = scope.async {
                val describe = Stream.of(classAnn, methodAnn)
                    .filter(Objects::nonNull)
                    .map { it?.describe }
                    .flatMap { Stream.of(it) }
                    .toString()
                describe
            }
            LoggerDefine(action.await(), describe.await())
        }
    }
}

