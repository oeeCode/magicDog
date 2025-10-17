dependencies {
    api("org.springframework.boot:spring-boot-starter-web")
    api("org.springframework.boot:spring-boot-starter-aop")
    api("org.springframework.boot:spring-boot-starter-cache")
    api("org.springframework.boot:spring-boot-starter-actuator")
    api("org.springframework.boot:spring-boot-starter-jdbc")
    api("org.springframework.boot:spring-boot-starter-validation")
    api("org.springframework.boot:spring-boot-autoconfigure")

    api("ch.qos.logback:logback-classic:${property("logbackVersion")}")
    api("org.aspectj:aspectjrt:${property("aspectjrtVersion")}")
    api("org.aspectj:aspectjweaver:${property("aspectjweaverVersion")}")
    api("cn.hutool:hutool-all:${property("hutoolVersion")}")

    runtimeOnly("com.mysql:mysql-connector-j")
    api("com.baomidou:mybatis-plus-spring-boot3-starter:${property("mybatisPlusVersion")}")
    api("com.baomidou:mybatis-plus-jsqlparser:${property("mybatisPlusVersion")}")
    api("org.redisson:redisson-spring-boot-starter:${property("redissonVersion")}")


    api("com.fasterxml.jackson.core:jackson-core:${property("jsonVersion")}")
    api("com.fasterxml.jackson.core:jackson-databind:${property("jsonVersion")}")
    api("com.fasterxml.jackson.module:jackson-module-kotlin")

    implementation("org.freemarker:freemarker:${property("freemarkerVersion")}")
    api("org.springdoc:springdoc-openapi-starter-webmvc-ui:${property("springdocVersion")}")
    api("io.swagger:swagger-annotations:${property("swaggerVersion")}")
}