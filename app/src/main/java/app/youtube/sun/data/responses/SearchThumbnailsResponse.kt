package app.youtube.sun.data.responses

import kotlinx.serialization.Serializable

@Serializable
data class Thumbnails(
    val default: ThumbnailDetail?,
    val medium: ThumbnailDetail?,
    val high: ThumbnailDetail?
)