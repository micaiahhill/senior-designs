package com.example.firstapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun SoundLibraryScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp) // Add space between sections
    ) {
        Text("Sound Library", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        SoundLibrarySection(
            title = "Preloaded Sound Library",
            onClick = { /* Navigate to Preloaded Sound Library */ }
        )

        SoundLibrarySection(
            title = "Custom Sound Mixing",
            onClick = { /* Navigate to Custom Sound Mixing */ }
        )

        SoundLibrarySection(
            title = "Binaural Beats Integration",
            onClick = { /* Navigate to Binaural Beats Integration */ }
        )

        Button(onClick = { navController.popBackStack() }) {
            Text("Back to Home")
        }
    }
}

@Composable
fun SoundLibrarySection(title: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick) // Make the card clickable
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(title, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
            // Add any additional content or icons here
        }
    }
}