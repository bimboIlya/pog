import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

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

// region version updates configuration; tivi fork
tasks.withType<DependencyUpdatesTask> {
    rejectVersionIf {
        val current = versionToRelease(currentVersion)
        // If we're using a SNAPSHOT, ignore since we must be doing so for a reason.
        if (current == ReleaseType.SNAPSHOT) return@rejectVersionIf true

        // Otherwise we reject if the candidate is more 'unstable' than our version
        val candidate = versionToRelease(candidate.version)
        return@rejectVersionIf candidate.isLessStableThan(current)
    }
}

private val stableKeywords = listOf("RELEASE", "FINAL", "GA")
private val releaseRegex = "^[0-9,.v-]+(-r)?$".toRegex(RegexOption.IGNORE_CASE)
private val rcRegex = releaseKeywordRegex("rc")
private val betaRegex = releaseKeywordRegex("beta")
private val alphaRegex = releaseKeywordRegex("alpha")
private val devRegex = releaseKeywordRegex("dev")

fun releaseKeywordRegex(keyword: String): Regex {
    return "^[0-9,.v-]+(-$keyword[0-9]*)$".toRegex(RegexOption.IGNORE_CASE)
}

fun versionToRelease(version: String): ReleaseType {
    val stableKeyword = stableKeywords.any { keyword -> version.contains(keyword, ignoreCase = true) }
    if (stableKeyword) return ReleaseType.RELEASE

    return when {
        releaseRegex.matches(version) -> ReleaseType.RELEASE
        rcRegex.matches(version) -> ReleaseType.RC
        betaRegex.matches(version) -> ReleaseType.BETA
        alphaRegex.matches(version) -> ReleaseType.ALPHA
        devRegex.matches(version) -> ReleaseType.DEV
        else -> ReleaseType.SNAPSHOT
    }
}

enum class ReleaseType(private val level: Int) {
    SNAPSHOT(0),
    DEV(1),
    ALPHA(10),
    BETA(20),
    RC(60),
    RELEASE(100);

    fun isEqualOrMoreStableThan(other: ReleaseType): Boolean = level >= other.level

    fun isLessStableThan(other: ReleaseType): Boolean = level < other.level
}
// endregion
