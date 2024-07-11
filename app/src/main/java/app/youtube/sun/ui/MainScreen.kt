package app.youtube.sun.ui

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
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import app.youtube.sun.MainViewModel
import app.youtube.sun.R
import app.youtube.sun.data.models.Movie
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun MainScreenContent(viewModel: MainViewModel, navController: NavHostController) {
    val items = listOf(
        stringResource(id = R.string.main),
        stringResource(id = R.string.gaming),
        stringResource(id = R.string.movies)
    )
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
                    Movie(it.snippet.title, it.snippet.thumbnails.high.url, it.id)
                }
                MovieListScreen(
                    movies = movies,
                    loadMore = { viewModel.load() },
                    onVideoClick = { videoId ->
                        viewModel.fetchVideoDetails(videoId) { title, description ->
                            val encodedTitle = URLEncoder.encode(title, StandardCharsets.UTF_8.toString())
                            val encodedDescription = URLEncoder.encode(description, StandardCharsets.UTF_8.toString())
                            navController.navigate("detailScreen/$encodedTitle/$encodedDescription")
                        }
                    },
                    modifier = Modifier.padding(innerPadding)
                )
            }
            1 -> GamingScreen(modifier = Modifier.padding(innerPadding))
            2 -> MoviesScreen(modifier = Modifier.padding(innerPadding))
        }
    }
}


@Composable
fun MovieListScreen(movies: List<Movie>, loadMore: () -> Unit, onVideoClick: (String) -> Unit, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        itemsIndexed(movies) { index, movie ->
            if (index == movies.size - 1) {
                LaunchedEffect(key1 = index) {
                    loadMore()
                }
            }
            VideoCard(movie = movie, onClick = { onVideoClick(movie.id) })
        }
    }
}
