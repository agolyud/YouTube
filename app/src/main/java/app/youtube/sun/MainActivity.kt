package app.youtube.sun

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import app.youtube.sun.data.models.Movie
import app.youtube.sun.ui.NiaNavigationBarPreview
import app.youtube.sun.ui.detail.VideoDetailScreen
import app.youtube.sun.ui.theme.YouTubeSunTheme
import app.youtube.sun.ui.detail.VideoDetailViewModel
import app.youtube.sun.ui.filter.FilterViewModel
import app.youtube.sun.ui.list.VideoListScreen
import app.youtube.sun.ui.list.VideoListViewModel
import app.youtube.sun.ui.main.MainScreen
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val videoListViewModel: VideoListViewModel by viewModels()
    private val videoDetailViewModel: VideoDetailViewModel by viewModels()
    private val filterViewModel: FilterViewModel by viewModels()

    override fun attachBaseContext(newBase: Context?) {
        val newLocale = Locale.getDefault()
        val contextWithLocale = newBase?.let { setLocale(it, newLocale) }
        super.attachBaseContext(contextWithLocale)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            YouTubeSunTheme {
                val navController = rememberNavController()
                val selectedLanguage by filterViewModel.selectedLanguage.collectAsState()

                LaunchedEffect(selectedLanguage) {
                    selectedLanguage?.let { newLanguage ->
                        val currentLocale = resources.configuration.locales[0]
                        if (currentLocale.language != newLanguage) {
                            val newLocale = Locale(newLanguage)
                            setLocale(this@MainActivity, newLocale)
                            recreate()
                        }
                    }
                }

                NavHost(navController = navController, startDestination = "mainScreen") {
                    composable("mainScreen") {
                        MainScreen(
                            videoListViewModel = videoListViewModel,
                            videoDetailViewModel = videoDetailViewModel,
                            filterViewModel = filterViewModel,
                            navController = navController
                        )
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
                        VideoDetailScreen(
                            title = title,
                            description = description,
                            onBackClick = { navController.navigateUp() }
                        )
                    }
                }
            }
        }
    }
}


fun setLocale(context: Context, locale: Locale): Context {
        Locale.setDefault(locale)
        val config = context.resources.configuration
        config.setLocale(locale)
        config.setLayoutDirection(locale)

        return context.createConfigurationContext(config)
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
