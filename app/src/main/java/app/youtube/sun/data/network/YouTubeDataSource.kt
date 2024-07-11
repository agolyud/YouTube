package app.youtube.sun.data.network

import app.youtube.sun.data.responses.TopVideosResponse
import app.youtube.sun.data.responses.VideoDetailResponse

class YouTubeDataSource(private val api: YouTubeApi) {
    suspend fun fetchTopVideos(apiKey: String, pageToken: String? = null): TopVideosResponse {
        return api.getTopVideos(apiKey = apiKey, pageToken = pageToken)
    }

    suspend fun fetchVideoDetails(videoId: String, apiKey: String): VideoDetailResponse {
        return api.getVideoDetails(videoId = videoId, apiKey = apiKey)
    }
}
