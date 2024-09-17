package app.youtube.sun.di

import androidx.datastore.core.DataStore
import app.youtube.sun.data.preferences.UserPreferences
import app.youtube.sun.data.preferences.UserPreferencesProto
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCountryPreferences(
        dataStore: DataStore<UserPreferencesProto.UserPreferences>
    ): UserPreferences {
        return UserPreferences(dataStore)
    }
}