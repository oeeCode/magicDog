subprojects {
    dependencies {
        api(project(":plugins:common"))
        api("com.baomidou:mybatis-plus-generator:${property("mybatisPlusGenerator")}")
    }
}