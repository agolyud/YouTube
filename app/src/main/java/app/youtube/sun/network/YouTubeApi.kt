package app.youtube.sun.network

import retrofit2.http.GET
import retrofit2.http.Query

interface YouTubeApi {
    @GET("videos")
    suspend fun getTopVideos(
        @Query("chart") chart: String = "mostPopular",
        @Query("regionCode") regionCode: String = "US",
        @Query("part") part: String = "snippet,contentDetails,statistics",
        @Query("maxResults") maxResults: Int = 10,
        @Query("key") apiKey: String
    ): VideoResponse
}