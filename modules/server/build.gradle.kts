tasks.getByName("bootJar") {
    enabled = true
}

dependencies {
    implementation(project(":domain:core"))
    implementation(project(":domain:wechat"))
    implementation(project(":plugins:captcha"))
    implementation(project(":plugins:common"))
    implementation(project(":plugins:datascope"))
    implementation(project(":plugins:logger"))
    implementation(project(":plugins:security"))
}

springBoot {
    mainClass.set("com.makebk.server.Application")
    buildInfo()
}

graalvmNative {
    testSupport = false
    toolchainDetection = true
    metadataRepository {
        enabled = true  // 启用自动检测反射调用
    }
    binaries {
        named("main") {
            mainClass.set("com.makebk.server.Application")
            resources.autodetect()
            buildArgs.addAll(
                "--no-fallback",
                "-H:-ReportExceptionStackTraces",
                "-H:+AddAllFileSystemProviders",
                "-H:+AllowDeprecatedBuilderClassesOnImageClasspath",
                "--strict-image-heap",
                "--allow-incomplete-classpath",
                "--enable-url-protocols=http",
                "--features=com.makebk.server.infrastructure.ext.LambdaRegistrationFeature",
                "-Djava.security.properties=src/main/resources/custom.security",
                "--initialize-at-run-time=org.apache.tomcat.util.net.openssl.OpenSSLEngine" +
                        ",net.i2p.crypto.eddsa.EdDSASecurityProvider" +
                        ",org.slf4j.LoggerFactory" +
                        ",org.slf4j.reload4j.Reload4jLoggerFactory" +
                        ",org.redisson.Redisson" +
                        ",ch.qos.logback.classic.Logger" +
                        ",ch.qos.logback.core.status.InfoStatus" +
                        ",ch.qos.logback.classic.Level" +
                        ",ch.qos.logback.core.util.Loader" +
                        ",ch.qos.logback.core.util.StatusPrinter2" +
                        ",ch.qos.logback.core.util.StatusPrinter",
                "--trace-class-initialization=io.netty.util.internal.shaded.org.jctools.util.UnsafeAccess",
                "--trace-object-instantiation=java.util.jar.JarFile"
            )
        }
    }
}