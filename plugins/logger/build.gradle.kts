dependencies {
    implementation(project(":plugins:common"))
    api("org.slf4j:slf4j-api:${property("apiVersion")}")
    api("ch.qos.logback:logback-classic:${property("classicVersion")}")
    api("org.springframework.boot:spring-boot-starter-actuator:${property("springBootVersion")}")
}