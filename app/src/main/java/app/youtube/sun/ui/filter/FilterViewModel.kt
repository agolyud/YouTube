package app.youtube.sun.ui.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.youtube.sun.data.preferences.CountryPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(
    private val countryPreferences: CountryPreferences
) : ViewModel() {

    private val _selectedCountry = MutableStateFlow("US")
    val selectedCountry: StateFlow<String> get() = _selectedCountry

    init {
        viewModelScope.launch {
            _selectedCountry.value = countryPreferences.selectedCountry.first() ?: "US"
        }
    }

    fun updateCountry(countryCode: String) {
        viewModelScope.launch {
            countryPreferences.saveCountry(countryCode)
            _selectedCountry.value = countryCode
        }
    }
}