package com.example.firstapp

import BinauralBeatsScreen
import FactDisplayScreen
import SessionManager

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.firstapp.ui.theme.FirstappTheme

class MainActivity : ComponentActivity() {

    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        sessionManager = SessionManager(this)

        if (!sessionManager.isLoggedIn()) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return // Important: Exit onCreate to prevent Compose content from being set
        }

        setContent {
            FirstappTheme {
                AppNavigator(sessionManager = sessionManager) // Pass sessionManager
            }
        }
    }
}

@Composable
fun AppNavigator(sessionManager: SessionManager) {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "landing") {
        composable("landing") { LandingScreen(navController = navController, sessionManager = sessionManager) } // Pass sessionManager
        composable("questionnaire") { QuestionnaireScreen(navController) }
        composable("breathing") { BreathingExerciseScreen(navController) }
        composable("soundLibrary") { SoundLibraryScreen(navController) }
        composable("preloaded_sound_library") { PreLoadedSoundLibraryScreen(navController) }
        composable("binaural_beats") { BinauralBeatsScreen(navController) }
        composable("box_breathing") { BoxBreathingScreen(navController) }
        composable("478_breathing") { FSEBreathingScreen(navController) }
        composable("diaphragmatic_breathing") { DiaphragmaticBreathingScreen(navController) }
        composable("altNostril_breathing") { AlternateNostrilBreathingScreen(navController) }
        composable("wimHof_breathing") { WimHofBreathingScreen(navController) }
        composable("binaural_alpha") { AlphaBeatScreen(navController) }
        composable("binaural_beta") { BetaBeatScreen(navController) }
        composable("binaural_theta") { ThetaBeatScreen(navController) }
        composable("binaural_delta") { DeltaBeatScreen(navController) }

        composable("binaural_beats_with_suggestion/{suggestedBeat}") { backStackEntry ->
            val suggestedBeat = backStackEntry.arguments?.getString("suggestedBeat")
            BinauralBeatsScreen(navController, suggestedBeat = suggestedBeat)
        }
        // Add the FactDisplayScreen with factMessage argument
        composable("fact_display/{factMessage}/{recommendedBeat}") { backStackEntry ->
            val factMessage = backStackEntry.arguments?.getString("factMessage") ?: "No fact available"
            val recommendedBeat = backStackEntry.arguments?.getString("recommendedBeat") ?: "binaural_alpha"
            FactDisplayScreen(
                navController = navController,
                factMessage = factMessage,
                recommendedBeat = recommendedBeat
            )
        }

    }
}