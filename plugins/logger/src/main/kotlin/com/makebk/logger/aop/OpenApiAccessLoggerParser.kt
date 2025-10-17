package com.makebk.logger.aop

import com.makebk.common.util.CoroutineUtil
import com.makebk.logger.content.MethodInterceptorHolder
import com.makebk.logger.vo.LoggerDefine
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import org.springframework.core.annotation.AnnotationUtils
import org.springframework.util.StringUtils
import java.lang.reflect.Method
import java.util.*

/**
 *  SwaggerAccessLoggerParser
 *  @description：TODO
 *  @author：gme 2023/3/21｜14:46
 *  @version：0.0.1
 */
class OpenApiAccessLoggerParser : AccessLoggerParser {
    override suspend fun support(clazz: Class<*>?, method: Method?): Boolean {
        val scope = CoroutineUtil.VirtualThread
        return withContext(scope.coroutineContext) {
            val api = scope.async {
                val api = AnnotationUtils.findAnnotation(
                    clazz!!,
                    Tag::class.java
                )
                api
            }
            val operation = scope.async {
                val operation = AnnotationUtils.findAnnotation(
                    method!!,
                    Operation::class.java
                )
                operation
            }
            api.await() != null || operation.await() != null
        }

    }

    override suspend fun parse(holder: MethodInterceptorHolder?): LoggerDefine? {
        val api = holder!!.findAnnotation(Tag::class.java)
        val operation = holder.findAnnotation(Operation::class.java)
        var action = ""
        if (api != null) {
            action = action + api.name
        }
        if (null != operation) {
            action = if (StringUtils.isEmpty(action)) operation.summary else action + "-" + operation.summary
        }

        var description = ""
        if (Objects.nonNull(operation?.description)) {
            description = operation?.description!!
        }
        return LoggerDefine(action, description)
    }
}