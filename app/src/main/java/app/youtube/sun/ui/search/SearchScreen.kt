package app.youtube.sun.ui.search

import android.app.Activity
import android.content.Intent
import android.speech.RecognizerIntent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.navigation.NavHostController
import app.youtube.sun.R
import app.youtube.sun.data.models.Movie
import app.youtube.sun.ui.detail.VideoDetailViewModel
import app.youtube.sun.ui.list.VideoListScreen
import java.nio.charset.StandardCharsets
import java.util.Locale
import java.util.Base64

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavHostController,
    viewModel: SearchViewModel,
    videoDetailViewModel: VideoDetailViewModel
) {
    var searchText by remember { mutableStateOf(TextFieldValue("")) }
    val searchResults by viewModel.searchResults.collectAsState()
    val context = LocalContext.current

    val speechRecognizerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->
            val spokenText = result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            if (result.resultCode == Activity.RESULT_OK && spokenText != null) {
                searchText = TextFieldValue(spokenText[0] ?: "")
                viewModel.onSearchQuerySubmitted(searchText.text)
            } else {
                Toast.makeText(context, R.string.voice_input_failed, Toast.LENGTH_SHORT).show()
            }
        }
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                        OutlinedTextField(
                            value = searchText,
                            onValueChange = {
                                searchText = it
                                viewModel.onSearchQueryChanged(it.text)
                            },
                            placeholder = {
                                Text(
                                    text = stringResource(id = R.string.search_on_youtube),
                                    color = Color.Gray,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                containerColor = Color(0xFFF5F5F5),
                                focusedBorderColor = Color.Transparent,
                                unfocusedBorderColor = Color.Transparent,
                                focusedTextColor = Color.Black
                            ),
                            textStyle = MaterialTheme.typography.bodyLarge,
                            shape = CircleShape,
                            singleLine = true,
                            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
                            keyboardActions = KeyboardActions(
                                onSearch = {
                                    viewModel.onSearchQuerySubmitted(searchText.text)
                                }
                            )
                        )

                        IconButton(onClick = {
                            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                                putExtra(
                                    RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                                )
                                putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
                                putExtra(RecognizerIntent.EXTRA_PROMPT, R.string.speak_now)
                            }
                            speechRecognizerLauncher.launch(intent)
                        }) {
                            Icon(
                                imageVector = Icons.Default.Mic,
                                contentDescription = "Mic Search",
                                tint = Color.Black
                            )
                        }
                    }
                })
        }
    ) { innerPadding ->
        if (searchResults.isNotEmpty()) {
            val movies = searchResults.mapNotNull { item ->
                val videoId = item.id?.videoId
                val snippet = item.snippet
                if (videoId != null && snippet != null) {
                    Movie(
                        title = snippet.title ?: "",
                        thumbnailUrl = snippet.thumbnails?.high?.url ?: "",
                        id = videoId
                    )
                } else {
                    null
                }
            }
            VideoListScreen(
                movies = movies,
                loadMore = { viewModel.loadMore() },
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
    }
}