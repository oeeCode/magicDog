dependencies {
    implementation(project(":plugins:common"))
    api("io.vertx:vertx-web-client:${property("vertxVersion")}")
    api("io.vertx:vertx-lang-kotlin:${property("vertxVersion")}")
    api("io.vertx:vertx-lang-kotlin-coroutines:${property("vertxVersion")}")

    runtimeOnly("io.netty:netty-resolver-dns-native-macos:4.1.77.Final:osx-x86_64")
}