package com.makebk.logger.aop

import cn.hutool.core.util.*
import com.makebk.common.constant.SecurityConstants
import com.makebk.common.handler.UserContextHandler
import com.makebk.common.util.CoroutineUtil
import com.makebk.common.util.WebUtil
import com.makebk.logger.content.MethodInterceptorHolder
import com.makebk.logger.events.AccessLoggerAfterEvent
import com.makebk.logger.vo.AccessLoggerInfo
import com.makebk.logger.vo.LoggerDefine
import jakarta.servlet.http.*
import kotlinx.coroutines.*
import org.aopalliance.intercept.*
import org.springframework.aop.support.*
import org.springframework.beans.factory.annotation.*
import org.springframework.context.*
import org.springframework.core.*
import org.springframework.util.*
import org.springframework.web.context.request.*
import java.lang.reflect.*

/**
 *
 * 　@类   名：AopAccessLoggerSupport
 * 　@描   述：
 * 　@作   者：gme
 * 　@版   本：1.0
 * 　@创建时间：2022/10/21 11:34 AM
 *
 */

class AopAccessLoggerSupport : StaticMethodMatcherPointcutAdvisor() {

    @Autowired(required = false)
    var listeners: MutableList<AccessLoggerListener> = ArrayList()

    @Autowired(required = false)
    var loggerParsers: MutableList<AccessLoggerParser> = ArrayList()

    @Autowired
    lateinit var eventPublisher: ApplicationEventPublisher


    init {
        advice = MethodInterceptor { methodInvocation ->
            val servletRequestAttributes = RequestContextHolder.getRequestAttributes() as ServletRequestAttributes
            val request = servletRequestAttributes.request
            val methodInterceptorHolder = MethodInterceptorHolder.create(methodInvocation)
            val info = createLogger(methodInterceptorHolder)
            val response: Any?
            try {
                listeners.forEach {
                    it.onLogBefore(info)
                }
                response = methodInvocation.proceed()
                info.response = (response)
            } catch (e: Throwable) {
                info.exception = (e)
                throw e
            } finally {
                // 在finally块中，先设置基本的日志信息
                info.requestId = getRequestId(request)
                info.responseTime = (System.currentTimeMillis())
                
                // 在虚拟线程中同步处理日志解析和发布，以最小化阻塞影响
                try {
                    runBlocking(CoroutineUtil.VirtualThread.coroutineContext) {
                        // 解析日志定义
                        val resolvedDefine = loggerParsers.firstOrNull { parser ->
                            try {
                                parser.support(
                                    methodInterceptorHolder?.let { ClassUtils.getUserClass(it.target) },
                                    methodInterceptorHolder?.method
                                )
                            } catch (e: Exception) {
                                false
                            }
                        }?.let { parser ->
                            try {
                                parser.parse(methodInterceptorHolder)
                            } catch (e: Exception) {
                                null
                            }
                        }
                        
                        if (resolvedDefine != null) {
                            info.action = (resolvedDefine.action)
                            info.describe = (resolvedDefine.describe)
                        }
                    }
                } catch (e: Exception) {
                    // 如果解析失败，记录错误但继续执行
                }
                
                // 发布事件和处理监听器（在主线程中，但很快）
                eventPublisher.publishEvent(AccessLoggerAfterEvent(info))
                listeners.forEach {
                    it.onLogger(info)
                }
            }
            response
        }
    }

    private fun getRequestId(request: HttpServletRequest): String {
        // SYS 为系统生成
        return request.getHeader(SecurityConstants.REQUESTID) ?: "SYS${IdUtil.fastSimpleUUID()}"
    }
    protected fun createLogger(holder: MethodInterceptorHolder?): AccessLoggerInfo {
        val info = AccessLoggerInfo()
        info.requestTime = System.currentTimeMillis()

        val user = UserContextHandler.currentUser
        if (user != null) {
            info.user = user
        }
        info.parameters = holder?.args as Map<String, Any>?
        info.target = (holder?.target?.javaClass)
        info.method = (holder?.method)

        val request: HttpServletRequest? = WebUtil.getHttpServletRequest()
        if (null != request) {
            info.httpHeaders = (WebUtil.getHeaders(request))
            info.ip = (WebUtil.getIpAddr(request))
            info.httpMethod = (request.method)
            info.url = (request.requestURL.toString())
        }
        return info
    }

    override fun getOrder(): Int {
        return HIGHEST_PRECEDENCE
    }

    override fun matches(method: Method, targetClass: Class<*>): Boolean {
        // 由于support是挂起函数，我们使用runBlocking在虚拟线程中调用它
        // 以最小化阻塞影响
        return try {
            runBlocking(CoroutineUtil.VirtualThread.coroutineContext) {
                loggerParsers.any { parser ->
                    try {
                        parser.support(targetClass, method)
                    } catch (e: Exception) {
                        false
                    }
                }
            }
        } catch (e: Exception) {
            false
        }
    }

}