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

@Serializable
data class SearchResult(
    val kind: String,
    val etag: String,
    val id: SearchResultId,
    val snippet: Snippet
)

@Serializable
data class SearchResultId(
    val kind: String,
    val videoId: String?
)

@Serializable
data class Snippet(
    val publishedAt: String,
    val channelId: String,
    val title: String,
    val description: String,
    val thumbnails: Thumbnails,
    val channelTitle: String,
    val liveBroadcastContent: String,
    val publishTime: String
)

@Serializable
data class Thumbnails(
    val default: ThumbnailDetail?,
    val medium: ThumbnailDetail?,
    val high: ThumbnailDetail?
)

@Serializable
data class ThumbnailDetail(
    val url: String,
    val width: Int?,
    val height: Int?
)

@Serializable
data class PageInfo(
    val totalResults: Int,
    val resultsPerPage: Int
)
