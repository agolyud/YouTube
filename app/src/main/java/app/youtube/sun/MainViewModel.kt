package app.youtube.sun

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.youtube.sun.data.Video
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

    private val _movieList = MutableStateFlow<List<Video>>(emptyList())
    val movieList: StateFlow<List<Video>> get() = _movieList

    init {
        viewModelScope.launch {
            val apiKey = BuildConfig.API_KEY
            _movieList.value = repository.getTopVideos(apiKey)
        }
    }
}