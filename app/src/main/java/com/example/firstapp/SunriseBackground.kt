// SunriseBackground.kt
package com.example.firstapp

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.lerp


@Composable
fun SunriseBackground(content: @Composable BoxScope.() -> Unit) {
    val riseProgress = remember { Animatable(0f) }

    val bobbingTransition = rememberInfiniteTransition()
    val bobbingOffset by bobbingTransition.animateFloat(
        initialValue = -10f,
        targetValue = 10f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    LaunchedEffect(Unit) {
        riseProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 4000, easing = FastOutSlowInEasing)
        )
    }

    val bottomColor = Color(0xFFE3F2FD)
    val topStartColor = Color(0xFFE3F2FD)
    val topEndColor = Color(0xFFFFEB3B)
    val topBackgroundColor = lerp(topStartColor, topEndColor, riseProgress.value)

    val screenHeight = LocalDensity.current.run { LocalConfiguration.current.screenHeightDp.dp.toPx() }
    val startOffsetY = screenHeight / 2
    val endOffsetY = screenHeight / 5
    val baseOffsetY = lerp(startOffsetY, endOffsetY, riseProgress.value)
    val finalOffsetY = with(LocalDensity.current) { (baseOffsetY + bobbingOffset).toDp() }

    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .background(topBackgroundColor)
                .align(Alignment.TopCenter)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .background(bottomColor)
                .align(Alignment.BottomCenter)
        )

        Box(
            modifier = Modifier
                .size(100.dp)
                .offset(y = finalOffsetY)
                .align(Alignment.TopCenter)
                .clip(CircleShape)
                .background(Color.White)
        )

        content()
    }
}
