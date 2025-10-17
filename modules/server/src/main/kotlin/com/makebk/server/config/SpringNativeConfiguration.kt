package com.makebk.server.config

import org.springframework.aot.hint.RuntimeHints
import org.springframework.aot.hint.RuntimeHintsRegistrar
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.ImportRuntimeHints

@Configuration(proxyBeanMethods = false)
@ImportRuntimeHints(SpringNativeConfiguration.SpringRuntimeHintsRegistrar::class)
class SpringNativeConfiguration {

    internal class SpringRuntimeHintsRegistrar : RuntimeHintsRegistrar {

        override fun registerHints(
            hints: RuntimeHints,
            classLoader: ClassLoader?,
        ) {
            registerResourceHints(hints)
        }

        private fun registerResourceHints(hints: RuntimeHints) {
            hints.resources().registerPattern("banner.txt")
            hints.resources().registerPattern("doc.yml")
            hints.resources().registerPattern("ai.yml")
            hints.resources().registerPattern("logger.yml")
            hints.resources().registerPattern("captcha.yml")
            hints.resources().registerPattern("database.yml")
            hints.resources().registerPattern("mybatis.yml")
            hints.resources().registerPattern("security.yml")
        }
    }

}