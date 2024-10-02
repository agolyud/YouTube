package app.youtube.sun.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.youtube.sun.repositories.VideoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import android.util.Log
import app.youtube.sun.BuildConfig
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val videoRepository: VideoRepository
) : ViewModel() {

    private var searchJob: Job? = null
    private val apiKey = BuildConfig.API_KEY

    fun onSearchQueryChanged(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(3000)
            performSearch(query)
        }
    }

    fun onSearchQuerySubmitted(query: String) {
        searchJob?.cancel()
        viewModelScope.launch {
            performSearch(query)
        }
    }

    private suspend fun performSearch(query: String) {
        try {
            val searchResponse = videoRepository.searchVideos(query, apiKey)
            Log.d("SearchViewModel", "Результаты поиска для '$query': $searchResponse")
        } catch (e: Exception) {
            Log.e("SearchViewModel", "Ошибка при поиске: ${e.message}")
        }
    }
}

