dependencies {
    api("io.jsonwebtoken:jjwt:${property("jjwtVersion")}")
    api("com.auth0:java-jwt:${property("jwtVersion")}")
    api("org.apache.shiro:shiro-core:${property("shiroVersion")}:jakarta") {
        exclude(group = "org.apache.shiro")
    }
    api("org.apache.shiro:shiro-spring:${property("shiroVersion")}:jakarta") {
        exclude(group = "org.apache.shiro")
    }
    api("org.apache.shiro:shiro-web:${property("shiroVersion")}:jakarta") {
        exclude(group = "org.apache.shiro")
    }
    api("org.apache.shiro:shiro-jaxrs:${property("shiroVersion")}:jakarta") {
        exclude(group = "org.apache.shiro")
    }
    api("org.apache.shiro:shiro-ehcache:${property("shiroVersion")}")
    implementation(project(":plugins:common"))
}
