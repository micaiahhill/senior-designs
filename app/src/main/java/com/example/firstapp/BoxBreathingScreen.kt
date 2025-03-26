package com.example.firstapp

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


@Composable
fun BoxBreathingScreen(navController: NavController) {
    val squareSize = 200.dp

    // Infinite transition to control the animation
    val infiniteTransition = rememberInfiniteTransition()
    val progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 16000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    // Determine current phase (inhale, hold, exhale, hold)
    val phase = when {
        progress < 0.25f -> "Inhale"
        progress < 0.5f -> "Hold"
        progress < 0.75f -> "Exhale"
        else -> "Hold"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text("Box Breathing", style = MaterialTheme.typography.headlineMedium)

        // Canvas for square + moving circle
        Box(
            modifier = Modifier.size(squareSize)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val canvasWidth = size.width
                val canvasHeight = size.height

                // Draw the square
                drawRect(
                    color = Color.Gray,
                    size = size,
                    style = androidx.compose.ui.graphics.drawscope.Stroke(
                        width = 4.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                )

                // Calculate moving circle position based on progress
                val perimeter = 4 * canvasWidth
                val position = progress * perimeter

                val (x, y) = when {
                    position <= canvasWidth -> {
                        // Top side (left → right)
                        Offset(position, 0f)
                    }
                    position <= canvasWidth + canvasHeight -> {
                        // Right side (top → bottom)
                        Offset(canvasWidth, position - canvasWidth)
                    }
                    position <= 2 * canvasWidth + canvasHeight -> {
                        // Bottom side (right → left)
                        Offset(2 * canvasWidth + canvasHeight - position, canvasHeight)
                    }
                    else -> {
                        // Left side (bottom → top)
                        Offset(0f, perimeter - position)
                    }
                }

                // Draw the moving circle (indicator)
                drawCircle(
                    color = Color.Blue,
                    radius = 12.dp.toPx(),
                    center = Offset(x, y)
                )
            }
        }

        // Phase text
        Text(text = phase, style = MaterialTheme.typography.headlineSmall)

        // Back button
        Button(onClick = { navController.popBackStack() }) {
            Text("Back")
        }
    }
}
