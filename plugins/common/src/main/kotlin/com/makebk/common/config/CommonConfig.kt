package com.makebk.common.config

import com.fasterxml.jackson.databind.SerializationFeature
import com.makebk.common.executor.AsyncTaskExecutorService
import com.makebk.common.permission.JwtProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(*[JwtProperties::class])
class CommonConfig : ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private val asyncTaskExecutorService: AsyncTaskExecutorService? = null

    override fun onApplicationEvent(event: ContextRefreshedEvent) {
        asyncTaskExecutorService!!.run()
    }

    @Bean
    open fun customizer(): Jackson2ObjectMapperBuilderCustomizer? {
        return Jackson2ObjectMapperBuilderCustomizer { builder: Jackson2ObjectMapperBuilder ->
            builder.featuresToEnable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING)
        }
    }
}