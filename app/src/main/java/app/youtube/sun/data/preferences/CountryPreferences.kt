package app.youtube.sun.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "country_preferences")

@Singleton
class CountryPreferences @Inject constructor(private val context: Context) {

    private val COUNTRY_KEY = stringPreferencesKey("country_key")

    val selectedCountry: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[COUNTRY_KEY]
        }

    suspend fun saveCountry(countryCode: String) {
        context.dataStore.edit { preferences ->
            preferences[COUNTRY_KEY] = countryCode
        }
    }
}