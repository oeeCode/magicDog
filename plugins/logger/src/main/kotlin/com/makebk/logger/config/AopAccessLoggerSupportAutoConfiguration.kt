package com.makebk.logger.config

import com.makebk.logger.aop.AccessLoggerListener
import com.makebk.logger.aop.AopAccessLoggerSupport
import com.makebk.logger.aop.DefaultAccessLoggerParser
import com.makebk.logger.aop.OpenApiAccessLoggerParser
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration(proxyBeanMethods = false)
@ComponentScan("com.makebk.logger")
@ConditionalOnClass(
    AccessLoggerListener::class
)
class AopAccessLoggerSupportAutoConfiguration {

    @Bean
    open fun aopAccessLoggerSupport(): AopAccessLoggerSupport {
        return AopAccessLoggerSupport()
    }
    
    @Bean
    open fun defaultAccessLoggerParser(): DefaultAccessLoggerParser {
        return DefaultAccessLoggerParser()
    }

    @Bean
    @ConditionalOnClass(name = ["io.swagger.v3.oas.annotations.tags.Tag"])
    open fun swaggerAccessLoggerParser(): OpenApiAccessLoggerParser? {
        return OpenApiAccessLoggerParser()
    }

}
