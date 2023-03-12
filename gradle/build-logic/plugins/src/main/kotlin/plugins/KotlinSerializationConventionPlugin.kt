package plugins

import deps
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class KotlinSerializationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply(deps.plugins.kotlin.serialization.get().pluginId)

            dependencies {
                add("implementation", deps.kotlinx.serialization)
            }
        }
    }
}
