plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

gradlePlugin {
    plugins {
        create("apiKeysPlugin") {
            id = "apikeys"
            implementationClass = "ApiKeysPlugin"
        }
        create("hiltPlugin") {
            id = "hilt"
            implementationClass = "HiltPlugin"
        }
        create("composePlugin") {
            id = "compose"
            implementationClass = "ComposePlugin"
        }
    }
}