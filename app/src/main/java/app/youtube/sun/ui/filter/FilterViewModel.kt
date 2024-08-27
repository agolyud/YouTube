package app.youtube.sun.ui.filter

import android.app.Application
import android.content.res.Configuration
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

    init {
        viewModelScope.launch {
            _selectedCountry.value = userPreferences.selectedCountry.first() ?: "US"
            val savedLanguage = userPreferences.selectedLanguage.first()
            if (savedLanguage != null) {
                _selectedLanguage.value = savedLanguage
                updateLocale(savedLanguage)
            } else {
                val deviceLanguage = Locale.getDefault().language
                _selectedLanguage.value = deviceLanguage
                userPreferences.saveLanguage(deviceLanguage)
            }
        }
    }

    fun updateCountry(countryCode: String) {
        viewModelScope.launch {
            userPreferences.saveCountry(countryCode)
            _selectedCountry.value = countryCode
        }
    }

    fun updateLanguage(languageCode: String) {
        viewModelScope.launch {
            userPreferences.saveLanguage(languageCode)
            _selectedLanguage.value = languageCode
            updateLocale(languageCode)
        }
    }

    private fun updateLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        getApplication<Application>().resources.updateConfiguration(config, getApplication<Application>().resources.displayMetrics)
    }
}