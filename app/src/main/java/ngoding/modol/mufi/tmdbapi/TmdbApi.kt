package ngoding.modol.mufi.tmdbapi

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.cache.storage.FileStorage
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import ngoding.modol.mufi.BuildConfig
import java.io.File

class TmdbApi(
    cacheDir: File,
) {
    private val client = HttpClient(OkHttp) {
        install(Logging) {
            logger = Logger.ANDROID
            level = LogLevel.ALL
        }
        defaultRequest {
            url("https://api.themoviedb.org/3/")
            header(HttpHeaders.Authorization, "Bearer ${BuildConfig.TMDB_API_KEY}")
        }
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
        install(HttpCache) {
            val cacheDir = File(cacheDir, "net_cache")
            publicStorage(FileStorage(cacheDir))
        }
    }

    suspend fun getPopular(): PopularResponse {
        return client.get("movie/popular?language=en-US").body()
    }

    suspend fun getMovieCredit(movieId: Int): CreditResponse {
        return client.get("movie/$movieId/credits?language=en-US").body()
    }
}