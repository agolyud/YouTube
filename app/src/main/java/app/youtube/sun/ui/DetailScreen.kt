package app.youtube.sun.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.util.Base64
import java.nio.charset.StandardCharsets

@Composable
fun DetailScreen(title: String, description: String, modifier: Modifier = Modifier) {
    val decodedTitle = String(Base64.getUrlDecoder().decode(title), StandardCharsets.UTF_8)
    val decodedDescription = String(Base64.getUrlDecoder().decode(description), StandardCharsets.UTF_8)

    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
            Text(text = decodedTitle, modifier = Modifier.padding(bottom = 8.dp))
            Text(text = decodedDescription)
        }
    }
}
