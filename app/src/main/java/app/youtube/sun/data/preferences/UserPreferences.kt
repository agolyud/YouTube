package app.youtube.sun.data.preferences

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import app.youtube.sun.data.preferences.UserPreferencesProto.UserPreferences
import com.google.protobuf.InvalidProtocolBufferException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject
import javax.inject.Singleton


private val Context.userPreferencesDataStore: DataStore<UserPreferences> by dataStore(
    fileName = "user_prefs.pb",
    serializer = UserPreferencesSerializer
)

@Singleton
class UserPreferences @Inject constructor(private val context: Context) {

    private val dataStore = context.userPreferencesDataStore

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


object UserPreferencesSerializer : Serializer<UserPreferences> {
    override val defaultValue: UserPreferences = UserPreferences.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): UserPreferences {
        try {
            return UserPreferences.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: UserPreferences, output: OutputStream) = t.writeTo(output)
}
