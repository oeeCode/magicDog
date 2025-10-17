subprojects {
    dependencies {
        compileOnly("org.graalvm.sdk:graal-sdk:${property("graalSdkVersion")}")
    }
}