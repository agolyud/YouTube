package app.youtube.sun

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import app.youtube.sun.ui.VideoCard
import app.youtube.sun.ui.theme.YouTubeSunTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            YouTubeSunTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val movies = viewModel.movieList.collectAsState(initial = emptyList()).value.map {
                        Movie(it.snippet.title, it.snippet.thumbnails.high.url)
                    }
                    MovieList(
                        movies = movies,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

data class Movie(
    val title: String,
    val thumbnailUrl: String
)


@Composable
fun MovieList(movies: List<Movie>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(movies) { movie ->
            VideoCard(movie = movie)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieListPreview() {
    YouTubeSunTheme {
        MovieList(
            movies = listOf(
                Movie("Movie 1", R.drawable.ic_placeholder.toString()),
                Movie("Movie 2", R.drawable.ic_placeholder.toString()),
                Movie("Movie 3", R.drawable.ic_placeholder.toString())
            )
        )
    }
}
