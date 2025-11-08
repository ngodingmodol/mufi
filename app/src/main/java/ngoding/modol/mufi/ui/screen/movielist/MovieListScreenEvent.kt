package ngoding.modol.mufi.ui.screen.movielist

sealed interface MovieListScreenEvent {
    data class ItemClicked(val index: Int): MovieListScreenEvent
}