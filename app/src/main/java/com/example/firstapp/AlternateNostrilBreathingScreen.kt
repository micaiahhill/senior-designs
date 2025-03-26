package com.example.firstapp

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun AlternateNostrilBreathingScreen(
    navController: NavController,
    inhaleDuration: Int = 4000, // 4 sec
    exhaleDuration: Int = 4000 // 4 sec
) {
    val singleCycleDuration = inhaleDuration + exhaleDuration
    val totalCycleDuration = singleCycleDuration * 2 // Inhale Left/Right, Exhale Left/Right

    val infiniteTransition = rememberInfiniteTransition()
    val progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = totalCycleDuration, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val elapsedMs = progress * totalCycleDuration

    // Determine phase
    val (phase, leftBarFill, rightBarFill, leftSeconds, rightSeconds) = when {
        elapsedMs < inhaleDuration -> {
            // Inhale Left
            val fill = (elapsedMs / inhaleDuration).coerceIn(0f, 1f)
            val sec = inhaleDuration / 1000 - (elapsedMs / 1000).toInt()
            Quadruple("Inhale Left", fill, 0f, sec, 0)
        }
        elapsedMs < inhaleDuration + exhaleDuration -> {
            // Exhale Right
            val exhaleElapsed = elapsedMs - inhaleDuration
            val fill = 1f - (exhaleElapsed / exhaleDuration).coerceIn(0f, 1f)
            val sec = exhaleDuration / 1000 - (exhaleElapsed / 1000).toInt()
            Quadruple("Exhale Right", 0f, fill, 0, sec)
        }
        elapsedMs < inhaleDuration * 2 + exhaleDuration -> {
            // Inhale Right
            val inhaleElapsed = elapsedMs - (inhaleDuration + exhaleDuration)
            val fill = (inhaleElapsed / inhaleDuration).coerceIn(0f, 1f)
            val sec = inhaleDuration / 1000 - (inhaleElapsed / 1000).toInt()
            Quadruple("Inhale Right", 0f, fill, 0, sec)
        }
        else -> {
            // Exhale Left
            val exhaleElapsed = elapsedMs - (inhaleDuration * 2 + exhaleDuration)
            val fill = 1f - (exhaleElapsed / exhaleDuration).coerceIn(0f, 1f)
            val sec = exhaleDuration / 1000 - (exhaleElapsed / 1000).toInt()
            Quadruple("Exhale Left", fill, 0f, sec, 0)
        }

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text("Alternate Nostril Breathing", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        Row(
            horizontalArrangement = Arrangement.spacedBy(32.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left Bar
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .width(40.dp)
                        .height(200.dp)
                        .background(Color.Gray.copy(alpha = 0.2f), RoundedCornerShape(8.dp))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(leftBarFill)
                            .align(Alignment.BottomCenter)
                            .background(Color.Blue.copy(alpha = 0.8f), RoundedCornerShape(8.dp))
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    if (leftSeconds > 0) "$leftSeconds s" else "",
                    fontSize = 18.sp
                )
            }

            // Right Bar
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .width(40.dp)
                        .height(200.dp)
                        .background(Color.Gray.copy(alpha = 0.2f), RoundedCornerShape(8.dp))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(rightBarFill)
                            .align(Alignment.BottomCenter)
                            .background(Color.Blue.copy(alpha = 0.8f), RoundedCornerShape(8.dp))
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    if (rightSeconds > 0) "$rightSeconds s" else "",
                    fontSize = 18.sp
                )
            }
        }

        // Phase Text
        Text(text = phase, fontSize = 20.sp)

        // Back Button
        Button(onClick = { navController.popBackStack() }) {
            Text("Back")
        }
    }
}

// Helper Quadruple data class (since Kotlin doesn’t have it natively)
data class Quadruple<A, B, C, D, E>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
    val fifth: E
)
