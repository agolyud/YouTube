package app.youtube.sun.ui.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.SportsEsports
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
import app.youtube.sun.ui.filter.FilterViewModel
import app.youtube.sun.ui.gaming.GamingScreen
import app.youtube.sun.ui.list.VideoListScreen
import app.youtube.sun.ui.list.VideoListViewModel
import app.youtube.sun.ui.movies.MoviesScreen
import java.util.Base64
import java.nio.charset.StandardCharsets
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    videoListViewModel: VideoListViewModel,
    videoDetailViewModel: VideoDetailViewModel,
    filterViewModel: FilterViewModel,
    navController: NavHostController
) {
    val items = listOf(
        R.string.main,
        R.string.gaming,
        R.string.movies
    )
    val icons = listOf(
        Icons.Filled.Home,
        Icons.Filled.SportsEsports,
        Icons.Filled.Movie
    )
    var selectedItem by remember { mutableStateOf(0) }
    var isFilterDialogVisible by remember { mutableStateOf(false) }
    val selectedCountry by filterViewModel.selectedCountry.collectAsState()
    val selectedLanguage by filterViewModel.selectedLanguage.collectAsState()

    LaunchedEffect(selectedCountry) {
        selectedCountry?.let {
            videoListViewModel.reload(it)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = when (selectedItem) {
                    0 -> stringResource(id = R.string.main)
                    1 -> stringResource(id = R.string.gaming)
                    2 -> stringResource(id = R.string.movies)
                    else -> stringResource(id = R.string.main)
                }) },
                actions = {
                    IconButton(onClick = { isFilterDialogVisible = true }) {
                        Icon(imageVector = Icons.Filled.FilterList, contentDescription = "Filter")
                    }
                }
            )
        },
        bottomBar = {
            NiaNavigationBar {
                items.forEachIndexed { index, itemResId ->
                    NiaNavigationBarItem(
                        icon = { Icon(icons[index], contentDescription = null) },
                        label = { Text(stringResource(id = itemResId)) },
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
                    loadMore = { selectedCountry?.let { videoListViewModel.load(it) } },
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
                selectedCountry = selectedCountry ?: "US",
                selectedLanguage = selectedLanguage ?: Locale.getDefault().language,
                onCountryChange = { countryCode ->
                    filterViewModel.updateCountry(countryCode)
                    isFilterDialogVisible = false
                },
                onLanguageChange = { languageCode ->
                    filterViewModel.updateLanguage(languageCode)
                    isFilterDialogVisible = false
                }
            )
        }
    }
}

