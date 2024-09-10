package app.youtube.sun.ui.filter

import android.app.Application
import android.content.res.Configuration
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import app.youtube.sun.data.preferences.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
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
            val savedCountry = userPreferences.selectedCountry.first()
            if (savedCountry.isNullOrEmpty()) {
                val deviceCountry = Locale.getDefault().country
                _selectedCountry.value = deviceCountry
                userPreferences.saveCountry(deviceCountry)
            } else {
                _selectedCountry.value = savedCountry
            }

            val savedLanguage = userPreferences.selectedLanguage.first()
            if (savedLanguage.isNullOrEmpty()) {
                val deviceLanguage = Locale.getDefault().language
                _selectedLanguage.value = deviceLanguage
                userPreferences.saveLanguage(deviceLanguage)
            } else {
                _selectedLanguage.value = savedLanguage
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
        }
    }
}
