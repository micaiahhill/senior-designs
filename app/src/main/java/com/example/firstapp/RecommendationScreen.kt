package com.example.firstapp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecommendationScreen(tech: String, navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Your Recommendation") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Based on your responses, we recommend:",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = tech.replace('_', ' ').replaceFirstChar { it.uppercase() },
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = getRecommendationExplanation(tech),
                fontSize = 18.sp,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = { navController.navigate(tech) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Text("Start This Activity")
                }

                OutlinedButton(
                    onClick = { navController.navigate("activitySelector") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    Text("Explore")
                }
            }
        }
    }
}

fun getRecommendationExplanation(tech: String): String {
    return when (tech) {
        "wimHof_breathing" -> "You seem energized and mentally sharp, so we suggest a strong, invigorating technique to match your state."
        "box_breathing" -> "Your responses show a good balance — box breathing can help maintain focus and calm."
        "altNostril_breathing" -> "You might benefit from grounding energy — alternate nostril breathing balances the mind and body."
        "478_breathing" -> "You may be feeling overwhelmed or anxious. 4-7-8 breathing is great for calming the nervous system."
        "soundLibrary" -> "You appear relatively calm. Soundscapes can help you relax or focus even further."
        "diaphragmatic_breathing" -> "Tension in the body suggests a need for deep physical relaxation — try diaphragmatic breathing."
        "ambient_focus_sound" -> "You might be low on energy or focus. Ambient sounds can help re-engage your attention gently."
        "gentle_focus_breathing" -> "You may be feeling scattered. Gentle guided breathing can help you center your thoughts."
        "deep_body_relaxation" -> "High body tension calls for deep relaxation. This method targets full-body release."
        "sleep_guided" -> "Your responses show low energy and stress — guided breathing is ideal to help you wind down."
        "gentle_breathing" -> "This gentle technique supports balanced or mixed emotional states."
        else -> "This activity is chosen to help you reconnect and feel grounded based on your state."
    }
}
