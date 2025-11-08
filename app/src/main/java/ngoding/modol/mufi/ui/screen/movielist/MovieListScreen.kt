package ngoding.modol.mufi.ui.screen.movielist

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import ngoding.modol.mufi.ui.LocalAnimatedContentScope
import ngoding.modol.mufi.ui.LocalSharedTransitionScope
import ngoding.modol.mufi.ui.MufiPreview
import ngoding.modol.mufi.ui.theme.SharedTransitionKey

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MovieListScreen(
    state: MovieListScreenState,
    event: (MovieListScreenEvent) -> Unit
) {
    val sharedTransitionScope = LocalSharedTransitionScope.current
    val animatedContentScope = LocalAnimatedContentScope.current
    LocalContext.current

    Scaffold(
        contentWindowInsets = WindowInsets.navigationBars
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            val scope = rememberCoroutineScope()
            val density = LocalDensity.current

            val backgroundPagerState = rememberPagerState { state.movies.size }
            val moviePagerState = rememberPagerState { state.movies.size }

            var buyTicketButtonHeight by remember { mutableIntStateOf(0) }

            LaunchedEffect(Unit) {
                snapshotFlow {
                    val currentPage = moviePagerState.currentPage
                    val offsetFraction = moviePagerState.currentPageOffsetFraction
                    currentPage to offsetFraction
                }.collect { (page, offset) ->
                    scope.launch {
                        backgroundPagerState.scrollToPage(page, offset)
                    }
                }
            }

            BackgroundPager(
                movies = state.movies,
                pagerState = backgroundPagerState
            )

            MoviePager(
                movies = state.movies,
                contentPadding = PaddingValues(
                    start = 24.dp,
                    top = 24.dp,
                    end = 24.dp,
                    bottom = 24.dp + with(density) { buyTicketButtonHeight.toDp() }
                ),
                pagerState = moviePagerState,
                onItemClick = {
                    event(MovieListScreenEvent.ItemClicked(it))
                }
            )
            with(sharedTransitionScope) {
                Box(
                    modifier = Modifier
                        .onGloballyPositioned { layoutCoordinates ->
                            buyTicketButtonHeight = layoutCoordinates.size.height
                        }
                        .fillMaxWidth(0.65f)
                        .sharedBounds(
                            sharedContentState = rememberSharedContentState(
                                SharedTransitionKey.BuyTicketButton
                            ),
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
                                zIndexInOverlay = 1f,
                            )
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun MovieListScreenPreview() {
    MufiPreview {
        MovieListScreen(
            state = MovieListScreenState.mock(),
            event = {}
        )
    }
}