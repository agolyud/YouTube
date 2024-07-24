package app.youtube.sun.repositories

import app.youtube.sun.data.responses.TopVideosResponse
import app.youtube.sun.data.responses.VideoDetailResponse
import app.youtube.sun.data.network.YouTubeDataSource
import app.youtube.sun.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

open class VideoRepository @Inject constructor(
    private val dataSource: YouTubeDataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun getTopVideos(apiKey: String, pageToken: String? = null, regionCode: String): TopVideosResponse {
        return withContext(ioDispatcher) {
            dataSource.fetchTopVideos(apiKey, pageToken, regionCode)
        }
    }

    suspend fun getVideoDetails(videoId: String, apiKey: String): VideoDetailResponse {
        return withContext(ioDispatcher) {
            dataSource.fetchVideoDetails(videoId, apiKey)
        }
    }
}