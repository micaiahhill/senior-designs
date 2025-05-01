package com.example.firstapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun DeltaBeatScreen(navController: NavController) {
    BeatPlayer(navController, Beat("Delta", R.raw.delta))
}