package ngoding.modol.mufi.ui

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import coil3.ColorImage
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.AsyncImagePreviewHandler
import coil3.compose.LocalAsyncImagePreviewHandler

@SuppressLint("UnusedContentLambdaTargetStateParameter")
@OptIn(ExperimentalCoilApi::class, ExperimentalSharedTransitionApi::class)
@Composable
fun MufiPreview(content: @Composable () -> Unit) {
    val previewHandler = AsyncImagePreviewHandler {
        val color = when (it.data as? String) {
            "red" -> Color.Red
            "green" -> Color.Green
            "blue" -> Color.Blue
            else -> Color.Magenta
        }.toArgb()
        ColorImage(color)
    }
    CompositionLocalProvider(LocalAsyncImagePreviewHandler provides previewHandler) {
        SharedTransitionLayout {
            CompositionLocalProvider(LocalSharedTransitionScope provides this) {
                AnimatedContent(Unit) {
                    CompositionLocalProvider(LocalAnimatedContentScope provides this) {
                        content()
                    }
                }
            }
        }
    }
}
