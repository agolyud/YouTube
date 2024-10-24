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
            id = "app.youtube.sun.apikeys"
            implementationClass = "ApiKeysPlugin"
        }
    }
}