package com.example.firstapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun BetaBeatScreen(navController: NavController) {
    BeatPlayer(navController, Beat("Beta", R.raw.beta))
}
