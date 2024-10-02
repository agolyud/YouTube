package app.youtube.sun.data.responses

import kotlinx.serialization.Serializable

@Serializable
data class PageInfo(
    val totalResults: Int,
    val resultsPerPage: Int
)