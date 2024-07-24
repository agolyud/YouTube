package app.youtube.sun.data.responses

import kotlinx.serialization.Serializable

@Serializable
data class VideoDetailResponse(
    val items: List<VideoItem>
)
