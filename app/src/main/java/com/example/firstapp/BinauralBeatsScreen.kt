package com.example.firstapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController

// Remove ComponentActivity since you're using Composables and Navigation.
@Composable
fun BinauralBeatsScreen(navController: NavController) {
    Text(text = "Binaural Beats Screen")
}

@Preview(showBackground = true)
@Composable
fun PreviewBinauralBeatsScreen() {
    BinauralBeatsScreen(navController = NavController(context = LocalContext.current))
}
