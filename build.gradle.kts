plugins {
    java
    `maven-publish`
}

apply(from = "gradle/property_loader.gradle")

println("Numismatics v${"mod_version"()}")

val buildNumber = System.getenv("BUILD_NUMBER")?.toInt()
val gitHash = "\"${calculateGitHash() + (if (hasUnstaged()) "-modified" else "")}\""

allprojects {
    apply(plugin = "java")
    apply(plugin = "maven-publish")

    base.archivesName.set("ponder-${project.name}")
    group = "maven_group"()

    val buildNum = buildNumber?.let { it } ?: "0"

    version = "${"mod_version"()}.${buildNum}+mc${"minecraft_version"()}"

    tasks.withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
    }

    java {
        withSourcesJar()
    }
}

subprojects {
    apply(from = "../gradle/property_loader.gradle")
    apply(from = "../gradle/java.gradle")
    apply(from = "../gradle/minify_jsons.gradle")
    apply(from = "../gradle/signing.gradle")

    repositories {
        maven("https://maven.createmod.net")
        maven("https://maven.terraformersmc.com/releases/")
        maven("https://raw.githubusercontent.com/Fuzss/modresources/main/maven/")
        maven("https://maven.jamieswhiteshirt.com/libs-release")
        maven("https://mvn.devos.one/snapshots/")
        maven("https://mvn.devos.one/releases/")
        maven("https://maven.createmod.net")
        maven("https://jitpack.io")
    }

    tasks.processResources {
        val expandProps = mapOf(
            "version"                   to "mod_version"(),
            "group"                     to project.group, //Else we target the task's group.
            "minecraft_version"         to "minecraft_version"(),
            "forge_version"             to "forge_version"(),
            "forge_version_range"       to "forge_version_range"(),
            "minecraft_version_range"   to "minecraft_version_range"(),
            "fabric_version"            to "fabric_version"(),
            "fabric_loader_version"     to "fabric_loader_version"(),
            "flywheel_version_range"    to "flywheel_version_range"(),
            "mod_name"                  to "mod_name"(),
            "mod_author"                to "mod_author"(),
            "mod_credit"                to "mod_credit"(),
            "mod_id"                    to "mod_id"(),
            "mod_homepage"              to "mod_homepage"(),
            "mod_source"                to "mod_source"(),
            "mod_issues"                to "mod_issues"(),
            "mod_description"           to "mod_description"(),
            "mod_license"               to "mod_license"()
        )

        filesMatching(listOf("pack.mcmeta", "fabric.mod.json", "META-INF/mods.toml", "*.mixins.json")) {
            expand(expandProps)
        }
        inputs.properties(expandProps)
    }
}

fun calculateGitHash(): String {
    try {
        val output = providers.exec {
            commandLine("git", "rev-parse", "HEAD")
        }
        return output.standardOutput.asText.get().trim()
    } catch(_: Throwable) {
        return "unknown"
    }
}

fun hasUnstaged(): Boolean {
    try {
        val output = providers.exec {
            commandLine("git", "status", "--porcelain")
        }
        val result = output.standardOutput.asText.get().replace("/M gradlew(\\.bat)?/", "").trim()
        if (!result.isEmpty())
            println("Found stageable results:\n ${result}\n")
        return !result.isEmpty()
    }  catch(_: Throwable) {
        return false
    }
}

operator fun String.invoke(): String {
    return rootProject.ext[this] as? String
        ?: throw IllegalStateException("Property $this is not defined")
}
