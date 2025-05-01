package com.example.firstapp

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@Composable
fun SoundscapesScreen(navController: NavController) {
    val context = LocalContext.current

    val backgrounds = listOf(
        "Galaxy" to R.drawable.galaxy,
        "Galaxy 2" to R.drawable.galaxy_2,
        "Mountain Top" to R.drawable.mountain_top,
        "Zen" to R.drawable.zen,
        "Nightlights" to R.drawable.nightlights
    )

    val soundOptions = listOf("Auto Playlist", "Space", "Wind", "Meditate")

    val soundResMap = mapOf(
        "Space" to R.raw.space,
        "Wind" to R.raw.wind,
        "Meditate" to R.raw.meditate
    )

    var selectedBackgroundIndex by remember { mutableStateOf(0) }
    var autoMode by remember { mutableStateOf(false) }
    var animateZoom by remember { mutableStateOf(false) }
    var isMuted by remember { mutableStateOf(true) }
    var selectedSound by remember { mutableStateOf("Auto Playlist") }
    var menuExpanded by remember { mutableStateOf(false) }
    var fullscreen by remember { mutableStateOf(false) }
    var showExitFullscreenButton by remember { mutableStateOf(false) }

    val mediaPlayer = remember { mutableStateOf<MediaPlayer?>(null) }

    fun playSound(context: Context, resId: Int) {
        mediaPlayer.value?.release()
        mediaPlayer.value = MediaPlayer.create(context, resId).apply {
            setOnCompletionListener {
                if (selectedSound == "Auto Playlist") {
                    val next = (soundOptions.indexOfLast { it != "Auto Playlist" && soundResMap.containsKey(it) && soundResMap[it] == resId } + 1)
                    val nextSound = soundOptions.filter { it != "Auto Playlist" }[next % 3]
                    soundResMap[nextSound]?.let { playSound(context, it) }
                }
            }
            isLooping = selectedSound != "Auto Playlist"
            if (!isMuted) start()
        }
    }

    LaunchedEffect(selectedSound, isMuted) {
        mediaPlayer.value?.stop()
        mediaPlayer.value?.release()
        mediaPlayer.value = null

        if (!isMuted && selectedSound != "Auto Playlist") {
            soundResMap[selectedSound]?.let { playSound(context, it) }
        } else if (!isMuted && selectedSound == "Auto Playlist") {
            playSound(context, soundResMap["Space"]!!) // Start with Space
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer.value?.stop()
            mediaPlayer.value?.release()
        }
    }

    LaunchedEffect(autoMode) {
        if (autoMode) {
            while (true) {
                delay(30000)
                selectedBackgroundIndex = (selectedBackgroundIndex + 1) % backgrounds.size
            }
        }
    }

    val infiniteTransition = rememberInfiniteTransition()
    val zoomScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(12000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    val scale = if (animateZoom) zoomScale else 1f

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable { showExitFullscreenButton = fullscreen },
        contentAlignment = Alignment.TopEnd
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            if (!fullscreen) {
                // Menu
                Box(modifier = Modifier.fillMaxWidth().padding(16.dp), contentAlignment = Alignment.TopEnd) {
                    IconButton(onClick = { menuExpanded = true }) {
                        Icon(Icons.Default.Menu, contentDescription = "Options")
                    }

                    DropdownMenu(expanded = menuExpanded, onDismissRequest = { menuExpanded = false }) {
                        DropdownMenuItem(
                            text = {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text("Auto Mode")
                                    Spacer(modifier = Modifier.weight(1f))
                                    Switch(checked = autoMode, onCheckedChange = { autoMode = it })
                                }
                            },
                            onClick = { }
                        )
                        DropdownMenuItem(
                            text = {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text("Animate")
                                    Spacer(modifier = Modifier.weight(1f))
                                    Switch(checked = animateZoom, onCheckedChange = { animateZoom = it })
                                }
                            },
                            onClick = { }
                        )
                        DropdownMenuItem(
                            text = {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text("Mute")
                                    Spacer(modifier = Modifier.weight(1f))
                                    Switch(checked = isMuted, onCheckedChange = { isMuted = it })
                                }
                            },
                            onClick = { }
                        )
                        DropdownMenuItem(
                            text = {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text("Fullscreen")
                                    Spacer(modifier = Modifier.weight(1f))
                                    Switch(checked = fullscreen, onCheckedChange = { fullscreen = it })
                                }
                            },
                            onClick = { }
                        )
                        DropdownMenuItem(
                            text = {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text("Sound")
                                    Spacer(modifier = Modifier.width(8.dp))
                                    var expanded by remember { mutableStateOf(false) }
                                    OutlinedButton(onClick = { expanded = true }) {
                                        Text(selectedSound)
                                    }
                                    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                                        soundOptions.forEach { option ->
                                            DropdownMenuItem(
                                                text = { Text(option) },
                                                onClick = {
                                                    selectedSound = option
                                                    expanded = false
                                                }
                                            )
                                        }
                                    }
                                }
                            },
                            onClick = { }
                        )
                    }
                }
            }

            if (!autoMode && !fullscreen) {
                var dropdownExpanded by remember { mutableStateOf(false) }
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    OutlinedButton(onClick = { dropdownExpanded = true }) {
                        Text("Scene: ${backgrounds[selectedBackgroundIndex].first}")
                    }

                    DropdownMenu(
                        expanded = dropdownExpanded,
                        onDismissRequest = { dropdownExpanded = false }
                    ) {
                        backgrounds.forEachIndexed { index, (label, _) ->
                            DropdownMenuItem(
                                text = { Text(label) },
                                onClick = {
                                    selectedBackgroundIndex = index
                                    dropdownExpanded = false
                                }
                            )
                        }
                    }
                }
            }

            Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
                Image(
                    painter = painterResource(id = backgrounds[selectedBackgroundIndex].second),
                    contentDescription = backgrounds[selectedBackgroundIndex].first,
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer {
                            scaleX = scale
                            scaleY = scale
                        },
                    contentScale = ContentScale.Crop
                )
            }

            if (!fullscreen) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Button(onClick = { navController.popBackStack() }) {
                        Text("Back")
                    }
                }
            }
        }

        if (fullscreen && showExitFullscreenButton) {
            IconButton(
                onClick = { fullscreen = false; showExitFullscreenButton = false },
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Exit Fullscreen",
                    tint = Color.White
                )
            }
        }
    }
}
