package pog.bimboilya.buildlogic.configuration

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import pog.bimboilya.buildlogic.utils.config
import pog.bimboilya.buildlogic.utils.libraries

fun Project.configureKotlin() {
    tasks.withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = config.versions.java.get()
            freeCompilerArgs = freeCompilerArgs + listOf(
                "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                "-Xopt-in=kotlinx.coroutines.FlowPreview",
                "-Xopt-in=kotlin.RequiresOptIn",
            )
        }
    }

    dependencies {
        add("implementation", libraries.kotlin.stdlib)
    }
}
