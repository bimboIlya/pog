import org.gradle.accessors.dm.LibrariesForConfig
import org.gradle.accessors.dm.LibrariesForDeps
import org.gradle.api.Project
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.getByType

val Project.deps: LibrariesForDeps
    get() = extensions.getByType()

val Project.config: LibrariesForConfig
    get() = extensions.getByType()

fun Provider<String>.getAsInt(): Int = get().toInt()
