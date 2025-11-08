package ngoding.modol.mufi.ui.screen.movielist

import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import kotlinx.serialization.Serializable
import java.util.Locale
import kotlin.math.ceil

data class MovieListScreenState(
    val movies: List<Movie> = emptyList()
) {

    @Serializable
    data class Cast(
        val name: String,
        val imageUrl: String
    ) {
        companion object {
            fun mocks() = listOf(
                Cast(
                    name = "Edward Norton",
                    imageUrl = "red"
                ),
                Cast(
                    name = "Brad Pitt",
                    imageUrl = "green"
                ),
                Cast(
                    name = "Helena Bonham Carter",
                    imageUrl = "blue"
                ),
            )
        }
    }

    @Serializable
    data class Movie(
        val title: String,
        val imageUrl: String,
        val rating: String,
        val starCount: Int,
        val introduction: String,
        val casts: List<Cast>,
    ) {

        constructor(
            title: String,
            imageUrl: String,
            rating: Float,
            introduction: String,
            casts: List<Cast>
        ) : this(
            title = title,
            imageUrl = imageUrl,
            rating = String.format(Locale.ENGLISH, "%.1f", rating),
            starCount = ceil(rating / 2).toInt(),
            introduction = introduction,
            casts = casts
        )

        companion object {
            fun mocks(): List<Movie> {
                val introduction = LoremIpsum().values.first()
                return listOf(
                    Movie(
                        title = "Ant-Man and the Wasp: Quantumania",
                        introduction = introduction,
                        imageUrl = "red",
                        rating = 5.5f,
                        casts = Cast.mocks(),
                    ),
                    Movie(
                        title = "The Super Mario Bros. Movie",
                        introduction = introduction,
                        imageUrl = "green",
                        rating = 6f,
                        casts = Cast.mocks(),
                    ),
                    Movie(
                        title = "Shazam! Fury of the Gods",
                        introduction = introduction,
                        imageUrl = "blue",
                        rating = 7.5f,
                        casts = Cast.mocks(),
                    ),
                )
            }
        }
    }

    companion object {
        fun mock() = MovieListScreenState(
            movies = Movie.mocks()
        )
    }
}