package ngoding.modol.mufi.ui.theme

sealed interface SharedTransitionKey {
    object BuyTicketButton: SharedTransitionKey
    object BuyTicketText: SharedTransitionKey
    data class Background(val pageIndex: Int): SharedTransitionKey
    data class MovieCardContainer(val pageIndex: Int): SharedTransitionKey
    data class Title(val pageIndex: Int): SharedTransitionKey
    data class RatingText(val pageIndex: Int): SharedTransitionKey
    data class RatingStar(val starIndex: Int, val pageIndex: Int): SharedTransitionKey
}