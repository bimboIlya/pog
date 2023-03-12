dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

//apply(from = "plugins/src/main/kotlin/settings/build-convention-plugin.settings.gradle.kts")
apply(from = "../version-catalogs.gradle")

rootProject.name = "build-logic"

include(":plugins")
