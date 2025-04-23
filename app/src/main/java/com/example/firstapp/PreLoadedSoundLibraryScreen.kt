package com.example.firstapp
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

import android.media.MediaPlayer
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.firstapp.R

@Composable
fun PreLoadedSoundLibraryScreen(navController: NavController? = null) {
    val context = LocalContext.current

    val sounds = listOf(
        SoundItem("Waves", R.drawable.waves, R.raw.waves),
        SoundItem("Lofi", R.drawable.lofi, R.raw.lofi),
        SoundItem("Airplane", R.drawable.airplane, R.raw.airplane),
        SoundItem("Rainforest", R.drawable.rainforest, R.raw.rainforest),
        SoundItem("Fire", R.drawable.fire, R.raw.fireplace),
        SoundItem("Jazz", R.drawable.jazz, R.raw.jazz)
    )

    var mediaPlayer: MediaPlayer? by remember { mutableStateOf(null) }
    var currentlyPlaying by remember { mutableStateOf<String?>(null) }

    val infiniteTransition = rememberInfiniteTransition()
    val offset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 10000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val animatedGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFB3D6FC),
            Color(0xFFB8D5EA),
            Color(0xFFE4E4E7)
        ),
        startY = offset,
        endY = offset + 1000f
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(animatedGradient)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "🎶 Preloaded Sound Library",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Text(
                text = "Tap a sound to start listening with ease.",
                fontSize = 16.sp,
                color = Color.DarkGray
            )

            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 160.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(sounds) { sound ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(8.dp)
                    ) {
                        Image(
                            painter = painterResource(id = sound.iconRes),
                            contentDescription = sound.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(120.dp)
                                .clickable {
                                    mediaPlayer?.stop()
                                    mediaPlayer?.release()
                                    mediaPlayer = MediaPlayer.create(context, sound.soundRes)
                                    mediaPlayer?.start()
                                    currentlyPlaying = sound.name
                                }
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = sound.name,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                            color = Color.Black
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Button(
                                onClick = {
                                    mediaPlayer?.start()
                                    currentlyPlaying = sound.name
                                },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Play", fontSize = 12.sp)
                            }

                            Button(
                                onClick = {
                                    mediaPlayer?.pause()
                                },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Pause", fontSize = 12.sp)
                            }

                            Button(
                                onClick = {
                                    mediaPlayer?.stop()
                                    mediaPlayer?.release()
                                    mediaPlayer = null
                                    currentlyPlaying = null
                                },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Stop", fontSize = 12.sp)
                            }
                        }
                    }
                }
            }

            navController?.let {
                Button(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text("⬅ Back to Library")
                }
            }
        }
    }
}
data class SoundItem(val name: String, val iconRes: Int, val soundRes: Int)
