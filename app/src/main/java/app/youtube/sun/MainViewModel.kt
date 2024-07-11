package app.youtube.sun

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.youtube.sun.data.responses.VideoResponse
import app.youtube.sun.repositories.VideoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: VideoRepository
) : ViewModel() {

    private val _movieList = MutableStateFlow<List<VideoResponse>>(emptyList())
    val movieList: StateFlow<List<VideoResponse>> get() = _movieList
    private var _nextPageToken: String? = null

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            val apiKey = BuildConfig.API_KEY
            val response = repository.getTopVideos(apiKey, _nextPageToken)
            _movieList.value += response.items
            _nextPageToken = response.nextPageToken
        }
    }

    fun fetchVideoDetails(videoId: String, onDetailsFetched: (String, String) -> Unit) {
        viewModelScope.launch {
            val apiKey = BuildConfig.API_KEY
            val videoDetails = repository.getVideoDetails(videoId, apiKey)
            videoDetails.items.firstOrNull()?.let { item ->
                onDetailsFetched(item.snippet.title, item.snippet.description)
                Log.d("VideoDetails", "Video ID: ${item.id}, Title: ${item.snippet.title}, Description: ${item.snippet.description}")
            }
        }
    }
}

