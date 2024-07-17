package app.youtube.sun.ui.movies

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import app.youtube.sun.R

@Composable
fun MoviesScreen(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier.fillMaxSize()
    ) {
        Text(text = stringResource(id = R.string.section_coming), modifier = Modifier.padding(it))
    }
}

@Composable
@Preview
fun MoviesScreenPreview() {
    MoviesScreen()
}