package app.youtube.sun.data.network

import app.youtube.sun.data.responses.TopVideosResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface YouTubeApi {
    @GET("videos")
    suspend fun getTopVideos(
        @Query("chart") chart: String = "mostPopular",
        @Query("regionCode") regionCode: String = "US",
        @Query("part") part: String = "snippet,contentDetails",
        @Query("maxResults") maxResults: Int = 10,
        @Query("pageToken") pageToken: String? = null,
        @Query("key") apiKey: String
    ): TopVideosResponse
}