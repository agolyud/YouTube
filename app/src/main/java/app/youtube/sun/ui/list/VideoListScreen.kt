package app.youtube.sun.ui.list

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import app.youtube.sun.R
import app.youtube.sun.data.models.Movie
import app.youtube.sun.repositories.FakeVideoRepository
import app.youtube.sun.ui.VideoCard
import app.youtube.sun.ui.gaming.GamingScreen
import app.youtube.sun.ui.movies.MoviesScreen
import app.youtube.sun.ui.NiaNavigationBar
import app.youtube.sun.ui.NiaNavigationBarItem
import app.youtube.sun.ui.detail.VideoDetailViewModel
import java.util.Base64
import java.nio.charset.StandardCharsets


@Composable
fun VideoListScreenContent(
    videoListViewModel: VideoListViewModel,
    videoDetailViewModel: VideoDetailViewModel,
    navController: NavHostController
) {
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
                val movies = videoListViewModel.movieList.collectAsState(initial = emptyList()).value.map {
                    Movie(it.snippet.title, it.snippet.thumbnails.high.url, it.id)
                }
                MovieListScreen(
                    movies = movies,
                    loadMore = { videoListViewModel.load() },
                    onVideoClick = { videoId ->
                        videoDetailViewModel.fetchVideoDetails(videoId) { title, description ->
                            val encodedTitle = Base64.getUrlEncoder().encodeToString(title.toByteArray(StandardCharsets.UTF_8))
                            val encodedDescription = Base64.getUrlEncoder().encodeToString(description.toByteArray(StandardCharsets.UTF_8))
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


@Preview(showBackground = true)
@Composable
fun VideoListScreenContentPreview() {
    val dummyViewModel = VideoListViewModel(FakeVideoRepository())
    val dummyDetailViewModel = VideoDetailViewModel(FakeVideoRepository())
    val navController = rememberNavController()
    VideoListScreenContent(
        videoListViewModel = dummyViewModel,
        videoDetailViewModel = dummyDetailViewModel,
        navController = navController
    )
}
