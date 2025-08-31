/*
 This module can inject build properties from a JSON file. Each property in the
 JSON file will be mapped to a build property using the key of that property.
 Property keys ending with _comment will be skipped.

 If a secretFile property exists and points to a valid JSON file that file will
 be automatically loaded. You can manually load a file using the loadProperties
 method.
*/

import groovy.json.JsonSlurper
import org.gradle.api.Project
import java.io.File

// Auto detects a secret file and injects it.
if (rootProject.hasProperty("secretFile")) {
    logger.lifecycle("Automatically loading properties from the secretFile")
    val secretsFile = rootProject.file(rootProject.property("secretFile")!!)

    if (secretsFile.exists() && secretsFile.name.endsWith(".json")) {
        rootProject.loadProperties(secretsFile)
    }
}

// Loads properties using a specified json file.
fun Project.loadProperties(propertyFile: File) {
    if (propertyFile.exists()) {
        propertyFile.reader().use { reader ->
            @Suppress("UNCHECKED_CAST")
            val propMap = JsonSlurper().parse(reader) as Map<String, Any?>
            for ((key, value) in propMap) {
                // Filter entries that use _comment in the key.
                if (!key.endsWith("_comment")) {
                    extensions.extraProperties[key] = value
                }
            }

            logger.lifecycle("Successfully loaded " + propMap.size + " properties")
        }
    } else {
        logger.warn("The property file " + propertyFile.name + " could not be loaded. It does not exist.")
    }
}

// Allows other scripts to use these methods.
project.extra.set("loadProperties", { file: File -> project.loadProperties(file) })
