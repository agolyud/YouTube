package app.youtube.sun.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import app.youtube.sun.Movie
import app.youtube.sun.R
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale

@Composable
fun VideoCard(movie: Movie, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(8.dp)) {

        Image(
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current).data(movie.thumbnailUrl)
                    .apply {
                        crossfade(true)
                        placeholder(R.drawable.ic_placeholder)
                        scale(Scale.FIT)
                    }.build()
            ),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(bottom = 8.dp)
        )
        Text(
            text = movie.title,
            modifier = Modifier.padding(8.dp)
        )
    }
}
