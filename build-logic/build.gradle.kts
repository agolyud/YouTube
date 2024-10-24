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
    }
}