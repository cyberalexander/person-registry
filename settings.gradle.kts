println(">>> Initialization phase:\nProcessing 'settings.gradle.kts'")

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        mavenLocal()
    }
}

rootProject.name = "person-registry"
