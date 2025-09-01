plugins {
    id("net.neoforged.moddev.legacyforge")
}

mixin {
    add(sourceSets["main"], "${"mod_id"()}.refmap.json")

    config("${"mod_id"()}.mixins.json")
    config("${"mod_id"()}-common.mixins.json")
}

tasks.jar {
    finalizedBy("reobfJar")
    manifest.attributes(
        mapOf(
            "MixinConfigs" to "${"mod_id"()}.mixins.json,${"mod_id"()}-common.mixins.json"
        )
    )
}

legacyForge {
    version = "${"minecraft_version"()}-${"forge_version"()}"

    accessTransformers.from(project(":common").file("src/main/resources/META-INF/accesstransformer.cfg"))

    parchment {
        minecraftVersion = "minecraft_version"()
        mappingsVersion = "parchment_version"()
    }

    runs {
        create("client") {
            client()
        }

        create("server") {
            server()

            gameDirectory = project.file("run/server")
        }

        configureEach {
            jvmArgument("-XX:+AllowEnhancedClassRedefinition")
            jvmArgument("-XX:+IgnoreUnrecognizedVMOptions")
            jvmArgument("-Dmixin.debug.export=true")
            jvmArgument("-Dmixin.env.remapRefMap=true")
            jvmArgument("-Dmixin.env.refMapRemappingFile=${projectDir}/build/createSrgToMcp/output.srg")
        }
    }

    mods {
        create("mod_id"()) {
            sourceSet(sourceSets["main"])
        }
    }
}

dependencies {
    annotationProcessor("org.spongepowered:mixin:0.8.5-SNAPSHOT:processor")

    modCompileOnly("dev.engine-room.flywheel:flywheel-forge-api-${"minecraft_version"()}:${"flywheel_version"()}")
    modRuntimeOnly("dev.engine-room.flywheel:flywheel-forge-${"minecraft_version"()}:${"flywheel_version"()}")
}

operator fun String.invoke(): String {
    return rootProject.ext[this] as? String
        ?: throw IllegalStateException("Property $this is not defined")
}
