package ngoding.modol.mufi.ui.screen.movielist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ngoding.modol.mufi.tmdbapi.TmdbApi
import javax.inject.Inject


@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val tmdbApi: TmdbApi
) : ViewModel() {
    private val _state = MutableStateFlow(MovieListScreenState())
    val state = _state.asStateFlow()


    init {
        viewModelScope.launch {
            val popularResponse = tmdbApi.getPopular()
            popularResponse.results?.let { movies ->
                val credits = movies.map { async { tmdbApi.getMovieCredit(it.id!!) } }.awaitAll()
                _state.update {
                   it.copy(
                       movies = movies.mapIndexed { index, movie ->
                           val credit = credits[index]
                           MovieListScreenState.Movie(
                               title = movie.title ?: "-",
                               imageUrl = "https://image.tmdb.org/t/p/original/${movie.posterPath}",
                               rating = movie.voteAverage ?: 0f,
                               introduction = movie.overview ?: "",
                               casts = credit.casts.map {
                                   MovieListScreenState.Cast(
                                       name = it.originalName!!,
                                       imageUrl = "https://image.tmdb.org/t/p/original/${it.profilePath}",
                                   )
                               }
                           )
                       }
                   )
                }
            }
        }
    }

}
