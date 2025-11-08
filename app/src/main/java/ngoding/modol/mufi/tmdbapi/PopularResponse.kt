package ngoding.modol.mufi.tmdbapi

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PopularResponse(
    val page: Int? = null,
    val results: List<Movie>? = null
)

@Serializable
data class Movie(
    val id: Int? = null,
    val title: String? = null,
    @SerialName("backdrop_path")
    val backdropPath: String? = null,
    @SerialName("poster_path")
    val posterPath: String? = null,
    @SerialName("vote_average")
    val voteAverage: Float? = null,
    val overview: String? = null,
)
