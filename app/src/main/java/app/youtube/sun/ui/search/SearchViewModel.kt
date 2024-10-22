package app.youtube.sun.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.youtube.sun.repositories.VideoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import app.youtube.sun.BuildConfig
import app.youtube.sun.data.responses.SearchResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: VideoRepository,
) : ViewModel() {

    private val _searchResults = MutableStateFlow<List<SearchResult>>(emptyList())
    val searchResults: StateFlow<List<SearchResult>> get() = _searchResults

    private val _searchQuery = MutableStateFlow<String?>(null)
    val searchQuery: String? get() = _searchQuery.value

    private var _nextPageToken: String? = null
    private var currentQuery: String = ""

    fun load(query: String) {
        viewModelScope.launch {
            val apiKey = BuildConfig.API_KEY
            val response = repository.searchVideos(query, apiKey, _nextPageToken)
            val newItems = response.items ?: emptyList()
            _searchResults.update { it + newItems }
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
        _searchQuery.value = query
    }

    fun onSearchQuerySubmitted(query: String) {
        if (query.isNotBlank()) {
            _searchQuery.value = query
            reload(query)
        }
    }

    fun loadMore() {
        if (_nextPageToken != null) {
            load(currentQuery)
        }
    }

    fun clearSearchResults() {
        _searchResults.value = emptyList()
        _searchQuery.value = null
    }
}

