package ngoding.modol.mufi.ui

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.compositionLocalOf

val LocalAnimatedContentScope = compositionLocalOf<AnimatedContentScope> { noLocalProvidedFor("LocalAnimatedContentScope") }
@OptIn(ExperimentalSharedTransitionApi::class)
val LocalSharedTransitionScope = compositionLocalOf<SharedTransitionScope> { noLocalProvidedFor("LocalSharedTransitionScope") }

private fun noLocalProvidedFor(name: String): Nothing {
    error("CompositionLocal $name not present")
}
