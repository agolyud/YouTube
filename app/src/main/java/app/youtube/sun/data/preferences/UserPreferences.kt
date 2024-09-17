package app.youtube.sun.data.preferences

import androidx.datastore.core.DataStore
import app.youtube.sun.data.preferences.UserPreferencesProto.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferences @Inject constructor(
    private val dataStore: DataStore<UserPreferences>
) {

    val selectedCountry: Flow<String?> = dataStore.data
        .map { preferences ->
            preferences.countryCode
        }

    val selectedLanguage: Flow<String?> = dataStore.data
        .map { preferences ->
            preferences.languageCode
        }

    suspend fun saveCountry(countryCode: String) {
        dataStore.updateData { preferences ->
            preferences.toBuilder()
                .setCountryCode(countryCode)
                .build()
        }
    }

    suspend fun saveLanguage(languageCode: String) {
        dataStore.updateData { preferences ->
            preferences.toBuilder()
                .setLanguageCode(languageCode)
                .build()
        }
    }
}
