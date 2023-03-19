plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(libraries.gradle.android)
    implementation(libraries.gradle.kotlin)

    implementation(libraries.test.junit.jupiter.api)
    runtimeOnly(libraries.test.junit.jupiter.engine)

    implementation(project(":utils"))
}

gradlePlugin {
    plugins {
        androidApplication()
        androidCompose()
        androidLibrary()
        firebase()
        hilt()
        junit5()
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

fun NamedDomainObjectContainer<PluginDeclaration>.firebase() {
    register("firebase") {
        id = "pog.firebase"
        implementationClass = "FirebaseConventionPlugin"
    }
}

fun NamedDomainObjectContainer<PluginDeclaration>.hilt() {
    register("hilt") {
        id = "pog.hilt"
        implementationClass = "HiltConventionPlugin"
    }
}

fun NamedDomainObjectContainer<PluginDeclaration>.junit5() {
    register("junit5") {
        id = "pog.junit5"
        implementationClass = "JUnit5ConventionPlugin"
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
