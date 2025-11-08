package ngoding.modol.mufi.ui.screen.movielist

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import ngoding.modol.mufi.ui.LocalAnimatedContentScope
import ngoding.modol.mufi.ui.LocalSharedTransitionScope
import ngoding.modol.mufi.ui.theme.SharedTransitionKey

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun BackgroundPager(
    movies: List<MovieListScreenState.Movie>,
    pagerState: PagerState,
    modifier: Modifier = Modifier
) {
    val sharedTransitionScope = LocalSharedTransitionScope.current
    val animatedContentScope = LocalAnimatedContentScope.current

    val surfaceColor = MaterialTheme.colorScheme.surface

    HorizontalPager(
        state = pagerState,
        beyondViewportPageCount = 1,
        modifier = modifier,
    ) { pageIndex ->
        val pageOffset = pagerState.getOffsetDistanceInPages(pageIndex)
        val movie = movies[pageIndex]
        with(sharedTransitionScope) {
            AsyncImage(
                model = movie.imageUrl,
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .sharedBounds(
                        sharedContentState = rememberSharedContentState(
                            SharedTransitionKey.Background(pageIndex)
                        ),
                        animatedVisibilityScope = animatedContentScope
                    )
                    .drawWithContent {
                        translate(pageOffset * -size.width) {
                            val clipPath = Path().apply {
                                addRect(
                                    Rect(
                                        left = 0f,
                                        top = size.height,
                                        right = size.width - pageOffset * size.width,
                                        bottom = 0f
                                    )
                                )
                            }
                            clipPath(clipPath) {
                                this@drawWithContent.drawContent()
                                val gradient = Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        surfaceColor,
                                        surfaceColor
                                    ),
                                    startY = size.height * 0.4f,
                                    endY = size.height
                                )
                                drawRect(gradient)
                            }
                        }
                    }
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    _root_ide_package_.ngoding.modol.mufi.ui.MufiPreview {
        val movies = MovieListScreenState.Movie.mocks()
        val pagerState = rememberPagerState { movies.size }
        BackgroundPager(
            movies = movies,
            pagerState = pagerState
        )
    }
}