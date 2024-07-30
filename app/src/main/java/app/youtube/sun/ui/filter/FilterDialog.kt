package app.youtube.sun.ui.filter

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import app.youtube.sun.R
import kotlinx.coroutines.launch

@Composable
fun FilterDialog(
    onDismiss: () -> Unit,
    selectedCountry: String,
    selectedLanguage: String,
    onCountryChange: (String) -> Unit,
    onLanguageChange: (String) -> Unit
) {
    val countries = listOf(
        stringResource(id = R.string.US) to "US",
        stringResource(id = R.string.RU) to "RU"
    )

    val languages = listOf(
        stringResource(id = R.string.english) to "en",
        stringResource(id = R.string.russian) to "ru"
    )

    val coroutineScope = rememberCoroutineScope()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = stringResource(id = R.string.filter), style = MaterialTheme.typography.titleLarge)
        },
        text = {
            Column(Modifier.verticalScroll(rememberScrollState())) {
                Text(stringResource(id = R.string.select_country))
                Column(Modifier.selectableGroup()) {
                    countries.forEach { (countryName, countryCode) ->
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .selectable(
                                    selected = selectedCountry == countryCode,
                                    onClick = {
                                        coroutineScope.launch {
                                            onCountryChange(countryCode)
                                        }
                                    }
                                )
                                .padding(16.dp)
                        ) {
                            RadioButton(
                                selected = selectedCountry == countryCode,
                                onClick = null
                            )
                            Text(
                                text = countryName,
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                    }
                }

                Text(stringResource(id = R.string.language), Modifier.padding(top = 16.dp))
                Column(Modifier.selectableGroup()) {
                    languages.forEach { (languageName, languageCode) ->
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .selectable(
                                    selected = selectedLanguage == languageCode,
                                    onClick = {
                                        coroutineScope.launch {
                                            onLanguageChange(languageCode)
                                        }
                                    }
                                )
                                .padding(16.dp)
                        ) {
                            RadioButton(
                                selected = selectedLanguage == languageCode,
                                onClick = null
                            )
                            Text(
                                text = languageName,
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            Text(
                text = stringResource(id = R.string.close),
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .clickable { onDismiss() },
                color = MaterialTheme.colorScheme.primary
            )
        },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    )
}

@Preview
@Composable
fun PreviewFilterDialog() {
    FilterDialog(
        onDismiss = {},
        selectedCountry = "US",
        selectedLanguage = "en",
        onCountryChange = {},
        onLanguageChange = {}
    )
}
