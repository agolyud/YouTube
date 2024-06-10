package app.youtube.sun.network

class YouTubeDataSource(private val api: YouTubeApi) {
    suspend fun fetchTopVideos(apiKey: String): List<Video> {
        return api.getTopVideos(apiKey = apiKey).items
    }
}