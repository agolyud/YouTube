package app.youtube.sun.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.youtube.sun.repositories.VideoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import app.youtube.sun.BuildConfig
import app.youtube.sun.data.responses.SearchResult
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import app.youtube.sun.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: VideoRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _searchResults = MutableStateFlow<List<SearchResult>>(emptyList())
    val searchResults: StateFlow<List<SearchResult>> get() = _searchResults

    private var _nextPageToken: String? = null
    private var currentQuery: String = ""
    private var searchJob: Job? = null

    fun load(query: String) {
        viewModelScope.launch(ioDispatcher) {
            val apiKey = BuildConfig.API_KEY
            val response = repository.searchVideos(query, apiKey, _nextPageToken)
                _searchResults.value += response.items ?: emptyList()
                _nextPageToken = response.nextPageToken
        }
    }

    fun reload(query: String) {
        currentQuery = query
        _searchResults.value = emptyList()
        _nextPageToken = null
        load(query)
    }

    fun onSearchQueryChanged(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch(ioDispatcher) {
            delay(3000)
            if (query.isNotBlank()) {
                reload(query)
            }
        }
    }

    fun onSearchQuerySubmitted(query: String) {
        searchJob?.cancel()
        if (query.isNotBlank()) {
            reload(query)
        }
    }

    fun loadMore() {
        if (_nextPageToken != null) {
            load(currentQuery)
        }
    }
}
