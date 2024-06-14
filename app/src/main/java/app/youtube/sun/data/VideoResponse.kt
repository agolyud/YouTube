package app.youtube.sun.data

data class VideoResponse(
    val items: List<Video>
)

data class Video(
    val id: String,
    val snippet: Snippet
)

data class Snippet(
    val title: String,
    val description: String
)