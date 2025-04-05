package com.example.firstapp
import SoundLibraryScreen
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
    NavHost(navController, startDestination = "landing") {
        composable("landing") { LandingScreen(navController) }
        composable("questionnaire") { QuestionnaireScreen(navController) }
        composable("breathing") { BreathingExerciseScreen(navController) }
        composable("soundLibrary") { SoundLibraryScreen(navController) }
        composable("preloaded_sound_library") { PreLoadedSoundLibraryScreen(navController) }
        composable("binaural_beats") { BinauralBeatsScreen(navController) }



        composable("box_breathing") { BoxBreathingScreen(navController)}
        composable("478_breathing") { FSEBreathingScreen(navController)}
        composable("diaphragmatic_breathing") {DiaphragmaticBreathingScreen(navController)}
        composable("altNostril_breathing") {AlternateNostrilBreathingScreen(navController)}
        composable("wimHof_breathing") {WimHofBreathingScreen(navController)}



    }
}
