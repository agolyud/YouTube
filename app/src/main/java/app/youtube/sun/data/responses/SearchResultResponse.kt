package app.youtube.sun.data.responses

import kotlinx.serialization.Serializable

@Serializable
data class SearchResult(
    val kind: String,
    val etag: String,
    val id: SearchResultId,
    val snippet: Snippet
)