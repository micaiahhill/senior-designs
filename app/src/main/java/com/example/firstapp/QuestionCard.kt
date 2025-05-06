package com.example.firstapp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun QuestionCard(
    question: String,
    value: Int,
    labels: List<String>,
    onValueChange: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(question, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(12.dp))
        Slider(
            value = value.toFloat(),
            onValueChange = { onValueChange(it.toInt()) },
            valueRange = 0f..4f,
            steps = 3,
            modifier = Modifier.padding(horizontal = 16.dp),
            colors = SliderDefaults.colors(
                thumbColor = Color(0xFFADD8E6), // Light Blue for the thumb
                activeTrackColor = Color(0xFFADD8E6).copy(alpha = 0.8f), // Light Blue for the active track
                inactiveTrackColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = labels.getOrNull(value) ?: "",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}