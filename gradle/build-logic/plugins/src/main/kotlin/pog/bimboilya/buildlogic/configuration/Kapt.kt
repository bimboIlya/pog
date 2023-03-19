package pog.bimboilya.buildlogic.configuration

import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.jetbrains.kotlin.gradle.dsl.KaptExtensionConfig

fun Project.kapt(block: KaptExtensionConfig.() -> Unit) {
    (this as ExtensionAware).extensions.configure("kapt", block)
}
