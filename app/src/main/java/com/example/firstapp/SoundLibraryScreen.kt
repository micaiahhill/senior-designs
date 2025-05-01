package com.example.firstapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

@Composable
fun SoundLibraryScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE9F5F2)) // Soft minty background
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = "Sound Library",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                color = Color(0xFF334E4A)
            )
        )

        // Preloaded Sound Library Section
        SoundLibrarySection(
            title = "Preloaded Sound Library",
            onClick = { navController.navigate("preloaded_sound_library") },
            iconResId = R.drawable.musicnote
        )

        // Binaural Beats Section
        SoundLibrarySection(
            title = "Binaural Beats Integration",
            onClick = { navController.navigate("binaural_beats") },
            iconResId = R.drawable.bb
        )

        // Soft Button
        Button(
            onClick = { navController.popBackStack() },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFABCFC6)),
            modifier = Modifier
                .padding(top = 32.dp)
                .height(50.dp)
                .fillMaxWidth(0.8f)
        ) {
            Text(
                text = "Back to Home",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }
    }
}

@Composable
fun SoundLibrarySection(title: String, onClick: () -> Unit, iconResId: Int) {
    Column(
        modifier = Modifier
            .clickable(onClick = onClick)
            .background(Color.White, shape = MaterialTheme.shapes.medium)
            .padding(16.dp)
            .fillMaxWidth(0.9f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Image(
            painter = painterResource(id = iconResId),
            contentDescription = title,
            modifier = Modifier.size(100.dp)
        )
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Medium,
                color = Color(0xFF334E4A)
            )
        )
    }
}
