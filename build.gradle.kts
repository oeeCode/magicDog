import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.net.URI

plugins {
    kotlin("jvm")
    kotlin("plugin.spring") apply false
    id("org.springframework.boot") apply false
    id("io.spring.dependency-management") apply false
    id("org.asciidoctor.jvm.convert") apply false
    id("org.jlleitschuh.gradle.ktlint") apply false
    id("org.graalvm.buildtools.native") apply false
}

java.sourceCompatibility = JavaVersion.valueOf("VERSION_${property("javaVersion")}")

allprojects {
    group = "${property("projectGroup")}"
    version = "${property("applicationVersion")}"

    repositories {
        maven { url = URI.create("https://maven.aliyun.com/repository/public") }
        maven { url = URI.create("https://maven.aliyun.com/repository/google") }
        maven { url = URI.create("https://maven.aliyun.com/repository/gradle-plugin") }
        maven { url = URI.create("https://maven.aliyun.com/repository/spring") }
        maven { url = URI.create("https://maven.aliyun.com/repository/grails-core") }
        maven { url = URI.create("https://maven.aliyun.com/repository/apache-snapshots") }
        mavenLocal()
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")

    if (project.path == ":modules:server") {
        apply(plugin = "org.graalvm.buildtools.native")
    }

    dependencies {
        implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactive")

        implementation("org.springframework.boot:spring-boot-starter")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    }

    tasks.getByName("bootJar") {
        enabled = false
    }

    tasks.getByName("jar") {
        enabled = true
    }

    java.sourceCompatibility = JavaVersion.valueOf("VERSION_${property("javaVersion")}")
    tasks.withType<KotlinCompile> {
        compilerOptions {
            freeCompilerArgs.set(listOf("-Xjsr305=strict"))
            jvmTarget.set(JvmTarget.fromTarget("${project.property("javaVersion")}"))
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

