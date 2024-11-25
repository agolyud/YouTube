import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class ComposePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            dependencies {
                "implementation"(libs.findLibrary("material-icons-core").get())
                "implementation"(libs.findLibrary("material-icons-extended").get())
                "implementation"(libs.findLibrary("androidx-navigation-compose").get())

                "implementation"(libs.findLibrary("androidx-activity-compose").get())
                "implementation"(platform(libs.findLibrary("androidx-compose-bom").get()))
                "implementation"(libs.findLibrary("androidx-ui").get())
                "implementation"(libs.findLibrary("androidx-ui-graphics").get())
                "implementation"(libs.findLibrary("androidx-ui-tooling-preview").get())
                "implementation"(libs.findLibrary("androidx-material3").get())
                "implementation"(libs.findLibrary("coil-compose").get())

                "debugImplementation"(libs.findLibrary("androidx-ui-tooling").get())
                "debugImplementation"(libs.findLibrary("androidx-ui-test-manifest").get())

                "androidTestImplementation"(platform(libs.findLibrary("androidx-compose-bom").get()))
                "androidTestImplementation"(libs.findLibrary("androidx-ui-test-junit4").get())
            }
        }
    }
}
