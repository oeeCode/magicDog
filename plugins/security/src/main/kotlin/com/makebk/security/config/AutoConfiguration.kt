package com.makebk.security.config

import com.makebk.common.permission.JwtProperties
import com.makebk.security.properties.ShiroFilterProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@ComponentScan(
    basePackages = [
        "com.makebk.common",
        "com.makebk.security",
    ]
)
@Configuration
@EnableConfigurationProperties(*[ShiroFilterProperties::class, JwtProperties::class])
class AutoConfiguration