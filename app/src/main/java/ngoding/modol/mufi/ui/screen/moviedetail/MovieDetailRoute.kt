package ngoding.modol.mufi.ui.screen.moviedetail

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import ngoding.modol.mufi.ui.LocalAnimatedContentScope
import ngoding.modol.mufi.ui.screen.movielist.MovieListScreenState
import ngoding.modol.mufi.ui.navtype.listNavType
import kotlin.reflect.typeOf

@Serializable
data class MovieDetailRoute(
    val movies: List<MovieListScreenState.Movie>,
    val index: Int,
)

fun NavController.navigateToMovieDetail(movies: List<MovieListScreenState.Movie>, index: Int) {
    navigate(
        route = MovieDetailRoute(
            movies = movies,
            index = index,
        )
    )
}

fun NavGraphBuilder.movieDetailRoute() {
    composable<MovieDetailRoute>(
        typeMap = mapOf(typeOf<List<MovieListScreenState.Movie>>() to listNavType<MovieListScreenState.Movie>())
    ) {
        CompositionLocalProvider(LocalAnimatedContentScope provides this) {
            val route: MovieDetailRoute = it.toRoute()
            BackHandler(this.transition.isRunning) {}
            MovieDetailScreen(
                state = MovieDetailScreenState(
                    movies = route.movies,
                    pageIndex = route.index
                )
            )
        }
    }
}
