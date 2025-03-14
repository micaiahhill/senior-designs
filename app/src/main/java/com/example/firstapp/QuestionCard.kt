package com.example.firstapp


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionCard(question: String, onAnswerSelected: (Int) -> Unit) {
    val moodColors = listOf(
        Color(0xFF4CAF50), // 1 - Relaxed
        Color(0xFF8BC34A), // 2 - Slightly Stressed
        Color(0xFFFFEB3B), // 3 - Neutral
        Color(0xFFFF9800), // 4 - Stressed
        Color(0xFFF44336)  // 5 - Highly Stressed
    )

    val moodLabels = listOf("😌", "🙂", "😐", "😟", "😫")

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Mood Assessment") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(question, fontSize = 24.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                moodLabels.forEachIndexed { index, emoji ->
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .background(moodColors[index], shape = RoundedCornerShape(8.dp))
                            .clickable { onAnswerSelected(index + 1) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(emoji, fontSize = 24.sp)
                    }
                }
            }
        }
    }
}
