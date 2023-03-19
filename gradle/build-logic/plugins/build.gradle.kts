plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(libraries.gradle.android)
    implementation(libraries.gradle.kotlin)

    // workaround for accessing version catalog: https://github.com/gradle/gradle/issues/15383
    implementation(files(libraries.javaClass.superclass.protectionDomain.codeSource.location))
    implementation(files(config.javaClass.superclass.protectionDomain.codeSource.location))
}

gradlePlugin {
    plugins {
        androidApplication()
        androidCompose()
        androidLibrary()
        hilt()
        kotlinLibrary()
        kotlinSerialization()
    }
}

fun NamedDomainObjectContainer<PluginDeclaration>.androidApplication() {
    register("androidApplication") {
        id = "pog.android.application"
        implementationClass = "AndroidApplicationConventionPlugin"
    }
}

fun NamedDomainObjectContainer<PluginDeclaration>.androidCompose() {
    register("androidCompose") {
        id = "pog.android.compose"
        implementationClass = "AndroidComposeConventionPlugin"
    }
}

fun NamedDomainObjectContainer<PluginDeclaration>.androidLibrary() {
    register("androidLibrary") {
        id = "pog.android.library"
        implementationClass = "AndroidLibraryConventionPlugin"
    }
}

fun NamedDomainObjectContainer<PluginDeclaration>.hilt() {
    register("hilt") {
        id = "pog.hilt"
        implementationClass = "HiltConventionPlugin"
    }
}

fun NamedDomainObjectContainer<PluginDeclaration>.kotlinLibrary() {
    register("kotlinLibrary") {
        id = "pog.kotlin.library"
        implementationClass = "KotlinLibraryConventionPlugin"
    }
}

fun NamedDomainObjectContainer<PluginDeclaration>.kotlinSerialization() {
    register("kotlinSerialization") {
        id = "pog.kotlin.serialization"
        implementationClass = "KotlinSerializationConventionPlugin"
    }
}
