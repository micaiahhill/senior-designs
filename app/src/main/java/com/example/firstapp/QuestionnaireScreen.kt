package com.example.firstapp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class) //make sure to change once app is deployed
@Composable
fun QuestionnaireScreen(navController: NavController) {
    val questions = listOf(
        "Stress",
        "Energy",
        "Mental Clarity",
        "Body Tension"
    )

    val labelSets = listOf(
        listOf("Not at all", "Slightly", "Moderately", "Very", "Extremely"),            // Stress
        listOf("Drained", "Low", "Neutral", "Energetic", "Hyper"),                      // Energy
        listOf("Scattered", "Distracted", "Neutral", "Focused", "Laser-sharp"),         // Clarity
        listOf("Loose", "Mild", "Moderate", "Tight", "Extremely tense")                 // Tension
    )

    val answers = remember { mutableStateListOf(2, 2, 2, 2) } // All default to neutral (2)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Self Check-In") })
        },
        bottomBar = {
            Button(
                onClick = { analyzeResults(answers, navController) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Submit")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            questions.forEachIndexed { index, question ->
                QuestionCard(
                    question = question,
                    value = answers[index],
                    labels = labelSets[index],
                    onValueChange = { newValue -> answers[index] = newValue }
                )
            }
        }
    }
}

fun analyzeResults(answers: List<Int>, navController: NavController) {
    val stressHigh = answers[0] >= 3
    val energyHigh = answers[1] >= 3
    val clarityHigh = answers[2] >= 3
    val tensionHigh = answers[3] >= 3

    val key = listOf(stressHigh, energyHigh, clarityHigh, tensionHigh)

    val recommendation = when (key) {
        listOf(true, true, true, true) -> "wimHof_breathing"
        listOf(true, true, true, false) -> "box_breathing"
        listOf(true, true, false, true) -> "altNostril_breathing"
        listOf(true, true, false, false) -> "478_breathing"
        listOf(true, false, true, true) -> "altNostril_breathing"
        listOf(true, false, true, false) -> "box_breathing"
        listOf(true, false, false, true) -> "478_breathing"
        listOf(true, false, false, false) -> "soundLibrary" // if this exists
        listOf(false, true, true, true) -> "diaphragmatic_breathing"
        listOf(false, true, true, false) -> "box_breathing"
        listOf(false, true, false, true) -> "diaphragmatic_breathing"
        listOf(false, true, false, false) -> "box_breathing"
        listOf(false, false, true, true) -> "diaphragmatic_breathing"
        listOf(false, false, true, false) -> "box_breathing"
        listOf(false, false, false, true) -> "diaphragmatic_breathing"
        listOf(false, false, false, false) -> "478_breathing"
        else -> "box_breathing"
    }


    navController.navigate("recommendation/$recommendation")
}
