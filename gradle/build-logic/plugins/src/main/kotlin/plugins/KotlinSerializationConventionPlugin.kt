package plugins

import libraries
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class KotlinSerializationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply(libraries.plugins.kotlin.serialization.get().pluginId)

            dependencies {
                add("implementation", libraries.kotlinx.serialization)
            }
        }
    }
}
