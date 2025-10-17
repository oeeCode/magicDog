package com.makebk.security.interceptor

import com.makebk.common.annotations.Permission
import com.makebk.common.annotations.Router
import com.makebk.common.constant.DefaultMethodEnums
import com.makebk.security.handler.DefaultPermissionAnnotationHandler
import org.apache.shiro.aop.MethodInvocation
import org.apache.shiro.authz.AuthorizationException
import org.apache.shiro.authz.aop.AuthorizingAnnotationMethodInterceptor
import java.util.stream.Collectors
import java.util.stream.Stream


class DefaultPermissionAnnotationMethodInterceptor : AuthorizingAnnotationMethodInterceptor(
    DefaultPermissionAnnotationHandler()
) {
    @Throws(AuthorizationException::class)
    override fun assertAuthorized(mi: MethodInvocation) {
        try {
            val typeAnnotation = getAnnotation(mi) as? Permission ?: return
            val annotation = mi.`this`::class.java.getAnnotation(Router::class.java)
            val method: String = mi.method.name
            val excludePermission: Array<DefaultMethodEnums> = annotation.excludePermission
            if (excludePermission.contains(DefaultMethodEnums.ALL)) return
            if (!excludePermission.contains(DefaultMethodEnums.getEnum(method))) {
                (handler as DefaultPermissionAnnotationHandler).assertAuthorized(
                    if (annotation.containPath) {
                        Stream.of(*annotation.value).collect(Collectors.joining()).substring(0) + typeAnnotation.value
                    } else {
                        typeAnnotation.value
                    }
                )
            }
        } catch (ae: AuthorizationException) {
            if (ae.cause == null) ae.initCause(AuthorizationException("Not authorized to invoke method: " + mi.method))
            throw ae
        }
    }

}

