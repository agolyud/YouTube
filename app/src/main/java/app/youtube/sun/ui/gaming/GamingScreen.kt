package app.youtube.sun.ui.gaming

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import app.youtube.sun.R

@Composable
fun GamingScreen(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier.fillMaxSize()
    ) {
        Text(text = stringResource(id = R.string.section_coming), modifier = Modifier.padding(it))
    }
}