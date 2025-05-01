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
import kotlinx.coroutines.launch

data class DiaphragmaticPace(val label: String, val inhale: Int, val exhale: Int)

@Composable
fun DiaphragmaticBreathingScreen(navController: NavController) {
    val paceOptions = listOf(
        DiaphragmaticPace("Fast (3-3)", 3000, 3000),
        DiaphragmaticPace("Medium (4-6)", 4000, 6000),
        DiaphragmaticPace("Slow (6-8)", 6000, 8000)
    )

    var selectedPace by remember { mutableStateOf(paceOptions[1]) }
    var expanded by remember { mutableStateOf(false) }
    var started by remember { mutableStateOf(false) }

    val totalDuration = selectedPace.inhale + selectedPace.exhale
    val animatableProgress = remember { Animatable(0f) }
    val progress = animatableProgress.value
    val scope = rememberCoroutineScope()

    // Control animation
    LaunchedEffect(selectedPace, started) {
        if (started) {
            animatableProgress.stop()
            animatableProgress.snapTo(0f)
            animatableProgress.animateTo(
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = totalDuration, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                )
            )
        } else {
            animatableProgress.stop()
            animatableProgress.snapTo(0f)
        }
    }

    val elapsedMs = progress * totalDuration

    val (phase, secondsRemaining) = when {
        elapsedMs < selectedPace.inhale -> {
            val sec = 1 + (elapsedMs / 1000).toInt()
            "Inhale" to sec
        }
        else -> {
            val exhaleElapsed = elapsedMs - selectedPace.inhale
            val sec = 1 + (exhaleElapsed / 1000).toInt()
            "Exhale" to sec
        }
    }

    val scaleFactor = when (phase) {
        "Inhale" -> lerp(1f, 1.5f, elapsedMs / selectedPace.inhale.toFloat())
        "Exhale" -> lerp(1.5f, 1f, (elapsedMs - selectedPace.inhale) / selectedPace.exhale.toFloat())
        else -> 1f
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text("Diaphragmatic Breathing", style = MaterialTheme.typography.headlineMedium)

        // Dropdown for pace selection
        Box {
            OutlinedButton(onClick = { expanded = true }, enabled = !started) {
                Text("Pace: ${selectedPace.label}")
            }

            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                paceOptions.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option.label) },
                        onClick = {
                            selectedPace = option
                            expanded = false
                        }
                    )
                }
            }
        }

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

        // Control buttons: Start / Reset / Back
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(onClick = { navController.popBackStack() }) {
                Text("Back")
            }

            Button(
                onClick = {
                    started = false
                    scope.launch { animatableProgress.snapTo(0f) }
                },
                enabled = started
            ) {
                Text("Reset")
            }

            Button(
                onClick = { started = true },
                enabled = !started
            ) {
                Text("Start")
            }
        }
        RetakeSurveyButton(navController = navController)
    }
}
