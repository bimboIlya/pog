import org.gradle.api.Plugin
import org.gradle.api.Project
import pog.bimboilya.buildlogic.configuration.configureAndroid
import pog.bimboilya.buildlogic.utils.libraries

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply(libraries.plugins.android.application.get().pluginId)
            pluginManager.apply(libraries.plugins.kotlin.android.get().pluginId)

            configureAndroid()
        }
    }
}
