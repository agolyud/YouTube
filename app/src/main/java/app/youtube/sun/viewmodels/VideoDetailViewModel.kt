package app.youtube.sun.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.youtube.sun.BuildConfig
import app.youtube.sun.repositories.VideoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoDetailViewModel @Inject constructor(
    private val repository: VideoRepository
) : ViewModel() {

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
