package ngoding.modol.mufi.ui.screen.movielist

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import ngoding.modol.mufi.ui.LocalAnimatedContentScope

@Serializable
data object MovieListRoute

fun NavGraphBuilder.movieListRoute(
    onNavigateToDetail: (movies: List<MovieListScreenState.Movie>, index: Int) -> Unit
) {
    composable<MovieListRoute> {
        CompositionLocalProvider(LocalAnimatedContentScope provides this) {
            BackHandler(this.transition.isRunning) {}
            val viewModel: MovieListViewModel = hiltViewModel()
            val state by viewModel.state.collectAsState()
            MovieListScreen(
                state = state,
                event = {
                    when (it) {
                        is MovieListScreenEvent.ItemClicked -> onNavigateToDetail(
                            state.movies,
                            it.index
                        )
                    }
                }
            )
        }
    }
}


