import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import java.io.StringReader
import java.util.*


abstract class ApiKeysPluginExtension {
    var apiKey: String = ""
    var apiVideos: String = ""
}

class ApiKeysPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            val extension = extensions.create<ApiKeysPluginExtension>("apiKeys")
            val properties = readProperties(target)

            properties?.let { writePropertiesToExtension(it, extension) }
        }
    }

    private fun readProperties(target: Project): Properties? {
        with(target) {
            val fileContent = providers
                .fileContents(rootProject.layout.projectDirectory.file("apikeys.properties"))
                .asText
                .orNull

            return fileContent?.let { content ->
                Properties().apply {
                    load(StringReader(content))
                }
            }
        }
    }

    private fun writePropertiesToExtension(
        properties: Properties,
        extension: ApiKeysPluginExtension,
    ) {
        with(extension) {
            properties.getProperty("API_KEY")?.let {
                apiKey = it
            }
            properties.getProperty("API_VIDEOS")?.let {
                apiVideos = it
            }
        }
    }
}
