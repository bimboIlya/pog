package plugins

import configuration.kapt
import deps
import org.gradle.api.Plugin
import org.gradle.api.Project

class HiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply(deps.plugins.hilt.get().pluginId)
            pluginManager.apply(deps.plugins.kotlin.kapt.get().pluginId)

            kapt {
                correctErrorTypes = true
            }
        }
    }
}
