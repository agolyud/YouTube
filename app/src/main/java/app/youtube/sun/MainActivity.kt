package app.youtube.sun

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import app.youtube.sun.data.objects.Movie
import app.youtube.sun.ui.GamingScreen
import app.youtube.sun.ui.MoviesScreen
import app.youtube.sun.ui.NiaNavigationBar
import app.youtube.sun.ui.NiaNavigationBarItem
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
                MainScreenContent(viewModel)
            }
        }
    }
}

@Composable
fun MainScreenContent(viewModel: MainViewModel) {
    val items = listOf("Main", "Gaming", "Movies")
    val icons = listOf(
        Icons.Filled.Home,
        Icons.Filled.SportsEsports,
        Icons.Filled.Movie
    )
    var selectedItem by remember { mutableStateOf(0) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NiaNavigationBar {
                items.forEachIndexed { index, item ->
                    NiaNavigationBarItem(
                        icon = { Icon(icons[index], contentDescription = null) },
                        label = { Text(item) },
                        selected = selectedItem == index,
                        onClick = { selectedItem = index }
                    )
                }
            }
        }
    ) { innerPadding ->
        when (selectedItem) {
            0 -> {
                val movies = viewModel.movieList.collectAsState(initial = emptyList()).value.map {
                    Movie(it.snippet.title, it.snippet.thumbnails.high.url)
                }
                MovieListScreen(movies = movies, loadMore = { viewModel.load() }, modifier = Modifier.padding(innerPadding))
            }
            1 -> GamingScreen(modifier = Modifier.padding(innerPadding))
            2 -> MoviesScreen(modifier = Modifier.padding(innerPadding))
        }
    }
}

@Composable
fun MovieListScreen(movies: List<Movie>, loadMore: () -> Unit, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        itemsIndexed(movies) { index, movie ->
            if (index == movies.size - 1) {
                LaunchedEffect(key1 = index) {
                    loadMore()
                }
            }
            VideoCard(movie = movie)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieListPreview() {
    YouTubeSunTheme {
        MovieListScreen(
            movies = listOf(
                Movie("Movie 1", R.drawable.ic_placeholder.toString()),
                Movie("Movie 2", R.drawable.ic_placeholder.toString()),
                Movie("Movie 3", R.drawable.ic_placeholder.toString())
            ),
            loadMore = {}
        )
    }
}
