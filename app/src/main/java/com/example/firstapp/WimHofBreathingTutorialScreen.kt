package com.example.firstapp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun WimHofBreathingTutorialScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Wim Hof Breathing",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = """
                The Wim Hof Breathing Method combines deep rhythmic inhalations and controlled breath retention to energize and strengthen the body.

                One round typically includes:
                • 30–40 deep breaths (inhale fully, exhale naturally)
                • After the last exhale, hold your breath as long as you can
                • Take a deep recovery breath and hold it for 15 seconds

                This practice boosts oxygen levels, increases endurance, and can lead to a heightened state of mental clarity and well-being.
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

            Button(onClick = { navController.navigate("wimHof_breathing") }) {
                Text("Continue")
            }
        }
    }
}
