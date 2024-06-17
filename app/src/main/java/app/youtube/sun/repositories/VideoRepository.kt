package app.youtube.sun.repositories

import app.youtube.sun.data.responses.VideoResponse
import app.youtube.sun.data.network.YouTubeDataSource

class VideoRepository(private val dataSource: YouTubeDataSource) {
    suspend fun getTopVideos(apiKey: String): List<VideoResponse> {
        return dataSource.fetchTopVideos(apiKey)
    }
}