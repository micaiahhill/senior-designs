package com.example.firstapp

import android.media.MediaPlayer
import android.os.CountDownTimer
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.foundation.shape.RoundedCornerShape

@Composable
fun BeatPlayer(navController: NavController, beat: Beat) {
    val context = LocalContext.current
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
    var isPlaying by remember { mutableStateOf(false) }
    var remainingTime by remember { mutableStateOf<Int?>(null) }
    var timer by remember { mutableStateOf<CountDownTimer?>(null) }

    var isVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { isVisible = true }

    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer?.release()
            timer?.cancel()
        }
    }

    fun stopPlayback() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
        timer?.cancel()
        remainingTime = null
        isPlaying = false
    }

    fun playBeat(durationSeconds: Int) {
        stopPlayback()
        mediaPlayer = MediaPlayer.create(context, beat.rawId)?.apply {
            start()
            isPlaying = true
        }
        remainingTime = durationSeconds

        timer = object : CountDownTimer(durationSeconds * 1000L, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                remainingTime = (millisUntilFinished / 1000).toInt()
            }

            override fun onFinish() {
                stopPlayback()
            }
        }.start()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEFF6F9)) // light pastel blue
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (isVisible) {
            Text(
                text = "For the best experience, use headphones.",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color.DarkGray,
                    fontWeight = FontWeight.Medium
                ),
                modifier = Modifier.animateContentSize()
            )
        }

        Text(
            text = "${beat.name} Beat",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp
            )
        )

        BeatDuration.values().forEach { dur ->
            CalmButton(
                text = dur.label,
                onClick = { playBeat(dur.seconds) },
                enabled = !isPlaying
            )
        }

        if (isPlaying && remainingTime != null) {
            val pulse by rememberInfiniteTransition().animateFloat(
                initialValue = 1f,
                targetValue = 1.1f,
                animationSpec = infiniteRepeatable(tween(800), RepeatMode.Reverse)
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Playing: ${beat.name}",
                modifier = Modifier.scale(pulse),
                style = MaterialTheme.typography.titleMedium
            )

            AnimatedContent(targetState = remainingTime!!) { time ->
                val mins = time / 60
                val secs = time % 60
                Text("Time Left: ${String.format("%02d:%02d", mins, secs)}")
            }

            CalmButton(
                text = "Stop",
                onClick = { stopPlayback() }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        CalmButton(
            text = "Try Another Beat",
            onClick = {
                stopPlayback()
                navController.navigate("binaural_beats") {
                    launchSingleTop = true
                }
            }
        )
    }
}

@Composable
fun CalmButton(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFBFDDE8),
            contentColor = Color.Black,
            disabledContainerColor = Color(0xFFDEE6E9),
            disabledContentColor = Color.Gray
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(horizontal = 8.dp)
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

enum class BeatDuration(val label: String, val seconds: Int) {
    SHORT("5 Minutes", 5 * 60),
    MEDIUM("10 Minutes", 10 * 60),
    LONG("15 Minutes", 15 * 60)
}
