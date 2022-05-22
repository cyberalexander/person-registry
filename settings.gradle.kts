println("Processing 'settings.gradle.kts' during the initialization phase.")

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "person-registry"
