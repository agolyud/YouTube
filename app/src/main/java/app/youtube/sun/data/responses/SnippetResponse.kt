package app.youtube.sun.data.responses

import kotlinx.serialization.Serializable

@Serializable
data class SnippetResponse(
    val title: String,
    val description: String,
    val thumbnails: ThumbnailsResponse
)
