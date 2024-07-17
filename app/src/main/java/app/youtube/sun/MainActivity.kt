package app.youtube.sun

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import app.youtube.sun.data.models.Movie
import app.youtube.sun.ui.NiaNavigationBar
import app.youtube.sun.ui.NiaNavigationBarItem
import app.youtube.sun.ui.NiaNavigationBarPreview
import app.youtube.sun.ui.detail.VideoDetailScreen
import app.youtube.sun.ui.list.VideoListScreenContent
import app.youtube.sun.ui.theme.YouTubeSunTheme
import app.youtube.sun.ui.detail.VideoDetailViewModel
import app.youtube.sun.ui.list.VideoListScreen
import app.youtube.sun.ui.list.VideoListViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val videoListViewModel: VideoListViewModel by viewModels()
    private val videoDetailViewModel: VideoDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            YouTubeSunTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "mainScreen") {
                    composable("mainScreen") {
                        VideoListScreenContent(videoListViewModel, videoDetailViewModel, navController)
                    }
                    composable(
                        "detailScreen/{title}/{description}",
                        arguments = listOf(
                            navArgument("title") { type = NavType.StringType },
                            navArgument("description") { type = NavType.StringType }
                        )
                    ) { backStackEntry ->
                        val title = backStackEntry.arguments?.getString("title") ?: ""
                        val description = backStackEntry.arguments?.getString("description") ?: ""
                        VideoDetailScreen(title = title, description = description, onBackClick = {
                            navController.navigateUp()
                        })
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MovieListPreview() {
    val dummyMovies = listOf(
        Movie("Movie 1", R.drawable.ic_placeholder.toString(), "id1"),
        Movie("Movie 2", R.drawable.ic_placeholder.toString(), "id2"),
        Movie("Movie 3", R.drawable.ic_placeholder.toString(), "id3")
    )
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = { NiaNavigationBarPreview() }
    ) { innerPadding ->
        VideoListScreen(
            movies = dummyMovies,
            loadMore = {},
            onVideoClick = {},
            modifier = Modifier.padding(innerPadding)
        )
    }
}
