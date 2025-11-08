@file:OptIn(ExperimentalSharedTransitionApi::class)

package ngoding.modol.mufi

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import ngoding.modol.mufi.ui.LocalSharedTransitionScope
import ngoding.modol.mufi.ui.screen.moviedetail.movieDetailRoute
import ngoding.modol.mufi.ui.screen.moviedetail.navigateToMovieDetail
import ngoding.modol.mufi.ui.screen.movielist.MovieListRoute
import ngoding.modol.mufi.ui.screen.movielist.movieListRoute
import ngoding.modol.mufi.ui.theme.MufiTheme

@Composable
fun MufiContent() {
    MufiTheme {
        SharedTransitionLayout {
            CompositionLocalProvider(
                LocalSharedTransitionScope provides this
            ) {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = MovieListRoute,
                    enterTransition = { EnterTransition.None },
                    exitTransition = { ExitTransition.None },
                ) {
                    movieListRoute(
                        onNavigateToDetail = navController::navigateToMovieDetail
                    )
                    movieDetailRoute()
                }
            }
        }
    }
}
