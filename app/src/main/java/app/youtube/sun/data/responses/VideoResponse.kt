package app.youtube.sun.data.responses

import kotlinx.serialization.Serializable

@Serializable
data class VideoResponse(
    val id: String,
    val snippet: SnippetResponse
)