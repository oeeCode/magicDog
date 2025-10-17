package com.makebk.logger.listener

import cn.hutool.core.util.StrUtil
import cn.hutool.extra.spring.SpringUtil
import cn.hutool.json.JSONUtil
import cn.hutool.log.LogFactory
import com.makebk.logger.events.AccessLoggerAfterEvent
import com.makebk.logger.events.LogEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import java.io.PrintWriter
import java.io.StringWriter
import java.net.InetAddress
import java.util.*

/**
 *  Slf4jAccessLoggerListener
 *  @description：TODO
 *  @author：gme 2023/2/13｜17:32
 *  @version：0.0.1
 */
@Component
class Slf4jAccessLoggerListener {

    val logger = LogFactory.get(javaClass)

    @Autowired
    var publisher: ApplicationEventPublisher? = null


    @Async
    @EventListener
    fun onLogger(event: AccessLoggerAfterEvent) {
        val info = event.logger
        val logEvent = LogEvent()
        if (logger.isDebugEnabled) {
            logEvent.id = info.requestId
            logEvent.type = LogEvent.EventType.Api
            logEvent.title = info.action
            logEvent.serviceId = SpringUtil.getApplicationName()
            logEvent.serverHost = InetAddress.getLocalHost().hostName
            logEvent.serverIp = InetAddress.getLocalHost().hostAddress
            logEvent.describe = info.describe
            val method = info.method
            val parameters: Map<String, Any>? = info.parameters
            if (method != null) {
                val methodAppender = StringJoiner(",", method.name + "(", ")")
                val parameterNames = parameters?.keys?.toTypedArray()
                val parameterTypes = method.parameterTypes
                for (i in parameterTypes.indices) {
                    methodAppender.add(parameterTypes[i].simpleName + " " + if (parameterNames?.size!! > i) parameterNames[i] else "arg$i")
                }
                logEvent.methodName = methodAppender.toString()
            }

            logEvent.env = SpringUtil.getActiveProfile()
            if (info.target != null) {
                logEvent.methodClass = "${info.target}"
            }
            if (info.httpHeaders != null) {
                logEvent.userAgent = info.httpHeaders!!["User-Agent"]
            }
            logEvent.method = info.httpMethod
            logEvent.requestUri = info.url
            logEvent.remoteIp = info.ip
            logEvent.params = "$parameters"
            if (info.response != null) {
                logEvent.response = "${JSONUtil.toJsonStr(info.response)}"
            }
            logEvent.createTime = Date(info.requestTime)
            logEvent.time = "${(info.responseTime - info.requestTime)}ms"
            if (info.exception != null) {
                val writer = StringWriter()
                info.exception?.printStackTrace(PrintWriter(writer))
                logEvent.type = LogEvent.EventType.Error
                logEvent.stackTrace = getStackTraceElement(info.exception?.getStackTrace())
                logEvent.exceptionName = info.exception!!::class.java.name
                logEvent.message = info.exception!!.message
                logEvent.lineNumber = info.exception!!.stackTrace[0].lineNumber
            }
            if (info.user != null) {
                logEvent.createBy = "${info.user!!.id}"
                logEvent.createByName = "${info.user!!.username}"
            }
            publisher!!.publishEvent(logEvent)
        }
    }

    private fun getStackTraceElement(stackTrace: Array<StackTraceElement>?): String {
        val sb = StringBuffer()
        if (stackTrace != null) {
            for (stackTraceElement in stackTrace) {
                sb.append(stackTraceElement.toString() + StrUtil.SPACE)
            }
        }
        return sb.toString()
    }
}