package ngoding.modol.mufi.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ngoding.modol.mufi.tmdbapi.TmdbApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTmdbApi(
        @ApplicationContext applicationContext: Context,
    ): TmdbApi {
        return TmdbApi(applicationContext.cacheDir)
    }
}