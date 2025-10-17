package com.makebk.server.initializer

import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext

class DefaultSpringAppPreInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
    val LOGGER = LoggerFactory.getLogger(javaClass)

    override fun initialize(applicationContext: ConfigurableApplicationContext) {
        LOGGER.info("Spring 应用启动前置初始化程序! applicationContext = $applicationContext")
    }
}