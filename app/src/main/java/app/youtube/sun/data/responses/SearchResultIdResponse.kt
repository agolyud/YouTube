package app.youtube.sun.data.responses

import kotlinx.serialization.Serializable

@Serializable
data class SearchResultId(
    val kind: String,
    val videoId: String?
)