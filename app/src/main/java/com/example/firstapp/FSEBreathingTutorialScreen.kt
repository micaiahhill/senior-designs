package com.example.firstapp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun FSEBreathingTutorialScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "4-7-8 Breathing",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = """
                The 4-7-8 Breathing technique is designed to calm your body and mind by controlling your breath.

                Here’s how it works:
                • Inhale through your nose for 4 seconds  
                • Hold your breath for 7 seconds  
                • Exhale slowly through your mouth for 8 seconds  

                This technique helps reduce anxiety, promote relaxation, and prepare your body for sleep or focus.
            """.trimIndent(),
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { navController.popBackStack() }) {
                Text("Back")
            }

            Button(onClick = { navController.navigate("478_breathing") }) {
                Text("Continue")
            }
        }
    }
}
