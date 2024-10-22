package app.youtube.sun.data.responses

import kotlinx.serialization.Serializable

@Serializable
data class ThumbnailDetail(
    val url: String,
    val width: Int?,
    val height: Int?
)