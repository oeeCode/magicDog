package com.makebk.server

import com.makebk.server.config.ApplicationConfig
import com.makebk.server.initializer.DefaultSpringAppPreInitializer
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.info.License
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup
import org.springframework.context.annotation.Import

@SpringBootApplication(proxyBeanMethods = false)
@Import(ApplicationConfig::class)
@OpenAPIDefinition(
    info = Info(
        title = "makebk",
        version = "dev",
        description = "makebk API文档",
        contact = Contact(name = "Gme", url = "https://lichong.work", email = "gme@gmeapp.cn"),
        license = License(name = "", url = "")
    )
)
class Application {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val springApplication = SpringApplication(Application::class.java)
            springApplication.mainApplicationClass = Application::class.java
            springApplication.applicationStartup = BufferingApplicationStartup(2048)
            springApplication.addInitializers(DefaultSpringAppPreInitializer())
            springApplication.run(*args)
        }
    }
}
