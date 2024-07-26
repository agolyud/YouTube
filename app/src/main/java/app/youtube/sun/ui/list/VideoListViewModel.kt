package app.youtube.sun.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.youtube.sun.BuildConfig
import app.youtube.sun.data.responses.VideoResponse
import app.youtube.sun.repositories.VideoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoListViewModel @Inject constructor(
    private val repository: VideoRepository
) : ViewModel() {

    private val _movieList = MutableStateFlow<List<VideoResponse>>(emptyList())
    val movieList: StateFlow<List<VideoResponse>> get() = _movieList
    private var _nextPageToken: String? = null

    fun load(countryCode: String = "US") {
        viewModelScope.launch {
            val apiKey = BuildConfig.API_KEY
            val response = repository.getTopVideos(apiKey, _nextPageToken, countryCode)
            _movieList.value += response.items
            _nextPageToken = response.nextPageToken
        }
    }

    fun reload(countryCode: String = "US") {
        viewModelScope.launch {
            _movieList.value = emptyList()
            _nextPageToken = null
            load(countryCode)
        }
    }
}