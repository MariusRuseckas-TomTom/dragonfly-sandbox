rootProject.name = "SandBox"

pluginManagement {
    repositories {
        gradlePluginPortal()
    }
    plugins {
        kotlin("jvm").version("1.9.0")
        kotlin("plugin.serialization").version("1.9.0")
        id("io.ktor.plugin").version("2.3.7")
        // Provides a task to determine which dependencies have updates
        id("com.github.ben-manes.versions").version("0.39.0")
    }
}
// https://github.com/gradle/foojay-toolchains
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention").version("0.8.0")
}
