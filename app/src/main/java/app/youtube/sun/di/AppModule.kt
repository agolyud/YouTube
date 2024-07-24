package app.youtube.sun.di

import android.content.Context
import app.youtube.sun.data.preferences.CountryPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCountryPreferences(@ApplicationContext context: Context): CountryPreferences {
        return CountryPreferences(context)
    }
}