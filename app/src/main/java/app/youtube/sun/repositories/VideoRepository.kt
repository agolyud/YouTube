package app.youtube.sun.repositories

import app.youtube.sun.data.responses.TopVideosResponse
import app.youtube.sun.data.network.YouTubeDataSource

class VideoRepository(private val dataSource: YouTubeDataSource) {
    suspend fun getTopVideos(apiKey: String, pageToken: String? = null): TopVideosResponse {
        return dataSource.fetchTopVideos(apiKey, pageToken)
    }
}