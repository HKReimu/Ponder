plugins {
    id("net.neoforged.moddev.legacyforge")
}

legacyForge {
    mcpVersion = "minecraft_version"()
    accessTransformers.from("src/main/resources/META-INF/accesstransformer.cfg")

    parchment {
        minecraftVersion = "minecraft_version"()
        mappingsVersion = "parchment_version"()
    }
}

dependencies {
    compileOnly("org.spongepowered:mixin:0.8.4")

    compileOnly("dev.engine-room.flywheel:flywheel-common-mojmap-api-${"minecraft_version"()}:${"flywheel_version"()}")

    implementation("com.electronwill.night-config:core:3.6.5")
    implementation("com.electronwill.night-config:toml:3.6.5")
    compileOnly("net.minecraftforge:forgeconfigapiport-fabric:3.2.3") //source: https://github.com/Fuzss/forgeconfigapiport-fabric
}

configurations {
    create("commonJava") {
        isCanBeResolved = false
        isCanBeConsumed = true
    }
    create("commonResources") {
        isCanBeResolved = false
        isCanBeConsumed = true
    }
}

artifacts {
    add("commonJava", sourceSets["main"].java.sourceDirectories.singleFile)
    add("commonResources", sourceSets["main"].resources.sourceDirectories.singleFile)
}

operator fun String.invoke(): String {
    return rootProject.ext[this] as? String
        ?: throw IllegalStateException("Property $this is not defined")
}
