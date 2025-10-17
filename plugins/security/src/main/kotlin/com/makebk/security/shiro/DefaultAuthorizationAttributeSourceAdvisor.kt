package com.makebk.security.shiro

import com.makebk.common.annotations.Permission
import com.makebk.security.aop.DefaultPermissionAnnotationAopInterceptor
import org.apache.shiro.authz.annotation.*
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor
import org.springframework.core.annotation.AnnotationUtils
import java.lang.reflect.Method

class DefaultAuthorizationAttributeSourceAdvisor : AuthorizationAttributeSourceAdvisor {

    var AUTHZ_ANNOTATION_CLASSES = arrayOf(
        Permission::class.java,
        RequiresPermissions::class.java,
        RequiresRoles::class.java,
        RequiresUser::class.java,
        RequiresGuest::class.java,
        RequiresAuthentication::class.java
    )

    constructor() : super() {
        advice = DefaultPermissionAnnotationAopInterceptor()
    }

    override fun matches(method: Method, targetClass: Class<*>): Boolean {
        var m: Method = method
        if (isAuthzAnnotationPresent(m)) {
            return true
        }
        return try {
            m = targetClass.getMethod(m.name, *m.parameterTypes)
            return isAuthzAnnotationPresent(m) || isAuthzAnnotationPresent(targetClass)
        } catch (e: NoSuchMethodException) {
            false
        }
    }

    private fun isAuthzAnnotationPresent(targetClazz: Class<*>): Boolean {
        for (annClass in AUTHZ_ANNOTATION_CLASSES) {
            val a = AnnotationUtils.findAnnotation(targetClazz, annClass)
            if (a != null) {
                return true
            }
        }
        return false
    }

    private fun isAuthzAnnotationPresent(method: Method): Boolean {
        for (annClass in AUTHZ_ANNOTATION_CLASSES) {
            val a = AnnotationUtils.findAnnotation(method, annClass)
            if (a != null) {
                return true
            }
        }
        return false
    }
}