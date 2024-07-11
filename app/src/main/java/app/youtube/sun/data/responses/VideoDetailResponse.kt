package app.youtube.sun.data.responses

import kotlinx.serialization.Serializable

@Serializable
data class VideoDetailResponse(
    val items: List<VideoItem>
)

@Serializable
data class VideoItem(
    val id: String,
    val snippet: Snippet
)

@Serializable
data class Snippet(
    val title: String,
    val description: String
)

