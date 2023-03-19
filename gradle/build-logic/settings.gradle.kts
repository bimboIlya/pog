apply(from = "settings/src/main/kotlin/build-convention-plugin.settings.gradle.kts")
apply(from = "../version-catalogs.gradle")

rootProject.name = "build-logic"

include(":plugins")
include(":settings")
include(":utils")
