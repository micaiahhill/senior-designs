package com.example.firstapp

import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController

@Composable
fun QuestionnaireScreen(navController: NavController) {
    // List of initial questions
    val questions = listOf(
        "How stressed do you feel?",
        "How focused are you right now?",
        "How much energy do you have?",
        "How anxious do you feel?"
    )

    // Additional questions to determine which technique
    val techniqueQuestions = listOf(
        "Do you prefer a calm and relaxing experience?",
        "Would you prefer a technique to increase focus or relaxation?"
    )

    var currentQuestionIndex by remember { mutableStateOf(0) }
    var isTechniquesChosen by remember { mutableStateOf(false) }
    val answers = remember { mutableStateMapOf<String, Int>() }

    // Logic to handle the question flow
    if (!isTechniquesChosen) {
        if (currentQuestionIndex < questions.size) {
            QuestionCard(
                question = questions[currentQuestionIndex],
                onAnswerSelected = { answer ->
                    answers[questions[currentQuestionIndex]] = answer
                    if (currentQuestionIndex < questions.size - 1) {
                        currentQuestionIndex++ // Move to next question
                    } else {
                        // Once all 4 questions are answered, move to technique questions
                        currentQuestionIndex = 0 // Reset index for technique questions
                        isTechniquesChosen = true // Set flag to switch to technique questions
                    }
                }
            )
        }
    } else {
        // Now asking technique questions
        if (currentQuestionIndex < techniqueQuestions.size) {
            QuestionCard(
                question = techniqueQuestions[currentQuestionIndex],
                onAnswerSelected = { answer ->
                    answers[techniqueQuestions[currentQuestionIndex]] = answer
                    if (currentQuestionIndex < techniqueQuestions.size - 1) {
                        currentQuestionIndex++ // Move to next technique question
                    } else {
                        // Once all questions are answered, analyze results and decide on technique
                        analyzeResults(answers, navController)
                    }
                }
            )
        }
    }
}

fun analyzeResults(answers: Map<String, Int>, navController: NavController) {
    val stressLevel = answers["How stressed do you feel?"] ?: 3
    val anxietyLevel = answers["How anxious do you feel?"] ?: 3
    val focusLevel = answers["How focused are you right now?"] ?: 3
    val energyLevel = answers["How much energy do you have?"] ?: 3

    val prefersCalmExperience = answers["Do you prefer a calm and relaxing experience?"] ?: 3
    val prefersFocusOrRelaxation = answers["Would you prefer a technique to increase focus or relaxation?"] ?: 3

    // ───── BREATHING TECHNIQUE LOGIC ─────
    val recommendedTechnique = when {
        stressLevel >= 4 && anxietyLevel >= 4 -> "box_breathing"
        stressLevel >= 3 && prefersCalmExperience >= 4 -> "diaphragmatic_breathing"
        focusLevel <= 2 && energyLevel <= 2 -> "478_breathing"
        anxietyLevel >= 3 && prefersFocusOrRelaxation <= 2 -> "altNostril_breathing"
        energyLevel >= 4 && prefersFocusOrRelaxation >= 4 -> "wimHof_breathing"
        else -> "box_breathing"
    }

    val techniqueFact = when (recommendedTechnique) {
        "box_breathing" -> "Box breathing is a simple, structured technique used by Navy SEALs and athletes to reduce stress and increase performance."
        "478_breathing" -> "4-7-8 breathing helps slow the heart rate and reduce anxiety by regulating your nervous system."
        "diaphragmatic_breathing" -> "Diaphragmatic breathing strengthens the diaphragm and helps you use less effort to breathe."
        "altNostril_breathing" -> "Alternate nostril breathing balances the left and right hemispheres of the brain and calms the mind."
        "wimHof_breathing" -> "Wim Hof breathing combines deep rhythmic inhalations with breath retention to boost energy and resilience."
        else -> "Explore different breathing methods to improve focus and well-being."
    }

    // If the stress or anxiety is critically high, prioritize breathing first
    if (stressLevel >= 4 || anxietyLevel >= 4) {
        navController.navigate("fact_display/$techniqueFact/$recommendedTechnique")
        return
    }

    // ───── BINAURAL BEATS LOGIC ─────
    val recommendedBeat = when {
        focusLevel >= 4 && energyLevel >= 3 -> "binaural_beta"     // Alertness
        focusLevel <= 2 && prefersCalmExperience >= 3 -> "binaural_theta" // Creativity
        stressLevel <= 2 && anxietyLevel <= 2 && prefersCalmExperience >= 4 -> "binaural_delta" // Deep rest
        else -> "binaural_alpha" // General calm
    }

    val beatFact = when (recommendedBeat) {
        "binaural_beta" -> "Beta waves are associated with concentration and heightened alertness. Studies have shown that listening to Beta waves can enhance cognitive performance and focus. (Source: American Psychological Association)"
        "binaural_theta" -> "Theta waves promote creativity, relaxation, and meditation. They can enhance your ability to access deep states of relaxation. (Source: National Institute of Mental Health)"
        "binaural_delta" -> "Delta waves are linked with deep sleep and restorative states. Listening to Delta waves can support restful sleep. (Source: National Sleep Foundation)"
        "binaural_alpha" -> "Alpha waves help in reducing stress and promoting relaxation. They can improve mental clarity and are often used in meditation. (Source: Harvard Medical School)"
        else -> "Explore our sound library for more beats and techniques to improve your mood!"
    }

    navController.navigate("fact_display/$beatFact/$recommendedBeat")
}