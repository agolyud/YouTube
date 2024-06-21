package app.youtube.sun

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

    private val _nextPageToken = MutableStateFlow<String?>(null)
    val nextPageToken: StateFlow<String?> get() = _nextPageToken

    init {
        loadMoreVideos()
    }

    fun loadMoreVideos() {
        viewModelScope.launch {
            val apiKey = BuildConfig.API_KEY
            val response = repository.getTopVideos(apiKey, _nextPageToken.value)
            _movieList.value = _movieList.value + response.items
            _nextPageToken.value = response.nextPageToken
        }
    }
}
