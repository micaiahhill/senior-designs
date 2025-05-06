package com.example.firstapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionnaireScreen(navController: NavController) {
    val questions = listOf(
        "How are you feeling stress-wise today?",
        "How’s your energy level right now?",
        "How clear does your mind feel?",
        "How does your body feel physically?"
    )

    val labelSets = listOf(
        listOf("At ease", "A little tense", "Some tension", "Pretty stressed", "Very stressed"),
        listOf("Exhausted", "Tired", "Okay", "Energized", "Buzzing"),
        listOf("Cloudy", "A bit foggy", "Neutral", "Clear", "Crystal clear"),
        listOf("Relaxed", "A little tight", "Moderate tension", "Tense", "Very tight")
    )

    val answers = remember { mutableStateListOf(2, 2, 2, 2) } // Neutral defaults

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Check In With Yourself") })
        },
        bottomBar = {
            Button(
                onClick = { analyzeResults(answers, navController) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp, pressedElevation = 4.dp)
            ) {
                Text("Show Me a Recommendation")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
                .background(Color(0xFFFFF1E5)), // Warm peachy background
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
    val stressLevel = answers[0]
    val tensionLevel = answers[3]

    val energyLevel = answers[1]
    val clarityLevel = answers[2]

    val highAnxiety = stressLevel >= 3 || tensionLevel >= 3

    val recommendation = if (highAnxiety) {
        // Prioritize breathing techniques for higher anxiety
        val key = listOf(stressLevel >= 3, energyLevel >= 3, clarityLevel >= 3, tensionLevel >= 3)
        when (key) {
            listOf(true, true, true, true) -> "wimHof_breathing"
            listOf(true, true, true, false) -> "box_breathing"
            listOf(true, true, false, true) -> "altNostril_breathing"
            listOf(true, true, false, false) -> "478_breathing"
            listOf(true, false, true, true) -> "altNostril_breathing"
            listOf(true, false, true, false) -> "box_breathing"
            listOf(true, false, false, true) -> "478_breathing"
            listOf(true, false, false, false) -> "binaural_beats" // Consider binaural beats if only stress is high
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
    } else {
        // Recommend sound library or specific binaural beats for lower anxiety
        if (stressLevel <= 2 && tensionLevel <= 2) {
            // Consider recommending based on other factors like energy and clarity
            if (energyLevel <= 2 && clarityLevel >= 3) {
                "binaural_alpha" // Calm alertness & focus
            } else if (energyLevel >= 3 && clarityLevel <= 2) {
                "binaural_beta"  // Active thinking & stress reduction
            } else if (energyLevel <= 2 && clarityLevel <= 2) {
                "binaural_theta" // Meditation & creativity
            } else {
                "preloaded_sound_library" // Default to sound library if no strong indicators
            }
        } else {
            "binaural_beats" // Fallback to the general binaural beats screen if some mild anxiety
        }
    }

    navController.navigate("recommendation/$recommendation")
}

