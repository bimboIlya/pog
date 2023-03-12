package plugins

import configuration.configureAndroid
import deps
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply(deps.plugins.android.library.get().pluginId)
            pluginManager.apply(deps.plugins.kotlin.android.get().pluginId)

            configureAndroid()
        }
    }
}
