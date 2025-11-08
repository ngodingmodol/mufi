package ngoding.modol.mufi.ui.screen.moviedetail

import androidx.compose.animation.EnterExitState
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.ArcMode
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.EaseOutBack
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ngoding.modol.mufi.ui.ElasticEase
import ngoding.modol.mufi.ui.LocalAnimatedContentScope
import ngoding.modol.mufi.ui.LocalSharedTransitionScope
import ngoding.modol.mufi.ui.MufiPreview
import ngoding.modol.mufi.ui.screen.movielist.MovieListScreenState
import ngoding.modol.mufi.ui.theme.MufiTheme
import ngoding.modol.mufi.ui.theme.SharedTransitionKey

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MovieDetailScreen(
    state: MovieDetailScreenState,
) {
    val sharedTransitionScope = LocalSharedTransitionScope.current
    val animatedContentScope = LocalAnimatedContentScope.current
    val density = LocalDensity.current

    val movie = state.movies[state.pageIndex]
    val prevMovie = state.movies.getOrNull(state.pageIndex - 1)
    val nextMovie = state.movies.getOrNull(state.pageIndex + 1)
    val backMovieTopPadding = 64.dp
    val backMovieEnterTransitionDuration = 900
    val backMovieWidthWeight = 0.4f
    val backMovieDelayBetween = 75
    val backMovieDelayStart = 100
    val backMovieRadius = 16.dp
    val backMovieDarkenValue = 0.5f
    var buyTicketButtonHeight by remember { mutableStateOf(0) }
    Scaffold(
        contentWindowInsets =WindowInsets.navigationBars
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            with(animatedContentScope) {
                with(sharedTransitionScope) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black)
                            .sharedBounds(
                                sharedContentState = rememberSharedContentState(
                                    SharedTransitionKey.Background(state.pageIndex)
                                ),
                                animatedVisibilityScope = animatedContentScope
                            )
                    )
                    nextMovie?.let {
                        AsyncImage(
                            model = nextMovie.imageUrl,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            colorFilter = ColorFilter.tint(
                                Color.Black.copy(alpha = backMovieDarkenValue),
                                BlendMode.Darken
                            ),
                            modifier = Modifier
                                .fillMaxWidth(backMovieWidthWeight)
                                .aspectRatio(9f / 16f)
                                .align(Alignment.TopEnd)
                                .padding(top = backMovieTopPadding)
                                .animateEnterExit(
                                    enter = slideInVertically(
                                        animationSpec = tween(
                                            durationMillis = backMovieEnterTransitionDuration,
                                            delayMillis = backMovieDelayStart + backMovieDelayBetween * 2,
                                            easing = ElasticEase
                                        ),
                                        initialOffsetY = { it }
                                    ),
                                    exit = slideOutVertically(
                                        animationSpec = tween(delayMillis = 75)
                                    ) { it }
                                )
                                .clip(RoundedCornerShape(backMovieRadius))
                        )
                    }
                    prevMovie?.let {
                        AsyncImage(
                            model = prevMovie.imageUrl,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            colorFilter = ColorFilter.tint(
                                Color.Black.copy(alpha = backMovieDarkenValue),
                                BlendMode.Darken
                            ),
                            modifier = Modifier
                                .fillMaxHeight(backMovieWidthWeight)
                                .aspectRatio(9f / 16f)
                                .align(Alignment.TopStart)
                                .padding(top = backMovieTopPadding)
                                .animateEnterExit(
                                    enter = slideInVertically(
                                        animationSpec = tween(
                                            durationMillis = backMovieDelayStart + backMovieEnterTransitionDuration,
                                            delayMillis = backMovieDelayBetween * 1,
                                            easing = ElasticEase
                                        ),
                                        initialOffsetY = { it }
                                    ),
                                    exit = slideOutVertically(
                                        animationSpec = tween(delayMillis = 75)
                                    ) { it }
                                )
                                .clip(RoundedCornerShape(backMovieRadius))
                        )
                    }

                    AsyncImage(
                        model = movie.imageUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth(backMovieWidthWeight)
                            .aspectRatio(9f / 16f)
                            .align(Alignment.TopCenter)
                            .animateEnterExit(
                                enter = slideInVertically(
                                    animationSpec = tween(
                                        durationMillis = backMovieEnterTransitionDuration,
                                        delayMillis = backMovieDelayStart,
                                        easing = ElasticEase
                                    ),
                                    initialOffsetY = { it }
                                ),
                                exit = slideOutVertically(
                                    animationSpec = tween(delayMillis = 75)
                                ) { it }
                            )
                            .clip(RoundedCornerShape(backMovieRadius))
                    )
                    Box(
                        modifier = Modifier
                            .sharedBounds(
                                sharedContentState = rememberSharedContentState(
                                    SharedTransitionKey.MovieCardContainer(state.pageIndex)
                                ),
                                animatedVisibilityScope = animatedContentScope,
                                resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds,
                                zIndexInOverlay = 1f,
                                enter = EnterTransition.None,
                                exit = ExitTransition.None
                            )
                            .align(Alignment.BottomCenter)
                            .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                            .background(MaterialTheme.colorScheme.surface)
                            .fillMaxHeight(0.8f)
                            .fillMaxWidth()
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .padding(top = 32.dp)
                                .fillMaxSize()
                        ) {
                            Text(
                                text = movie.title,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onSurface,
                                style = MaterialTheme.typography.displaySmall,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .sharedBounds(
                                        sharedContentState = rememberSharedContentState(
                                            SharedTransitionKey.Title(state.pageIndex)
                                        ),
                                        animatedVisibilityScope = animatedContentScope,
                                        zIndexInOverlay = 1f,
                                        boundsTransform = { initialBounds, targetBounds ->
                                            keyframes {
                                                initialBounds at 0 using ArcMode.ArcAbove using FastOutSlowInEasing
                                                targetBounds at durationMillis
                                            }
                                        }
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
                                val durationEach = 500
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
                                                SharedTransitionKey.RatingText(state.pageIndex)
                                            ),
                                            animatedVisibilityScope = animatedContentScope,
                                            zIndexInOverlay = 1f,
                                            boundsTransform = { init, target ->
                                                if (animatedContentScope.transition.targetState == EnterExitState.Visible) {
                                                    keyframes {
                                                        durationMillis = durationEach
                                                        init at 0 using ArcMode.ArcAbove using CubicBezierEasing(
                                                            0.34f,
                                                            1.25f,
                                                            0.64f,
                                                            1f
                                                        )
                                                        target at durationMillis
                                                    }
                                                } else {
                                                    spring(
                                                        stiffness = Spring.StiffnessMediumLow,
                                                        visibilityThreshold = Rect.VisibilityThreshold
                                                    )
                                                }
                                            }
                                        )
                                )
                                Spacer(Modifier.width(8.dp))
                                repeat(5) { starIndex ->
                                    Icon(
                                        imageVector = if (starIndex < movie.starCount) Icons.Default.Star
                                        else Icons.Default.StarOutline,
                                        contentDescription = null,
                                        tint = if (starIndex < movie.starCount) Color.Red else LocalContentColor.current,
                                        modifier = Modifier
                                            .sharedElement(
                                                sharedContentState = rememberSharedContentState(
                                                SharedTransitionKey.RatingStar(starIndex, state.pageIndex)
                                                ),
                                                animatedVisibilityScope = animatedContentScope,
                                                zIndexInOverlay = 1f,
                                                boundsTransform = { init, target ->
                                                    if (animatedContentScope.transition.targetState == EnterExitState.Visible) {
                                                        keyframes {
                                                            val delayBetween = 75
                                                            val startAt = starIndex * delayBetween
                                                            durationMillis = startAt + durationEach
                                                            init at startAt using ArcMode.ArcAbove using CubicBezierEasing(
                                                                0.34f,
                                                                1.25f,
                                                                0.64f,
                                                                1f
                                                            )
                                                            target at durationMillis
                                                        }
                                                    } else {
                                                        spring(
                                                            stiffness = Spring.StiffnessMediumLow,
                                                            visibilityThreshold = Rect.VisibilityThreshold
                                                        )

                                                    }
                                                },
                                            )
                                            .size(16.dp)
                                    )
                                }
                            }
                            Column(
                                modifier = Modifier
                                    .verticalScroll(rememberScrollState())
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .padding(
                                        top = 16.dp,
                                        bottom = 16.dp + with(density) { buyTicketButtonHeight.toDp() }
                                    )
                                    .animateEnterExit(
                                        enter = fadeIn(
                                            tween(
                                                delayMillis = 250,
                                                durationMillis = 750
                                            )
                                        )
                                                + slideInVertically(
                                            tween(
                                                delayMillis = 250,
                                                durationMillis = 750,
                                                easing = CubicBezierEasing(
                                                    0.34f,
                                                    1.25f,
                                                    0.64f,
                                                    1f
                                                )
                                            )
                                        ) { it }
                                    )
                            ) {
                                Spacer(Modifier.height(16.dp))
                                Text(
                                    text = "Actors",
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 16.dp)
                                )
                                Spacer(Modifier.height(16.dp))

                                val visibleStates = remember {
                                    mutableStateListOf(
                                        *List(3) { Animatable(0f) }.toTypedArray()
                                    )
                                }
                                LaunchedEffect(Unit) {
                                    delay(500)
                                    (0..2).forEachIndexed { index, _ ->
                                        launch {
                                            visibleStates[index].animateTo(
                                                targetValue = 1f,
                                                animationSpec = tween(durationMillis = 500, easing = EaseOutBack)
                                            )
                                        }
                                        delay(250)
                                    }
                                }
                                LazyRow(
                                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                                    contentPadding = PaddingValues(horizontal = 16.dp)
                                ) {
                                    itemsIndexed(
                                        items = movie.casts,
                                        key = { _, item -> item.name },
                                    ) { index, item ->
                                        val progress = if (LocalInspectionMode.current) 1f
                                        else visibleStates.getOrNull(index)?.value ?: 1f
                                        Column(
                                            modifier = Modifier
                                                .width(116.dp)
                                                .graphicsLayer {
                                                    alpha = progress
                                                    translationY =  (1f - progress) * 125f
                                                }
                                        ) {
                                            AsyncImage(
                                                model = item.imageUrl,
                                                contentDescription = null,
                                                contentScale = ContentScale.Crop,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .aspectRatio(1f)
                                                    .clip(RoundedCornerShape(8.dp))
                                            )
                                            Spacer(Modifier.height(4.dp))
                                            Text(
                                                text = item.name,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis,
                                            )
                                        }
                                    }
                                }
                                Spacer(Modifier.height(16.dp))
                                Text(
                                    text = "Introduction",
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 16.dp)
                                )
                                Spacer(Modifier.height(16.dp))
                                Text(
                                    text = movie.introduction,
                                    modifier = Modifier
                                        .padding(horizontal = 16.dp)
                                )
                            }
                        }
                        Box(
                            modifier = Modifier
                                .onGloballyPositioned { layoutCoordinates ->
                                    buyTicketButtonHeight = layoutCoordinates.size.height
                                }
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                                .sharedBounds(
                                    sharedContentState = rememberSharedContentState(SharedTransitionKey.BuyTicketButton),
                                    animatedVisibilityScope = animatedContentScope,
                                    zIndexInOverlay = 1f,
                                    resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds,
                                    enter = EnterTransition.None,
                                    exit = ExitTransition.None,
                                )
                                .align(Alignment.BottomCenter)
                                .padding(bottom = 16.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(MaterialTheme.colorScheme.inverseSurface)
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "BUY TICKET",
                                color = MaterialTheme.colorScheme.inverseOnSurface,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .sharedElement(
                                        sharedContentState = rememberSharedContentState(
                                            SharedTransitionKey.BuyTicketText
                                        ),
                                        animatedVisibilityScope = animatedContentScope,
                                        zIndexInOverlay = 1f
                                    )
                            )
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
        MufiTheme {
            MovieDetailScreen(
                state = MovieDetailScreenState(
                    movies = MovieListScreenState.Movie.mocks(),
                    pageIndex = 1
                )
            )
        }
    }
}