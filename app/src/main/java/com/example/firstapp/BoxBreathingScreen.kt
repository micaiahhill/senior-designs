package com.example.firstapp

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.math.min

@Composable
fun BoxBreathingScreen(navController: NavController) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        saveLastScreen(context, "box_breathing")
    }

    val scope = rememberCoroutineScope()
    val squareSize = 200.dp

    var started by remember { mutableStateOf(false) }
    var durationPerPhase by remember { mutableStateOf(4f) }
    val totalDurationMs = (durationPerPhase * 4 * 1000).toLong()
    val elapsedMillis = remember { mutableStateOf(0L) }

    LaunchedEffect(started, durationPerPhase) {
        if (started) {
            val startTime = System.currentTimeMillis()
            while (isActive) {
                val now = System.currentTimeMillis()
                val rawElapsed = now - startTime
                elapsedMillis.value = (rawElapsed % totalDurationMs).coerceAtMost(totalDurationMs - 1)
                delay(16L)
            }
        }
    }

    val elapsedTime = (elapsedMillis.value / 1000).toInt()
    val progress = elapsedMillis.value.toFloat() / totalDurationMs.toFloat()

    val phaseDurationSec = durationPerPhase.toInt()
    val (phase, secondsElapsed) = when {
        elapsedTime < phaseDurationSec -> "Inhale" to elapsedTime
        elapsedTime < 2 * phaseDurationSec -> "Hold" to elapsedTime - phaseDurationSec
        elapsedTime < 3 * phaseDurationSec -> "Exhale" to elapsedTime - 2 * phaseDurationSec
        else -> "Hold" to elapsedTime - 3 * phaseDurationSec
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text("Box Breathing", style = MaterialTheme.typography.headlineMedium)

            var sliderDropdownExpanded by remember { mutableStateOf(false) }

            Box {
                OutlinedButton(onClick = { sliderDropdownExpanded = true }) {
                    Text("Pace: ${durationPerPhase.toInt()}s per side")
                }

                DropdownMenu(
                    expanded = sliderDropdownExpanded,
                    onDismissRequest = { sliderDropdownExpanded = false }
                ) {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .width(250.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Adjust Pace", style = MaterialTheme.typography.bodyMedium)
                        Spacer(modifier = Modifier.height(8.dp))

                        Slider(
                            value = durationPerPhase,
                            onValueChange = { durationPerPhase = it },
                            valueRange = 2f..10f,
                            steps = 8,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )

                        Text("${durationPerPhase.toInt()} seconds per side")
                        Spacer(modifier = Modifier.height(8.dp))

                        Button(onClick = { sliderDropdownExpanded = false }) {
                            Text("Done")
                        }
                    }
                }
            }

            Box(modifier = Modifier.size(squareSize), contentAlignment = Alignment.Center) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val canvasWidth = size.width
                    val canvasHeight = size.height

                    drawRect(
                        color = Color.Gray,
                        size = size,
                        style = androidx.compose.ui.graphics.drawscope.Stroke(
                            width = 4.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    )

                    val perimeter = 4 * canvasWidth
                    val position = progress * perimeter
                    val (x, y) = when {
                        position <= canvasWidth -> Offset(position, 0f)
                        position <= canvasWidth + canvasHeight -> Offset(canvasWidth, position - canvasWidth)
                        position <= 2 * canvasWidth + canvasHeight -> Offset(2 * canvasWidth + canvasHeight - position, canvasHeight)
                        else -> Offset(0f, perimeter - position)
                    }

                    drawCircle(
                        color = Color.Blue,
                        radius = 12.dp.toPx(),
                        center = Offset(x, y)
                    )
                }

                Text(
                    text = "${secondsElapsed + 1}",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.Black
                )
            }

            Text(text = phase, style = MaterialTheme.typography.headlineSmall)

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Button(onClick = { navController.popBackStack() }) {
                    Text("Back")
                }
                Button(onClick = {
                    started = false
                    elapsedMillis.value = 0L
                }) {
                    Text("Reset")
                }
                Button(
                    onClick = { started = true },
                    enabled = !started
                ) {
                    Text("Start")
                }
            }
        }

        RetakeSurveyButton(navController = navController)
    }
}
