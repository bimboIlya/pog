package plugins

import configuration.configureKotlin
import libraries
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class KotlinLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply(libraries.plugins.kotlin.jvm.get().pluginId)

            configureKotlin()

            dependencies {
                add("implementation", libraries.kotlin.stdlib)
            }
        }
    }
}
