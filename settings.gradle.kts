rootProject.name = "magicDog"
include(
    "cluster",
    "cluster:monitor",
    "cluster:router",
    "domain",
    "domain:analysis",
    "domain:core",
    "modules",
    "modules:server",
    "plugins",
    "plugins:captcha",
    "plugins:common",
    "plugins:datascope",
    "plugins:logger",
    "plugins:ruleengine",
    "plugins:sdk",
    "plugins:security"
)

pluginManagement {
    val kotlinVersion: String by settings
    val springBootVersion: String by settings
    val springManagementVersion: String by settings
    val asciidoctorConvertVersion: String by settings
    val ktlintVersion: String by settings
    val nativeVersion: String by settings

    resolutionStrategy {
        eachPlugin {
            when (requested.id.id) {
                "org.jetbrains.kotlin.jvm" -> useVersion(kotlinVersion)
                "org.jetbrains.kotlin.plugin.spring" -> useVersion(kotlinVersion)
                "org.springframework.boot" -> useVersion(springBootVersion)
                "io.spring.dependency-management" -> useVersion(springManagementVersion)
                "org.asciidoctor.jvm.convert" -> useVersion(asciidoctorConvertVersion)
                "org.jlleitschuh.gradle.ktlint" -> useVersion(ktlintVersion)
                "org.graalvm.buildtools.native" -> useVersion(nativeVersion)
            }
        }
    }
}

include("domain:wechat")