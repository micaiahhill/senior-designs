package com.example.firstapp


import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController

@Composable
fun QuestionnaireScreen(navController: NavController) {
    val questions = listOf(
        "How stressed do you feel?",
        "How focused are you right now?",
        "How much energy do you have?",
        "How anxious do you feel?"
    )

    var currentQuestionIndex by remember { mutableStateOf(0) }
    val answers = remember { mutableStateMapOf<String, Int>() }

    if (currentQuestionIndex < questions.size) {
        QuestionCard(
            question = questions[currentQuestionIndex],
            onAnswerSelected = { answer ->
                answers[questions[currentQuestionIndex]] = answer
                if (currentQuestionIndex < questions.size - 1) {
                    currentQuestionIndex++ // Move to next question
                } else {
                    analyzeResults(answers, navController)
                }
            }
        )
    }
}

fun analyzeResults(answers: Map<String, Int>, navController: NavController) {
    val stressLevel = answers["How stressed do you feel?"] ?: 3
    val anxietyLevel = answers["How anxious do you feel?"] ?: 3

    if (stressLevel >= 4 || anxietyLevel >= 4) {
        navController.navigate("breathing") // High stress/anxiety → breathing exercises
    } else {
        navController.navigate("soundLibrary") // Low stress → sound library
    }
}
