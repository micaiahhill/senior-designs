package com.example.firstapp

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecommendationScreen(tech: String, navController: NavController) {
    Scaffold(
        containerColor = Color(0xFFFDF6EC), // soft cream background
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "A Moment for You",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color(0xFFFDF6EC))
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Take a breath.\nHere's a gentle practice that may help bring you peace and clarity today:",
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = tech.replace('_', ' ').replaceFirstChar { it.uppercase() },
                fontSize = 30.sp,
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = getRecommendationExplanation(tech),
                fontSize = 18.sp,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 8.dp)
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
                    Text("Start This Practice")
                }

                OutlinedButton(
                    onClick = { navController.navigate("activitySelector") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    Text("Browse More Paths")
                }
            }
        }
    }
}

fun getRecommendationExplanation(tech: String): String {
    return when (tech) {
        "wimHof_breathing" -> "You’re feeling strong and focused. This practice can help you channel that energy with intention."
        "box_breathing" -> "Your spirit feels balanced. Box breathing supports clarity, calm, and grounded presence."
        "altNostril_breathing" -> "When you need to center yourself, alternate nostril breathing gently brings harmony to body and mind."
        "478_breathing" -> "If things feel overwhelming, this calming rhythm invites peace and deep rest to your nervous system."
        "preloaded_sound_library" -> "Let your mind unwind. These peaceful soundscapes can guide you into stillness or gentle focus."
        "diaphragmatic_breathing" -> "Your body may be asking for deep rest. This breathing helps release tension from within."
        "binaural_alpha" -> "You’re seeking calm alertness. Alpha waves can help you feel steady, yet focused — like a quiet sunrise."
        "binaural_beta" -> "Your thoughts are active. Beta waves offer clarity and grounding to keep you steady amidst the rush."
        "binaural_theta" -> "For deeper reflection or creativity, theta waves open space for imagination and renewal."
        "binaural_delta" -> "If rest is what your soul seeks, delta waves may guide you into deep relaxation or restorative sleep."
        "binaural_beats" -> "Explore these gentle frequencies and choose what resonates most with where you are right now."
        else -> "This practice is offered as a moment to reset, breathe, and reconnect with yourself."
    }
}
