package com.makebk.logger.utils


import org.aspectj.lang.JoinPoint
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.core.annotation.AnnotationUtils
import org.springframework.util.ClassUtils
import org.springframework.util.ReflectionUtils
import java.lang.reflect.Method

/**
 *
 * 　@类   名：AopUtil
 * 　@描   述：
 * 　@作   者：gme
 * 　@版   本：1.0
 * 　@创建时间：2022/10/21 11:28 AM
 *
 */
class AopUtil {
    companion object {
        fun <T : Annotation?> findMethodAnnotation(targetClass: Class<*>, method: Method?, annClass: Class<T>?): T? {
            var m: Method? = method
            var a: T? = m?.let { AnnotationUtils.findAnnotation(it, annClass) }
            if (a != null) {
                return a
            }
            m = m?.let { ClassUtils.getMostSpecificMethod(it, targetClass) }
            a = m?.let { AnnotationUtils.findAnnotation(it, annClass) }
            if (a == null) {
                val supers: MutableList<Class<*>> = ArrayList()
                supers.addAll(targetClass.interfaces)
                if (targetClass.superclass != Any::class.java) {
                    supers.add(targetClass.superclass)
                }
                for (aClass in supers) {
                    val ims: Array<Method?> = arrayOfNulls<Method>(1)
                    ReflectionUtils.doWithMethods(aClass) { im ->
                        if (im.getName()
                                .equals(method?.getName()) && im.parameterCount == method?.parameterCount
                        ) {
                            ims[0] = im
                        }
                    }
                    if (ims[0] != null) {
                        a = findMethodAnnotation(aClass, ims[0], annClass)
                        if (a != null) {
                            return a
                        }
                    }
                }
            }
            return a
        }

        fun <T : Annotation> findAnnotationClass(targetClass: Class<*>?, annClass: Class<T>?): T? {
            return targetClass?.let { AnnotationUtils.findAnnotation(it, annClass!!) }
        }

        fun <T : Annotation> findAnnotation(targetClass: Class<*>?, method: Method?, annClass: Class<T>?): T? {
            val a = targetClass?.let { findMethodAnnotation(it, method, annClass) }
            return a ?: findAnnotationClass(targetClass, annClass)
        }

        fun <T : Annotation> findAnnotation(pjp: JoinPoint, annClass: Class<T>?): T? {
            val signature: MethodSignature = pjp.signature as MethodSignature
            val m: Method = signature.method
            val targetClass: Class<*> = pjp.target.javaClass
            return findAnnotation(targetClass, m, annClass)
        }

        fun getMethodBody(pjp: JoinPoint): String? {
            val methodName = StringBuilder(pjp.signature.name).append("(")
            val signature: MethodSignature = pjp.signature as MethodSignature
            val names: Array<String> = signature.parameterNames
            val args: Array<Class<*>> = signature.parameterTypes
            var i = 0
            val len = args.size
            while (i < len) {
                if (i != 0) {
                    methodName.append(",")
                }
                methodName.append(args[i].simpleName).append(" ").append(names[i])
                i++
            }
            return methodName.append(")").toString()
        }

        fun getArgsMap(pjp: JoinPoint): Map<String, Any>? {
            val signature: MethodSignature = pjp.signature as MethodSignature
            val args: MutableMap<String, Any> = LinkedHashMap()
            val names: Array<String> = signature.parameterNames
            var i = 0
            val len = names.size
            while (i < len) {
                args[names[i]] = pjp.args[i]
                i++
            }
            return args
        }

    }
}