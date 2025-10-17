dependencies {
    api(project(":plugins:sdk"))
    implementation("org.springframework.ai:spring-ai-starter-model-vertex-ai-gemini:${property("springAIVersion")}")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.ai:spring-ai-bom:${property("springAIVersion")}")
    }
}