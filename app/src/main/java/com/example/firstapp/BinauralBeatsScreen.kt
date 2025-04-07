package com.example.firstapp

import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


enum class BeatDuration(val label: String, val seconds: Int) {
    SHORT("45 sec", 45),
    MEDIUM("5 min", 5 * 60),
    LONG("20 min", 20 * 60)
}

data class Beat(val name: String, val rawId: Int)

@Composable
fun BinauralBeatsScreen(navController: NavController) {
    val context = LocalContext.current

    val beats = listOf(
        Beat("Alpha", R.raw.alpha),
        Beat("Beta", R.raw.beta),
        Beat("Delta", R.raw.delta),
        Beat("Theta", R.raw.theta)
    )

    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
    var isPlaying by remember { mutableStateOf(false) }
    var remainingTime by remember { mutableStateOf<Int?>(null) }
    var timer by remember { mutableStateOf<CountDownTimer?>(null) }
    var currentPlayingBeat by remember { mutableStateOf<Beat?>(null) }

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
        currentPlayingBeat = null
    }

    fun playBeat(beat: Beat, durationSeconds: Int) {
        stopPlayback()
        mediaPlayer = MediaPlayer.create(context, beat.rawId)?.apply {
            start()
            isPlaying = true
        }
        currentPlayingBeat = beat
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
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = "Binaural Beats", style = MaterialTheme.typography.headlineMedium)

        beats.forEach { beat ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(beat.name, style = MaterialTheme.typography.titleMedium)

                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn(animationSpec = tween(600)) + slideInHorizontally(initialOffsetX = { -100 }),
                    exit = fadeOut()
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        BeatDuration.values().forEach { dur ->
                            Button(
                                onClick = { playBeat(beat, dur.seconds) },
                                enabled = !isPlaying
                            ) {
                                Text(dur.label)
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { stopPlayback() },
            enabled = isPlaying
        ) {
            Text("Stop")
        }

        if (currentPlayingBeat != null && remainingTime != null) {
            val infiniteTransition = rememberInfiniteTransition()
            val pulse by infiniteTransition.animateFloat(
                initialValue = 1f,
                targetValue = 1.1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(800, easing = LinearEasing),
                    repeatMode = RepeatMode.Reverse
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Playing: ${currentPlayingBeat!!.name}",
                modifier = Modifier.scale(pulse),
                style = MaterialTheme.typography.titleMedium
            )

            AnimatedContent(targetState = remainingTime!!) { time ->
                val mins = time / 60
                val secs = time % 60
                Text("Time Left: ${String.format("%02d:%02d", mins, secs)}")
            }
        } else {
            Spacer(modifier = Modifier.height(8.dp))
            Text("Select a beat and duration")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBinauralBeatsScreen() {

}
