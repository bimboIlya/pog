package pog.bimboilya.buildlogic.configuration

import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import pog.bimboilya.buildlogic.utils.config
import pog.bimboilya.buildlogic.utils.getAsInt

fun Project.configureAndroid() {

    extensions.configure<BaseExtension> {
        buildToolsVersion = config.versions.buildTools.get()
        compileSdkVersion(config.versions.targetSdk.getAsInt())

        defaultConfig {
            minSdk = config.versions.minSdk.getAsInt()
            targetSdk = config.versions.targetSdk.getAsInt()
            versionCode = config.versions.versionCode.getAsInt()
            versionName = config.versions.versionName.get()
        }

        compileOptions {
            sourceCompatibility = JavaVersion.toVersion(config.versions.java.get())
            targetCompatibility = JavaVersion.toVersion(config.versions.java.get())
        }
    }

    configureKotlin()
}
