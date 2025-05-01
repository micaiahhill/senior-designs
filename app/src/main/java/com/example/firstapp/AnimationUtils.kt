// AnimationUtils.kt
package com.example.firstapp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
//import androidx.compose.material.icons.filled.Replay
//import androidx.compose.material.icons.filled.Assignment
//import androidx.compose.material.icons.filled.Restore
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun RetakeSurveyButton(
    navController: NavController,
    position: Alignment = Alignment.TopEnd,
    icon: ImageVector = Icons.Default.Refresh
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = position
    ) {
        IconButton(
            onClick = {
                navController.navigate("questionnaire") {
                    popUpTo(navController.graph.startDestinationId) {
                        inclusive = false
                    }
                    launchSingleTop = true
                }
            }
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "Retake Survey"
            )
        }
    }
}


fun lerp(start: Float, end: Float, fraction: Float): Float {
    return start + (end - start) * fraction.coerceIn(0f, 1f)
}
