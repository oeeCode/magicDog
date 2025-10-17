package com.makebk.server.config

import com.makebk.captcha.EnableCaptcha
import com.makebk.common.constant.Constant
import com.makebk.logger.EnableAccessLogger
import com.makebk.security.EnableSecurity
import org.mybatis.spring.annotation.MapperScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync

@EnableAsync
@EnableCaptcha
@EnableSecurity
@EnableAccessLogger
@Configuration(proxyBeanMethods = false)
@ComponentScan(Constant.PACKAGE)
@MapperScan(
    value = ["com.makebk.domain.core.infrastructure.mapper"],
    sqlSessionFactoryRef = "sqlSessionFactory"
)
class ApplicationConfig