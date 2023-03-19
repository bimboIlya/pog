plugins {
    alias(libraries.plugins.android.application) apply false
    alias(libraries.plugins.android.library) apply false
    alias(libraries.plugins.firebase.crashlytics) apply false
    alias(libraries.plugins.gms) apply false
    alias(libraries.plugins.hilt) apply false
    alias(libraries.plugins.kotlin.android) apply false
    alias(libraries.plugins.kotlin.jvm) apply false
    alias(libraries.plugins.kotlin.kapt) apply false
    alias(libraries.plugins.kotlin.serialization) apply false
    alias(libraries.plugins.versionUpdates)
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
