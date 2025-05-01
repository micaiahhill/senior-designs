package com.example.firstapp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun DiaphragmaticBreathingTutorialScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Diaphragmatic Breathing",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = """
                Diaphragmatic Breathing, also known as belly breathing, encourages deeper, slower breathing that engages the diaphragm.

                Here's how to do it:
                • Inhale deeply through your nose, letting your belly expand  
                • Exhale slowly through your mouth, letting your belly fall  
                • Keep your chest as still as possible and focus on using your diaphragm  

                This technique is great for reducing tension, lowering heart rate, and improving oxygen flow.
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

            Button(onClick = { navController.navigate("diaphragmatic_breathing") }) {
                Text("Continue")
            }
        }
    }
}
