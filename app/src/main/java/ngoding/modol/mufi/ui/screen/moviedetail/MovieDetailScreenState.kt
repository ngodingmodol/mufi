package ngoding.modol.mufi.ui.screen.moviedetail

import ngoding.modol.mufi.ui.screen.movielist.MovieListScreenState

data class MovieDetailScreenState (
    val movies: List<MovieListScreenState.Movie>,
    val pageIndex: Int
)

