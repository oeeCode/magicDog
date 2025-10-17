package com.makebk.logger.content

import com.makebk.common.util.ThreadLocalUtil
import com.makebk.logger.utils.AopUtil
import org.aopalliance.intercept.MethodInvocation
import org.springframework.core.ParameterNameDiscoverer
import org.springframework.core.StandardReflectionParameterNameDiscoverer
import org.springframework.util.DigestUtils
import java.lang.reflect.Method

/**
 *
 * 　@类   名：MethodInterceptorHolder
 * 　@描   述：
 * 　@作   者：gme
 * 　@版   本：1.0
 * 　@创建时间：2022/10/21 11:29 AM
 *
 */
open class MethodInterceptorHolder(
    val id: String,
    val method: Method,
    val target: Any,
    val args: Map<String?, Any?>,
) {


    fun current(): MethodInterceptorHolder? {
        return ThreadLocalUtil[MethodInterceptorHolder::class.java.name]
    }

    fun clear(): MethodInterceptorHolder? {
        return ThreadLocalUtil.getAndRemove(MethodInterceptorHolder::class.java.name)
    }

    fun setCurrent(holder: MethodInterceptorHolder): MethodInterceptorHolder {
        return ThreadLocalUtil.put(MethodInterceptorHolder::class.java.name, holder)
    }

    companion object {
        val nameDiscoverer: ParameterNameDiscoverer = StandardReflectionParameterNameDiscoverer()

        fun create(invocation: MethodInvocation): MethodInterceptorHolder? {
            val id: String =
                DigestUtils.md5DigestAsHex(java.lang.String.valueOf(invocation.method.hashCode()).toByteArray())
            val argNames: Array<out String>? = nameDiscoverer.getParameterNames(invocation.method)
            val args: Array<Any> = invocation.arguments
            val argMap: MutableMap<String, Any> = LinkedHashMap()
            var i = 0
            val len = args.size
            while (i < len) {
                argMap[(if (argNames == null) "arg$i" else argNames[i])] = args[i]
                i++
            }

            return invocation.getThis()?.let {
                MethodInterceptorHolder(
                    id,
                    invocation.method,
                    it,
                    argMap as MutableMap<String?, Any?>
                )
            }
        }

    }

    fun set(): MethodInterceptorHolder? {
        this.setCurrent(this)
        return this
    }


    fun <T : Annotation?> findMethodAnnotation(annClass: Class<T>): T? {
        return AopUtil.findMethodAnnotation<T>(annClass, method, annClass)
    }

    fun <T : Annotation> findClassAnnotation(annClass: Class<T>?): T? {
        return AopUtil.findAnnotationClass(target.javaClass, annClass)
    }

    fun <T : Annotation> findAnnotation(annClass: Class<T>?): T? {
        return AopUtil.findAnnotation(target.javaClass, method, annClass)
    }

}