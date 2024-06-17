package app.youtube.sun.data.network

import app.youtube.sun.data.responses.VideoResponse

class YouTubeDataSource(private val api: YouTubeApi) {
    suspend fun fetchTopVideos(apiKey: String): List<VideoResponse> {
        return api.getTopVideos(apiKey = apiKey).items
    }
}