package app.youtube.sun.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import app.youtube.sun.data.preferences.UserPreferencesProto.UserPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

private val dataStoreScope = CoroutineScope(Dispatchers.IO)

val Context.userPreferencesDataStore: DataStore<UserPreferences> by dataStore(
    fileName = "user_prefs.pb",
    serializer = UserPreferencesSerializer,
    scope = dataStoreScope
)
