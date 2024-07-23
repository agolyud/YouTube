package app.youtube.sun.ui.list

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import app.youtube.sun.R
import app.youtube.sun.ui.theme.YouTubeSunTheme
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.youtube.sun.data.models.Movie
import app.youtube.sun.ui.VideoCard


@Composable
fun VideoListScreen(
    movies: List<Movie>,
    loadMore: () -> Unit,
    onVideoClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
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
    YouTubeSunTheme {
        VideoListScreen(
            movies = listOf(
                Movie("Movie 1", R.drawable.ic_placeholder.toString(), "id1"),
                Movie("Movie 2", R.drawable.ic_placeholder.toString(), "id2"),
                Movie("Movie 3", R.drawable.ic_placeholder.toString(), "id3")
            ),
            loadMore = {},
            onVideoClick = { _ -> }
        )
    }
}