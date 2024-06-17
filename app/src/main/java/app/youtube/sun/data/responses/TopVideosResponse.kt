package app.youtube.sun.data.responses

import kotlinx.serialization.Serializable

@Serializable
data class TopVideosResponse(
    val items: List<VideoResponse>
)
