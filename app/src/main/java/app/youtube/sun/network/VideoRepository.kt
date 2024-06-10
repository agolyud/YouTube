package app.youtube.sun.network

class VideoRepository(private val dataSource: YouTubeDataSource) {
    suspend fun getTopVideos(apiKey: String): List<Video> {
        return dataSource.fetchTopVideos(apiKey)
    }
}