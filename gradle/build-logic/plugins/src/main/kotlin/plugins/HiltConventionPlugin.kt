package plugins

import configuration.kapt
import libraries
import org.gradle.api.Plugin
import org.gradle.api.Project

class HiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply(libraries.plugins.hilt.get().pluginId)
            pluginManager.apply(libraries.plugins.kotlin.kapt.get().pluginId)

            kapt {
                correctErrorTypes = true
            }
        }
    }
}
