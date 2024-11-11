import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm").version("1.4.31")
    kotlin("plugin.serialization").version("1.9.0")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.junit.jupiter:junit-jupiter:5.8.2")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:2.3.4")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "17"
            freeCompilerArgs = listOf("-Xjsr305=strict")
        }
    }
    test {
        useJUnitPlatform()
        testLogging.showExceptions
    }
}

with (extensions.getByType(KotlinJvmProjectExtension::class.java)) {
    jvmToolchain(17)
}
