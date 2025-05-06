package com.example.firstapp
import BinauralBeatsScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.*
import com.example.firstapp.ui.theme.FirstappTheme

// Core Compose
import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.*

// Navigation
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

// Context
import androidx.compose.ui.platform.LocalContext

// Coroutine (if you're using launch inside composable)
import kotlinx.coroutines.launch

// Your app-specific screens (make sure these match your package structure)
import com.example.firstapp.*



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            FirstappTheme {
                AppNavigator()
            }
        }
    }
}

@Composable
fun AppNavigator() {
    val navController = rememberNavController()
    val context = LocalContext.current
    var loginCheckDone by remember { mutableStateOf(false) }
    var userIsLoggedIn by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        userIsLoggedIn = isLoggedIn(context)
        loginCheckDone = true
    }

    if (!loginCheckDone) {
        // Show loading indicator
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        NavHost(
            navController = navController,
            startDestination = if (userIsLoggedIn) "restore_prompt" else "auth_choice"
        ) {
            composable("auth_choice") { AuthChoiceScreen(navController) }
            composable("login") { LoginScreen(navController, context) }
            composable("register") { RegistrationScreen(navController, context) }
            composable("restore_prompt") { RestorePromptScreen(navController, context) }
            composable("landing") { LandingScreen(navController) }
            composable("questionnaire") { QuestionnaireScreen(navController) }
            composable("breathing") { BreathingExerciseScreen(navController) }

            composable("box_breathing") { BoxBreathingScreen(navController)}
            composable("478_breathing") { FSEBreathingScreen(navController)}
            composable("diaphragmatic_breathing") { DiaphragmaticBreathingScreen(navController)}
            composable("altNostril_breathing") { AlternateNostrilBreathingScreen(navController)}
            composable("wimHof_breathing") { WimHofBreathingScreen(navController)}

            composable("box_breathing_tutorial") { BoxBreathingTutorialScreen(navController) }
            composable("478_breathing_tutorial") { FSEBreathingTutorialScreen(navController) }
            composable("diaphragmatic_breathing_tutorial") { DiaphragmaticBreathingTutorialScreen(navController)}
            composable("altNostril_breathing_tutorial") { AlternateNostrilBreathingTutorialScreen(navController)}
            composable("wimHof_breathing_tutorial") { WimHofBreathingTutorialScreen(navController)}

            composable("preloaded_sound_library") { PreLoadedSoundLibraryScreen(navController) }
            composable("binaural_alpha") { AlphaBeatScreen(navController) }
            composable("binaural_beta") { BetaBeatScreen(navController) }
            composable("binaural_theta") { ThetaBeatScreen(navController) }
            composable("binaural_delta") { DeltaBeatScreen(navController) }
            composable("binaural_beats") { BinauralBeatsScreen(navController) }


            composable("recommendation/{tech}") { backStackEntry ->
                val tech = backStackEntry.arguments?.getString("tech") ?: "gentle_breathing"
                RecommendationScreen(tech = tech, navController = navController)
            }

            composable("activitySelector") { ActivitySelectorScreen(navController) }

            composable("soundLibrary") {
                SoundLibraryScreen(navController) // or however yours is named
            }

            composable("soundscapes") { SoundscapesScreen(navController)}

        }
    }
}

