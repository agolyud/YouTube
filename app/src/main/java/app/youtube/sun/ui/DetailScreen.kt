package app.youtube.sun.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun DetailScreen(title: String, description: String, modifier: Modifier = Modifier) {
    val decodedTitle = URLDecoder.decode(title, StandardCharsets.UTF_8.toString())
    val decodedDescription = URLDecoder.decode(description, StandardCharsets.UTF_8.toString())

    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
            Text(text = decodedTitle, modifier = Modifier.padding(bottom = 8.dp))
            Text(text = decodedDescription)
        }
    }
}
