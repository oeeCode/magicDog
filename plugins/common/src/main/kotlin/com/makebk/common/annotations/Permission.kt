package com.makebk.common.annotations

import java.lang.annotation.Documented

@Documented
@Target(AnnotationTarget.PROPERTY, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Permission(val value: String)