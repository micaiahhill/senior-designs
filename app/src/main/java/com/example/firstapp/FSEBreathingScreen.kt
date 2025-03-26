package com.example.firstapp

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun FSEBreathingScreen(navController: NavController) {
    val totalDuration = 19000  // Total duration: 4 + 7 + 8 = 19 sec

    val infiniteTransition = rememberInfiniteTransition()

    // Animate progress from 0f to 1f over 19 seconds
    val progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = totalDuration, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val elapsedMs = progress * totalDuration

    // Determine breathing phase and seconds remaining
    val (phase, secondsRemaining) = when {
        elapsedMs < 4000 -> {
            val sec = 1 + (elapsedMs / 1000).toInt()
            "Inhale" to sec
        }
        elapsedMs < 11000 -> {
            val holdElapsed = elapsedMs - 4000
            val sec = 1 + (holdElapsed / 1000).toInt()
            "Hold" to sec
        }
        else -> {
            val exhaleElapsed = elapsedMs - 11000
            val sec = 1 + (exhaleElapsed / 1000).toInt()
            "Exhale" to sec
        }
    }

    // Calculate scale factor for circle
    val scaleFactor = when (phase) {
        "Inhale" -> lerp(1f, 1.5f, elapsedMs / 4000f)  // Expanding
        "Hold" -> 1.5f  // Hold max size
        "Exhale" -> lerp(1.5f, 1f, (elapsedMs - 11000f) / 8000f)  // Contracting
        else -> 1f
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text("4-7-8 Breathing", style = MaterialTheme.typography.headlineMedium)

        // Circle animation with timer inside
        Box(
            modifier = Modifier
                .size(200.dp)
                .scale(scaleFactor)
                .background(Color.Blue.copy(alpha = 0.5f), shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "$secondsRemaining",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White
            )
        }

        // Phase text
        Text(text = phase, style = MaterialTheme.typography.headlineSmall)

        // Back button
        Button(onClick = { navController.popBackStack() }) {
            Text("Back")
        }
    }
}

// Helper function for linear interpolation
fun lerp(start: Float, end: Float, fraction: Float): Float {
    return start + (end - start) * fraction.coerceIn(0f, 1f)
}
