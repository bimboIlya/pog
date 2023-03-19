import org.gradle.api.Plugin
import org.gradle.api.Project
import pog.bimboilya.buildlogic.configuration.configureKotlin
import pog.bimboilya.buildlogic.utils.libraries

class KotlinLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply(libraries.plugins.kotlin.jvm.get().pluginId)

            configureKotlin()
        }
    }
}
