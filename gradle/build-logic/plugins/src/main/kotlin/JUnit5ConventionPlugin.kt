import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import pog.bimboilya.buildlogic.utils.libraries

class JUnit5ConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            tasks.withType<Test> {
                useJUnitPlatform()
            }

            dependencies {
                add("testRuntimeOnly", libraries.test.junit.jupiter.engine)
                add("testImplementation", libraries.test.junit.jupiter.api)
            }
        }
    }
}
