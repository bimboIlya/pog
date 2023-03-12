package configuration

import com.android.build.gradle.BaseExtension
import config
import getAsInt
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

fun Project.configureAndroid() {

    extensions.configure<BaseExtension> {
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

        testOptions {
            // todo maybe uncomment later
            // unitTests.all { useJUnitPlatform() }
        }
    }

    configureKotlin()
}
