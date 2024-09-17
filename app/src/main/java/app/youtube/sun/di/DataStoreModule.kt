package app.youtube.sun.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import app.youtube.sun.data.preferences.UserPreferencesProto
import app.youtube.sun.data.preferences.UserPreferencesSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    private val dataStoreScope = CoroutineScope(Dispatchers.IO)
    private val Context.userPreferencesDataStore: DataStore<UserPreferencesProto.UserPreferences> by dataStore(
        fileName = "user_prefs.pb",
        serializer = UserPreferencesSerializer,
        scope = dataStoreScope
    )

    @Provides
    @Singleton
    fun provideUserPreferencesDataStore(
        @ApplicationContext context: Context
    ): DataStore<UserPreferencesProto.UserPreferences> {
        return context.userPreferencesDataStore
    }
}
