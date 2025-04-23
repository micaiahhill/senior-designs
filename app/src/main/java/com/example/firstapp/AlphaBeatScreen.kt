package com.example.firstapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun AlphaBeatScreen(navController: NavController) {
    val alphaBeat = Beat("Alpha", R.raw.alpha) // make sure alpha.mp3 is in res/raw
    BeatPlayer(navController = navController, beat = alphaBeat)
}

