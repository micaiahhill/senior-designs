package com.example.firstapp

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.firstapp.SunriseBackground
import kotlinx.coroutines.launch

@Composable
fun RestorePromptScreen(navController: NavController, context: Context) {
    SunriseBackground {
        val scope = rememberCoroutineScope()
        var lastScreen by remember { mutableStateOf<String?>(null) }

        LaunchedEffect(Unit) {
            lastScreen = getLastScreen(context)
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center) // Center vertically...
                    .offset(y = 120.dp),      // ...then push it downward

                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Welcome back! Resume where you left off?")

                Spacer(modifier = Modifier.height(24.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Button(onClick = {
                        navController.navigate("activitySelector" ?: "landing") {
                            popUpTo("restore_prompt") { inclusive = true }
                        }
                    }) {
                        Text("Resume")
                    }

                    Button(onClick = {
                        navController.navigate("landing") {
                            popUpTo("restore_prompt") { inclusive = true }
                        }
                    }) {
                        Text("Start Fresh")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                LogoutButton(navController, LocalContext.current)
            }
        }
    }
}
