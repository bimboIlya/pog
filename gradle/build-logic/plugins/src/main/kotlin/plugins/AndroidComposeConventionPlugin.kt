package plugins

import com.android.build.gradle.BaseExtension
import libraries
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

@Suppress("UnstableApiUsage")
class AndroidComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            extensions.configure<BaseExtension> {
                buildFeatures.compose = true

                composeOptions {
                    kotlinCompilerExtensionVersion = libraries.versions.composeCompiler.get()
                }
            }

            dependencies {
                add("implementation", platform(libraries.compose.bom))
            }
        }
    }
}
