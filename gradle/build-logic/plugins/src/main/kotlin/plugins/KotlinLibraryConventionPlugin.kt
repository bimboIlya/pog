package plugins

import configuration.configureKotlin
import deps
import org.gradle.api.Plugin
import org.gradle.api.Project

class KotlinLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply(deps.plugins.kotlin.jvm.get().pluginId)

            configureKotlin()
        }
    }
}
