plugins {
    `kotlin-dsl`
}

dependencies {
//    implementation(libraries.gradle.android)
//    implementation(libraries.gradle.kotlin)

    // workaround for accessing version catalog: https://github.com/gradle/gradle/issues/15383
    api(files(libraries.javaClass.superclass.protectionDomain.codeSource.location))
    api(files(config.javaClass.superclass.protectionDomain.codeSource.location))
}
