import java.util.Properties

plugins {
    `kotlin-dsl`
    id("java-gradle-plugin")
    kotlin("jvm") version "1.9.23"
}

repositories {
    gradlePluginPortal()
    mavenCentral()
    maven("https://maven.ithundxr.dev/releases")
    maven("https://maven.fabricmc.net")
    maven("https://maven.neoforged.net/releases")
}

gradlePlugin {
    plugins {
        create("ponderPlugin") {
            id = "net.createmod.ponder.gradle"
            implementationClass = "net.createmod.pondergradle.PonderGradlePlugin"
        }
    }
}

val properties by lazy {
    Properties().apply {
        load(rootDir.parentFile.resolve("gradle.properties").inputStream())
    }
}

dependencies {
    implementation("org.ow2.asm:asm:${"asm_version"()}")
    implementation("org.ow2.asm:asm-tree:${"asm_version"()}")
    implementation("org.ow2.asm:asm-util:${"asm_version"()}")

    implementation("dev.ithundxr.lotus:lotus-gradle:${"lotus_gradle_version"()}")

    implementation("fabric-loom:fabric-loom.gradle.plugin:${properties["loom_version"]}")
    implementation("net.neoforged.moddev.legacyforge:net.neoforged.moddev.legacyforge.gradle.plugin:${properties["mdg_version"]}")
}

operator fun String.invoke(): String {
    return rootProject.ext[this] as? String
        ?: throw IllegalStateException("Property $this is not defined")
}
