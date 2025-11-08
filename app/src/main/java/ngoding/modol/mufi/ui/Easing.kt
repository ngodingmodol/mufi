package ngoding.modol.mufi.ui

import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FloatSpringSpec
import androidx.compose.animation.core.Spring

val ElasticEase: Easing = object : Easing {
    val floatSpringSpec = FloatSpringSpec(
        dampingRatio = 0.5f,
        stiffness = Spring.StiffnessMedium
    )
    val dur = floatSpringSpec.getDurationNanos(0f, 1f, 0.5f)
    override fun transform(fraction: Float): Float {
        return floatSpringSpec.getValueFromNanos(
            playTimeNanos = (dur.toFloat() * fraction).toLong(),
            initialValue = 0f,
            targetValue = 1f,
            initialVelocity = 0f
        )
    }
}