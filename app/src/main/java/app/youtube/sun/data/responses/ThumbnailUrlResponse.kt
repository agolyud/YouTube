package app.youtube.sun.data.responses

import kotlinx.serialization.Serializable

@Serializable
data class ThumbnailUrlResponse(
    val url: String
)