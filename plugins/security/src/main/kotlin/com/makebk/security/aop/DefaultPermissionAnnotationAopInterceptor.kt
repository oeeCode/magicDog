package com.makebk.security.aop

import com.makebk.security.interceptor.DefaultPermissionAnnotationMethodInterceptor
import org.apache.shiro.spring.security.interceptor.AopAllianceAnnotationsAuthorizingMethodInterceptor

class DefaultPermissionAnnotationAopInterceptor : AopAllianceAnnotationsAuthorizingMethodInterceptor {
    constructor() : super() {
        methodInterceptors.add(DefaultPermissionAnnotationMethodInterceptor())
    }
}