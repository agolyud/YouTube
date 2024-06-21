package app.youtube.sun.data.network

import app.youtube.sun.data.responses.TopVideosResponse

class YouTubeDataSource(private val api: YouTubeApi) {
    suspend fun fetchTopVideos(apiKey: String, pageToken: String? = null): TopVideosResponse {
        return api.getTopVideos(apiKey = apiKey, pageToken = pageToken)
    }
}
