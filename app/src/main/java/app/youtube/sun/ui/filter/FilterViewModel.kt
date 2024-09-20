package app.youtube.sun.ui.filter

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import app.youtube.sun.data.preferences.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(
    private val userPreferences: UserPreferences,
    application: Application
) : AndroidViewModel(application) {

    private val _selectedCountry = MutableStateFlow<String?>(null)
    val selectedCountry: StateFlow<String?> get() = _selectedCountry

    private val _selectedLanguage = MutableStateFlow<String?>(null)
    val selectedLanguage: StateFlow<String?> get() = _selectedLanguage

    private val supportedLanguages = listOf("en", "ru")
    private val supportedCountries = listOf("US", "RU")

    init {
        viewModelScope.launch {
            _selectedCountry.value = validSave(
                userPreferences.selectedCountry.first(),
                Locale.getDefault().country.uppercase(Locale.getDefault()),
                supportedCountries,
                "US",
                userPreferences::saveCountry
            )

            _selectedLanguage.value = validSave(
                userPreferences.selectedLanguage.first(),
                Locale.getDefault().language.lowercase(Locale.getDefault()),
                supportedLanguages,
                "en",
                userPreferences::saveLanguage
            )
        }
    }

    private suspend fun validSave(
        savedValue: String?,
        deviceValue: String,
        supportedValues: List<String>,
        defaultValue: String,
        saveAction: suspend (String) -> Unit
    ): String {
        val validValue = if (savedValue.isNullOrEmpty()) {
            if (supportedValues.contains(deviceValue)) deviceValue else defaultValue
        } else {
            savedValue
        }
        saveAction(validValue)
        return validValue
    }

    fun updateCountry(countryCode: String) {
        viewModelScope.launch {
            val validCountryCode = getValidValue(
                countryCode.uppercase(Locale.getDefault()),
                supportedCountries,
                "US"
            )
            userPreferences.saveCountry(validCountryCode)
            _selectedCountry.value = validCountryCode
        }
    }

    fun updateLanguage(languageCode: String) {
        viewModelScope.launch {
            val validLanguageCode = getValidValue(
                languageCode.lowercase(Locale.getDefault()),
                supportedLanguages,
                "en"
            )
            userPreferences.saveLanguage(validLanguageCode)
            _selectedLanguage.value = validLanguageCode
        }
    }

    private fun getValidValue(
        value: String,
        supportedValues: List<String>,
        defaultValue: String
    ): String {
        return if (supportedValues.contains(value)) {
            value
        } else {
            defaultValue
        }
    }
}

