package app.youtube.sun.repositories

import app.youtube.sun.data.network.YouTubeApi
import app.youtube.sun.data.responses.TopVideosResponse
import app.youtube.sun.data.responses.VideoDetailResponse
import app.youtube.sun.data.network.YouTubeDataSource
import kotlinx.coroutines.Dispatchers


class FakeVideoRepository : VideoRepository(
    dataSource = YouTubeDataSource(object : YouTubeApi {
        override suspend fun getTopVideos(
            chart: String,
            regionCode: String,
            part: String,
            maxResults: Int,
            pageToken: String?,
            apiKey: String
        ): TopVideosResponse {
            return TopVideosResponse(emptyList())
        }

        override suspend fun getVideoDetails(
            videoId: String,
            part: String,
            apiKey: String
        ): VideoDetailResponse {
            return VideoDetailResponse(emptyList())
        }
    }),
    ioDispatcher = Dispatchers.IO
)
