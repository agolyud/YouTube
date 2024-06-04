package app.youtube.sun.app.hdrezka.youtubesun

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {
    private val _movieList = MutableStateFlow<List<String>>(emptyList())
    val movieList: StateFlow<List<String>> get() = _movieList

    init {
        _movieList.value = listOf(
            "The Shawshank Redemption",
            "The Godfather",
            "The Dark Knight",
            "Pulp Fiction",
            "The Lord of the Rings: The Return of the King",
            "Forrest Gump",
            "Inception",
            "Fight Club",
            "The Matrix",
            "Goodfellas"
        )
    }
}