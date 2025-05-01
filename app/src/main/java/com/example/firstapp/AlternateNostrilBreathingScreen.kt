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
import kotlinx.coroutines.launch

// New pace data class
//data class BreathingPace(val label: String, val inhale: Int, val hold: Int, val exhale: Int)

data class Quadruple<A, B, C, D, E>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
    val fifth: E
)

@Composable
fun AlternateNostrilBreathingScreen(navController: NavController) {
    val paceOptions = listOf(
        BreathingPace("Gentle (4-2-4)", 4000, 2000, 4000),
        BreathingPace("Balanced (5-2-5)", 5000, 2000, 5000),
        BreathingPace("Deep (6-3-6)", 6000, 3000, 6000)
    )

    var selectedPace by remember { mutableStateOf(paceOptions[1]) }
    var expanded by remember { mutableStateOf(false) }
    var started by remember { mutableStateOf(false) }

    val inhaleDuration = selectedPace.inhale
    val holdDuration = selectedPace.hold
    val exhaleDuration = selectedPace.exhale

    val totalCycleDuration = (inhaleDuration + holdDuration + exhaleDuration) * 2

    val animatableProgress = remember { Animatable(0f) }
    val progress = animatableProgress.value
    val scope = rememberCoroutineScope()

    LaunchedEffect(started, selectedPace) {
        if (started) {
            animatableProgress.stop()
            animatableProgress.snapTo(0f)
            animatableProgress.animateTo(
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = totalCycleDuration, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                )
            )
        } else {
            animatableProgress.stop()
            animatableProgress.snapTo(0f)
        }
    }

    val elapsedMs = progress * totalCycleDuration

    val (phase, leftBarFill, rightBarFill, leftSeconds, rightSeconds) = when {
        elapsedMs < inhaleDuration -> {
            val fill = (elapsedMs / inhaleDuration).coerceIn(0f, 1f)
            val sec = inhaleDuration / 1000 - (elapsedMs / 1000).toInt()
            Quadruple("Inhale Left", fill, 0f, sec, 0)
        }
        elapsedMs < inhaleDuration + holdDuration -> {
            Quadruple("Hold", 1f, 0f, 0, 0)
        }
        elapsedMs < inhaleDuration + holdDuration + exhaleDuration -> {
            val ex = elapsedMs - inhaleDuration - holdDuration
            val fill = 1f - (ex / exhaleDuration).coerceIn(0f, 1f)
            val sec = exhaleDuration / 1000 - (ex / 1000).toInt()
            Quadruple("Exhale Right", 0f, fill, 0, sec)
        }
        elapsedMs < inhaleDuration * 2 + holdDuration + exhaleDuration -> {
            Quadruple("Hold", 0f, 0f, 0, 0)
        }
        elapsedMs < inhaleDuration * 2 + holdDuration * 2 + exhaleDuration -> {
            val inh = elapsedMs - (inhaleDuration + holdDuration + exhaleDuration + holdDuration)
            val fill = (inh / inhaleDuration).coerceIn(0f, 1f)
            val sec = inhaleDuration / 1000 - (inh / 1000).toInt()
            Quadruple("Inhale Right", 0f, fill, 0, sec)
        }
        elapsedMs < inhaleDuration * 2 + holdDuration * 2 + exhaleDuration + holdDuration -> {
            Quadruple("Hold", 0f, 1f, 0, 0)
        }
        else -> {
            val ex = elapsedMs - (inhaleDuration * 2 + holdDuration * 2 + exhaleDuration + holdDuration)
            val fill = 1f - (ex / exhaleDuration).coerceIn(0f, 1f)
            val sec = exhaleDuration / 1000 - (ex / 1000).toInt()
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

        // Dropdown
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

        // Bar animation
        Row(
            horizontalArrangement = Arrangement.spacedBy(32.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
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
                Text(if (leftSeconds > 0) "$leftSeconds s" else "", fontSize = 18.sp)
            }

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
                Text(if (rightSeconds > 0) "$rightSeconds s" else "", fontSize = 18.sp)
            }
        }

        Text(text = phase, fontSize = 20.sp)

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
