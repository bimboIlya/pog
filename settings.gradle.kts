pluginManagement {
    includeBuild("gradle/build-logic")
}

apply(from = "gradle/version-catalogs.gradle")
apply(from = "gradle/build-logic/settings/src/main/kotlin/build-convention-plugin.settings.gradle.kts")

rootProject.name = "pog"

include("apps:authist:app")
include("apps:bluepoop:app")
include("apps:compost:app")
include("apps:dbpg:app")

include("apps:firebase:app")
include("apps:firebase:feature:chooser")
include("apps:firebase:feature:crashlytics")
include("apps:firebase:feature:config")
include("apps:firebase:feature:firestore")
include("apps:firebase:feature:notification")

include("apps:navsample:app")
include("apps:permissions:app")

include("common:navigation:launcher")
include("common:navigation:voyager")
include("common:ktx:jvm")
include("common:ktx:android")
include("common:ktx:compose")
include("common:permissions")
include("common:preferences")
include("common:test:jvm")
include("common:ui:theme")
