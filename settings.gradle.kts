println(">>> Initialization phase:\nProcessing 'settings.gradle.kts'")

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "person-registry"
