package ngoding.modol.mufi.ui.screen.movielist

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import ngoding.modol.mufi.ui.LocalAnimatedContentScope
import ngoding.modol.mufi.ui.LocalSharedTransitionScope
import ngoding.modol.mufi.ui.MufiPreview
import ngoding.modol.mufi.ui.theme.SharedTransitionKey
import kotlin.math.absoluteValue

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MoviePager(
    movies: List<MovieListScreenState.Movie>,
    pagerState: PagerState,
    onItemClick: (index: Int) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(24.dp),
) {
    val sharedTransitionScope = LocalSharedTransitionScope.current
    val animatedContentScope = LocalAnimatedContentScope.current
    HorizontalPager(
        state = pagerState,
        beyondViewportPageCount = 1,
        modifier = modifier,
    ) { pageIndex ->
        val movie = movies[pageIndex]
        val pageOffset = pagerState.getOffsetDistanceInPages(pageIndex)
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            with(sharedTransitionScope) {
                with(animatedContentScope) {
                    Box(
                        modifier = Modifier
                            .then(
                                if (pageIndex != pagerState.settledPage)
                                    Modifier
                                        .renderInSharedTransitionScopeOverlay(
                                            zIndexInOverlay = -1f
                                        )
                                        .animateEnterExit(
                                            enter = slideInHorizontally { if (pageIndex < pagerState.settledPage) it else -it },
                                            exit = slideOutHorizontally { if (pageIndex < pagerState.settledPage) it else -it }
                                        )
                                else
                                    Modifier
                                        .sharedBounds(
                                            sharedContentState = rememberSharedContentState(
                                                SharedTransitionKey.MovieCardContainer(
                                                    pageIndex
                                                )
                                            ),
                                            animatedVisibilityScope = animatedContentScope,
                                            resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds,
                                            enter = EnterTransition.None,
                                            exit = ExitTransition.None,
                                        )
                            )
                            .clickable { onItemClick(pageIndex) }
                            .drawWithContent {
                                translate(pageOffset * -size.width * 0.4f) {
                                    scale(1 - pageOffset.absoluteValue * 0.125f) {
                                        this@drawWithContent.drawContent()
                                    }
                                }
                            }
                            .clip(RoundedCornerShape(32.dp))
                            .background(MaterialTheme.colorScheme.surface.copy(alpha = 1 - pageOffset.absoluteValue * 0.125f))
                            .padding(contentPadding)
                            .fillMaxHeight(0.65f)
                            .fillMaxWidth(0.675f)
                            .align(Alignment.BottomCenter)
                    ) {

                        Column {
                            with(animatedContentScope) {
                                AsyncImage(
                                    model = movie.imageUrl,
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .then(
                                            if (pageIndex == pagerState.settledPage)
                                                Modifier
                                                    .renderInSharedTransitionScopeOverlay(
                                                        zIndexInOverlay = 1f
                                                    )
                                                    .animateEnterExit(
                                                        enter = scaleIn(animationSpec = tween(delayMillis = 150)),
                                                        exit = scaleOut(animationSpec = tween(150))
                                                    )
                                            else
                                                Modifier
                                        )
                                        .clip(RoundedCornerShape(24.dp))
                                        .weight(1f)
                                )
                            }
                            Spacer(Modifier.height(16.dp))
                            Text(
                                text = movie.title,
                                textAlign = TextAlign.Center,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .sharedBounds(
                                        sharedContentState = rememberSharedContentState(
                                            SharedTransitionKey.Title(pageIndex)
                                        ),
                                        animatedVisibilityScope = animatedContentScope,
                                        zIndexInOverlay = 1f,
                                    )
                                    .fillMaxWidth()
                                    .skipToLookaheadSize()
                            )
                            Spacer(Modifier.height(8.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = movie.rating,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        lineHeightStyle = LineHeightStyle(
                                            alignment = LineHeightStyle.Alignment.Proportional,
                                            trim = LineHeightStyle.Trim.None,
                                        )
                                    ),
                                    modifier = Modifier
                                        .sharedElement(
                                            sharedContentState = rememberSharedContentState(
                                                SharedTransitionKey.RatingText(pageIndex)
                                            ),
                                            animatedVisibilityScope = animatedContentScope,
                                            zIndexInOverlay = 1f,
                                        )
                                )
                                Spacer(Modifier.width(8.dp))
                                repeat(5) {
                                    Icon(
                                        imageVector = if (it < movie.starCount) Icons.Default.Star
                                        else Icons.Default.StarOutline,
                                        contentDescription = null,
                                        tint = if (it < movie.starCount) Color.Red else LocalContentColor.current,
                                        modifier = Modifier
                                            .sharedElement(
                                                sharedContentState = rememberSharedContentState(
                                                    SharedTransitionKey.RatingStar(it, pageIndex)
                                                ),
                                                animatedVisibilityScope = animatedContentScope,
                                                zIndexInOverlay = 1f,
                                            )
                                            .size(16.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    MufiPreview {
        val movies = MovieListScreenState.Movie.mocks()
        val pagerState = rememberPagerState { movies.size }
        MoviePager(
            movies = movies,
            pagerState = pagerState,
            onItemClick = {}
        )
    }
}