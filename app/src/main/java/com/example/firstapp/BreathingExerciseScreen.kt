package com.example.firstapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.firstapp.R

@Composable
fun BreathingExerciseScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text("Guided Breathing Exercises", fontSize = 24.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(5.dp))

            // Icons Row
            Row(
                horizontalArrangement = Arrangement.spacedBy(40.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Icon 1: Box Breathing
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_box_breathing),
                        contentDescription = "Box Breathing",
                        modifier = Modifier
                            .size(150.dp)
                            .clickable {
                                navController.navigate("box_breathing_tutorial")
                            }
                    )
                    Text("Box")
                }

                // Icon 2: 4-7-8 Breathing
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_478_breathing),
                        contentDescription = "4-7-8 Breathing",
                        modifier = Modifier
                            .size(150.dp)
                            .clickable {
                                navController.navigate("478_breathing_tutorial")
                            }
                    )
                    Text("4-7-8")
                }
            }

            Spacer(modifier = Modifier.height(40.dp)) // Space between rows

            // Row 2: Diaphragmatic & Alternate Nostril Breathing
            Row(
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Diaphragmatic Breathing Icon
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_diaphragmatic_breathing),
                        contentDescription = "Diaphragmatic Breathing",
                        modifier = Modifier
                            .size(150.dp)
                            .clickable {
                                navController.navigate("diaphragmatic_breathing_tutorial")
                            }
                    )
                    Text("Diaphragmatic")
                }

                // Alternate Nostril Breathing Icon
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_alternate_nostril_breathing),
                        contentDescription = "Alternate Nostril Breathing",
                        modifier = Modifier
                            .size(150.dp)
                            .clickable {
                                navController.navigate("altNostril_breathing_tutorial")
                            }
                    )
                    Text("Alternate Nostril")
                }
            }

            Spacer(modifier = Modifier.height(40.dp)) // Space between rows

            // Row 3: Wim Hof Breathing (single icon centered)
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_wim_hof_breathing),
                        contentDescription = "Wim Hof Breathing",
                        modifier = Modifier
                            .size(150.dp)
                            .clickable {
                                navController.navigate("wimHof_breathing_tutorial")
                            }
                    )
                    Text("Wim Hof")
                }
            }
        }
        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
        ) {
            Text("Back to Home")
        }
    }
}
