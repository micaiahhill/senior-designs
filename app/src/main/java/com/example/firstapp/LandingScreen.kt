package com.example.firstapp

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun LandingScreen(navController: NavController) {
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize()) {
        // Top Yellow Section
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f) // Take up 50% of the screen
                .background(Color(0xFFFFEB3B)), // Yellow Color (adjust as needed)
            contentAlignment = Alignment.Center
        ) {
            // Placeholder for the Circular Icon
            Box(
                modifier = Modifier
                    .size(100.dp) // Adjust size as needed
                    .clip(CircleShape)
                    .background(Color.White) // White circle for now
            )
            // Replace this with your actual icon
        }

        // Bottom Light Blue Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f) // Take up 50% of the screen
                .background(Color(0xFFE3F2FD)) // Light Blue Background
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "PreZen",
                fontSize = 36.sp, // Larger font size
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { navController.navigate("questionnaire") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
            ) {
                Text("Start Assessment", fontSize = 18.sp, color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp)) // Add some space

            Button(
                onClick = {
                    // Removed SessionManager logout
                    // Redirect user to LoginActivity and clear backstack
                    val intent = Intent(context, LoginActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }
                    context.startActivity(intent)
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
            ) {
                Text(
                    "Logout",
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }
    }
}