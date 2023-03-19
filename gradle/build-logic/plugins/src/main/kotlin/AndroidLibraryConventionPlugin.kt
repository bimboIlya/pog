import org.gradle.api.Plugin
import org.gradle.api.Project
import pog.bimboilya.buildlogic.configuration.configureAndroid
import pog.bimboilya.buildlogic.utils.libraries

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply(libraries.plugins.android.library.get().pluginId)
            pluginManager.apply(libraries.plugins.kotlin.android.get().pluginId)

            configureAndroid()
        }
    }
}
