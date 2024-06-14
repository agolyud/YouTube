package app.youtube.sun.repositories

import app.youtube.sun.data.Video
import app.youtube.sun.data.network.YouTubeDataSource

class VideoRepository(private val dataSource: YouTubeDataSource) {
    suspend fun getTopVideos(apiKey: String): List<Video> {
        return dataSource.fetchTopVideos(apiKey)
    }
}