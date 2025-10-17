package com.makebk.security.handler

import com.makebk.common.annotations.Permission
import org.apache.shiro.authz.AuthorizationException
import org.apache.shiro.authz.aop.AuthorizingAnnotationHandler

class DefaultPermissionAnnotationHandler : AuthorizingAnnotationHandler(Permission::class.java) {

    @Throws(AuthorizationException::class)
    fun assertAuthorized(permission: String?) {
        this.subject.checkPermission(permission)
    }

    override fun assertAuthorized(a: Annotation?) {
        TODO("Not yet implemented")
    }
}