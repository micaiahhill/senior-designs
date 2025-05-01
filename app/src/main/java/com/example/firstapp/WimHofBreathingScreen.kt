package com.example.firstapp

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class WimHofRoundOption(val label: String, val rounds: Int)

@Composable
fun WimHofBreathingScreen(navController: NavController) {
    val roundOptions = listOf(
        WimHofRoundOption("1 Round", 1),
        WimHofRoundOption("2 Rounds", 2),
        WimHofRoundOption("3 Rounds", 3)
    )

    var selectedOption by remember { mutableStateOf(roundOptions[0]) }
    var dropdownExpanded by remember { mutableStateOf(false) }

    var currentRound by remember { mutableStateOf(1) }
    var phase by remember { mutableStateOf("Ready") } // Breathing, Hold, Recovery, or Completed
    var remainingTime by remember { mutableStateOf(0) }
    var animating by remember { mutableStateOf(false) }

    val scale = remember { Animatable(1f) }
    val scope = rememberCoroutineScope()

    val breathingDuration = 30  // seconds
    val holdDuration = 15
    val recoveryDuration = 15

    // Start animation sequence when rounds are chosen
    LaunchedEffect(animating) {
        if (!animating) return@LaunchedEffect

        currentRound = 1

        repeat(selectedOption.rounds) {
            // Breathing phase
            phase = "Breathing"
            remainingTime = breathingDuration
            repeat(breathingDuration) {
                scale.animateTo(1.5f, tween(1500, easing = LinearOutSlowInEasing))
                scale.animateTo(1f, tween(1500, easing = LinearOutSlowInEasing))
                delay(1000)
                remainingTime--
            }

            // Hold phase
            phase = "Hold"
            remainingTime = holdDuration
            scale.snapTo(1.5f)
            repeat(holdDuration) {
                delay(1000)
                remainingTime--
            }

            // Recovery phase
            phase = "Recovery"
            remainingTime = recoveryDuration
            scale.snapTo(1f)
            repeat(recoveryDuration) {
                delay(1000)
                remainingTime--
            }

            currentRound++
        }

        phase = "Completed"
        animating = false
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text("Wim Hof Breathing", style = MaterialTheme.typography.headlineMedium)

        // Dropdown (disabled during animation)
        Box {
            OutlinedButton(onClick = { dropdownExpanded = true }, enabled = !animating) {
                Text("Rounds: ${selectedOption.label}")
            }

            DropdownMenu(expanded = dropdownExpanded, onDismissRequest = { dropdownExpanded = false }) {
                roundOptions.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option.label) },
                        onClick = {
                            selectedOption = option
                            dropdownExpanded = false
                            phase = "Ready"
                            currentRound = 1
                            remainingTime = 0
                            animating = false
                            scope.launch {
                                scale.snapTo(1f)
                            }
                        }
                    )
                }
            }
        }

        // Breathing circle
        Box(
            modifier = Modifier
                .size(200.dp)
                .scale(scale.value)
                .background(Color.Blue.copy(alpha = 0.5f), shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            if (phase != "Completed") {
                Text(
                    text = "$remainingTime",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White
                )
            } else {
                Text(
                    text = "Done",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White
                )
            }
        }

        // Phase info
        Text(
            text = when (phase) {
                "Breathing" -> "Deep Breathing"
                "Hold" -> "Breath Hold"
                "Recovery" -> "Recovery Breath"
                "Completed" -> "Breathing Complete"
                else -> "Press Start"
            },
            style = MaterialTheme.typography.headlineSmall
        )

        Text("Round ${minOf(currentRound, selectedOption.rounds)} of ${selectedOption.rounds}")

        // Buttons
        Row {
            Button(onClick = { navController.popBackStack() }) {
                Text("Back")
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = {
                    animating = false
                    phase = "Ready"
                    currentRound = 1
                    remainingTime = 0
                    scope.launch {
                        scale.snapTo(1f)
                    }
                },
                enabled = animating || phase != "Ready"
            ) {
                Text("Reset")
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = { animating = true },
                enabled = !animating && phase != "Completed"
            ) {
                Text("Start")
            }
        }
        RetakeSurveyButton(navController = navController)
    }
}
