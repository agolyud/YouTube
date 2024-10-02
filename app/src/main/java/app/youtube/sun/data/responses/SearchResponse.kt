package app.youtube.sun.data.responses

import kotlinx.serialization.Serializable

@Serializable
data class SearchResponse(
    val kind: String?,
    val etag: String?,
    val nextPageToken: String? = null,
    val prevPageToken: String? = null,
    val regionCode: String? = null,
    val pageInfo: PageInfo?,
    val items: List<SearchResult>?
)
