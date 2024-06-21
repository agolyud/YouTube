package app.youtube.sun.data.responses

import kotlinx.serialization.Serializable

@Serializable
data class SnippetResponse(
    val title: String,
    val description: String,
    val thumbnails: ThumbnailsResponse // Модель для изображения
)

@Serializable
data class ThumbnailsResponse(
    val high: Thumbnail // Модель для высокого качества изображения
)

@Serializable
data class Thumbnail(
    val url: String // ссылка на изображение
)