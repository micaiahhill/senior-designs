package com.example.firstapp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun BoxBreathingTutorialScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Box Breathing",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = """
                Box Breathing is a simple and powerful technique used to reduce stress and improve focus. 
                
                The process involves:
                • Inhaling for 4 seconds  
                • Holding your breath for 4 seconds  
                • Exhaling for 4 seconds  
                • Holding again for 4 seconds  
                
                Each side of the “box” represents one of these steps.
                
                This rhythm calms the nervous system and helps bring your mind to a state of focus and clarity.
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

            Button(onClick = { navController.navigate("box_breathing") }) {
                Text("Continue")
            }
        }
    }
}
