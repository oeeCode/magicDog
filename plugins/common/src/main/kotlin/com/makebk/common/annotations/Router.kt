package com.makebk.common.annotations

import com.makebk.common.constant.DefaultMethodEnums
import org.springframework.core.annotation.AliasFor
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@RequestMapping
@RestController
annotation class Router(
    @get:AliasFor(annotation = RequestMapping::class) vararg val value: String = [],
    val excludeMethod: Array<String> = [],
    val excludePermission: Array<DefaultMethodEnums> = [],
    val containPath: Boolean = true,
)