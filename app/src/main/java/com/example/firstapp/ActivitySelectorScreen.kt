package com.example.firstapp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivitySelectorScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Explore Activities") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Choose what you'd like to explore:",
                fontSize = 22.sp,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { navController.navigate("breathing") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text("Breathing Techniques")
            }

            Button(
                onClick = { navController.navigate("soundLibrary") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text("Sound Library")
            }

            Button(onClick = { navController.navigate("soundscapes") },
                   modifier = Modifier
                       .fillMaxWidth()
                       .padding(vertical = 8.dp)
                ) {
                Text("Soundscapes")
            }
        }
    }
}
