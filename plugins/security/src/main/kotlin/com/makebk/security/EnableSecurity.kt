package com.makebk.security

import com.makebk.security.config.AutoConfiguration
import org.springframework.context.annotation.Import
import java.lang.annotation.Inherited

@Inherited
@MustBeDocumented
@Target(AnnotationTarget.CLASS)
@Import(
    AutoConfiguration::class
)
@Retention(AnnotationRetention.RUNTIME)
annotation class EnableSecurity
