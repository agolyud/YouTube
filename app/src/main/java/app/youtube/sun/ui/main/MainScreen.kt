package app.youtube.sun.ui.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import app.youtube.sun.R
import app.youtube.sun.data.models.Movie
import app.youtube.sun.ui.NiaNavigationBar
import app.youtube.sun.ui.NiaNavigationBarItem
import app.youtube.sun.ui.detail.VideoDetailViewModel
import app.youtube.sun.ui.filter.FilterDialog
import app.youtube.sun.ui.gaming.GamingScreen
import app.youtube.sun.ui.list.VideoListScreen
import app.youtube.sun.ui.list.VideoListViewModel
import app.youtube.sun.ui.movies.MoviesScreen
import java.util.Base64
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
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
    var isFilterDialogVisible by remember { mutableStateOf(false) }
    var selectedCountry by remember { mutableStateOf("US") }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "") },
                actions = {
                    IconButton(onClick = { isFilterDialogVisible = true }) {
                        Icon(imageVector = Icons.Filled.FilterList, contentDescription = "Filter")
                    }
                }
            )
        },
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
                VideoListScreen(
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
        if (isFilterDialogVisible) {
            FilterDialog(
                onDismiss = { isFilterDialogVisible = false },
                selectedCountry = selectedCountry,
                onCountryChange = { selectedCountry = it }
            )
        }
    }
}
