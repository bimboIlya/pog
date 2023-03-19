import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import pog.bimboilya.buildlogic.configuration.kapt
import pog.bimboilya.buildlogic.utils.libraries

class HiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply(libraries.plugins.hilt.get().pluginId)
            pluginManager.apply(libraries.plugins.kotlin.kapt.get().pluginId)

            kapt {
                correctErrorTypes = true
            }

            dependencies {
                add("implementation", libraries.hilt.core)
                add("kapt", libraries.hilt.compiler)
            }
        }
    }
}
