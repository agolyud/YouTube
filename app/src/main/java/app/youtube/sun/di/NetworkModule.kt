package app.youtube.sun.di

import app.youtube.sun.BuildConfig
import app.youtube.sun.data.network.YouTubeApi
import app.youtube.sun.data.network.YouTubeDataSource
import app.youtube.sun.repositories.VideoRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton
import okhttp3.MediaType.Companion.toMediaType
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return logging
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideJson(): Json {
        return Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient, json: Json): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_VIDEOS)
            .client(client)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    @Singleton
    fun provideYouTubeApi(retrofit: Retrofit): YouTubeApi {
        return retrofit.create(YouTubeApi::class.java)
    }

    @Provides
    @Singleton
    fun provideYouTubeDataSource(api: YouTubeApi): YouTubeDataSource {
        return YouTubeDataSource(api)
    }

    @Provides
    @Singleton
    fun provideVideoRepository(
        dataSource: YouTubeDataSource,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): VideoRepository {
        return VideoRepository(dataSource, ioDispatcher)
    }
}