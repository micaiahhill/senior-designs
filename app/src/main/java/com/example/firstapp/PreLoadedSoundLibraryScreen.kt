package com.example.firstapp

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.example.firstapp.R
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll


@Composable
fun PreLoadedSoundLibraryScreen(navController: NavController? = null) {
    val context = LocalContext.current

    val sounds = listOf(
        SoundItem("Waves", R.drawable.baseline_add_box_24, R.raw.waves),
        SoundItem("Lofi", R.drawable.baseline_add_box_24, R.raw.lofi),
        SoundItem("Airplane", R.drawable.baseline_add_box_24, R.raw.airplane),
        SoundItem("Rainforest", R.drawable.baseline_add_box_24, R.raw.rainforest),
        SoundItem("Fire", R.drawable.baseline_add_box_24, R.raw.fireplace),
        SoundItem("Jazz", R.drawable.baseline_add_box_24, R.raw.jazz)
    )

    var mediaPlayer: MediaPlayer? by remember { mutableStateOf(null) }
    var currentlyPlaying by remember { mutableStateOf<String?>(null) }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Preloaded Sound Library",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = "Explore a collection of pre-recorded sounds.",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 150.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(sounds) { sound ->
                Column(
                    horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(8.dp)
                ) {
                    Image(
                        painter = painterResource(id = sound.iconRes),
                        contentDescription = sound.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .clickable {
                                // Stop current and play new
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
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(onClick = {
                            mediaPlayer?.start()
                            currentlyPlaying = sound.name
                        }) {
                            Text("Play", fontSize = 12.sp)
                        }

                        Button(onClick = {
                            mediaPlayer?.pause()
                        }) {
                            Text("Pause", fontSize = 12.sp)
                        }

                        Button(onClick = {
                            mediaPlayer?.stop()
                            mediaPlayer?.release()
                            mediaPlayer = null
                            currentlyPlaying = null
                        }) {
                            Text("Stop", fontSize = 12.sp)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (navController != null) {
            Text(
                text = "See More",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate("preloaded_sound_library_details")
                    }
            )
        }
    }
}

data class SoundItem(val name: String, val iconRes: Int, val soundRes: Int)
