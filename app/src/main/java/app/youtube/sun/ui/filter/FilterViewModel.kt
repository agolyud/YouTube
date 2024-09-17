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
            val savedCountry = userPreferences.selectedCountry.first()
            if (savedCountry.isNullOrEmpty()) {
                var deviceCountry = Locale.getDefault().country.uppercase(Locale.getDefault())
                if (!supportedCountries.contains(deviceCountry)) {
                    deviceCountry = "US"
                }
                _selectedCountry.value = deviceCountry
                userPreferences.saveCountry(deviceCountry)
            } else {
                _selectedCountry.value = savedCountry
            }

            val savedLanguage = userPreferences.selectedLanguage.first()
            if (savedLanguage.isNullOrEmpty()) {
                var deviceLanguage = Locale.getDefault().language.lowercase(Locale.getDefault())
                if (!supportedLanguages.contains(deviceLanguage)) {
                    deviceLanguage = "en"
                }
                _selectedLanguage.value = deviceLanguage
                userPreferences.saveLanguage(deviceLanguage)
            } else {
                _selectedLanguage.value = savedLanguage
            }
        }
    }

    fun updateCountry(countryCode: String) {
        viewModelScope.launch {
            val validCountryCode = if (supportedCountries.contains(countryCode.uppercase(Locale.getDefault()))) {
                countryCode.uppercase(Locale.getDefault())
            } else {
                "US"
            }
            userPreferences.saveCountry(validCountryCode)
            _selectedCountry.value = validCountryCode
        }
    }

    fun updateLanguage(languageCode: String) {
        viewModelScope.launch {
            val validLanguageCode = if (supportedLanguages.contains(languageCode.lowercase(Locale.getDefault()))) {
                languageCode.lowercase(Locale.getDefault())
            } else {
                "en"
            }
            userPreferences.saveLanguage(validLanguageCode)
            _selectedLanguage.value = validLanguageCode
        }
    }
}
