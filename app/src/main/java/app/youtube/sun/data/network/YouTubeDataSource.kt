package app.youtube.sun.data.network

import app.youtube.sun.data.Video

class YouTubeDataSource(private val api: YouTubeApi) {
    suspend fun fetchTopVideos(apiKey: String): List<Video> {
        return api.getTopVideos(apiKey = apiKey).items
    }
}