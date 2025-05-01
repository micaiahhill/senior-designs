package com.example.firstapp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun AlternateNostrilBreathingTutorialScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Alternate Nostril Breathing",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = """
                Alternate Nostril Breathing (Nadi Shodhana) is a calming technique that balances the body and mind by regulating airflow between both nostrils.

                How it works:
                • Use your thumb to close your right nostril and inhale through the left  
                • Close the left nostril with your ring finger, open the right, and exhale  
                • Inhale through the right, then switch again and exhale through the left  
                
                This controlled rhythm helps reduce stress, increase focus, and balance energy.
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

            Button(onClick = { navController.navigate("altNostril_breathing") }) {
                Text("Continue")
            }
        }
    }
}
