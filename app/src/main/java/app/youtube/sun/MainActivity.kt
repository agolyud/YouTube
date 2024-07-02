package app.youtube.sun

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.youtube.sun.data.objects.Movie
import app.youtube.sun.ui.VideoCard
import app.youtube.sun.ui.theme.YouTubeSunTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            YouTubeSunTheme {
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
                    val movies = viewModel.movieList.collectAsState(initial = emptyList()).value.map {
                        Movie(it.snippet.title, it.snippet.thumbnails.high.url)
                    }

                    MovieList(
                        movies = movies,
                        modifier = Modifier.padding(innerPadding),
                        loadMore = { viewModel.load() }
                    )
                }
            }
        }
    }
}

@Composable
fun MovieList(movies: List<Movie>, modifier: Modifier = Modifier, loadMore: () -> Unit) {
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

@Composable
fun RowScope.NiaNavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    alwaysShowLabel: Boolean = true,
    icon: @Composable () -> Unit,
    selectedIcon: @Composable () -> Unit = icon,
    label: @Composable (() -> Unit)? = null,
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = if (selected) selectedIcon else icon,
        modifier = modifier,
        enabled = enabled,
        label = label,
        alwaysShowLabel = alwaysShowLabel,
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            selectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
            indicatorColor = MaterialTheme.colorScheme.primaryContainer,
        ),
    )
}

@Composable
fun NiaNavigationBar(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    NavigationBar(
        modifier = modifier,
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        tonalElevation = 0.dp,
        content = content,
    )
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
            ),
            loadMore = {}
        )
    }
}
