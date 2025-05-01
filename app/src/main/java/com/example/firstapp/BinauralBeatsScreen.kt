import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun BinauralBeatsScreen(navController: NavController, suggestedBeat: String? = null) {
    val beats = listOf("Alpha", "Beta", "Theta", "Delta")
    var selectedBeat by remember { mutableStateOf(suggestedBeat ?: "Alpha") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFB8DCD4)) // Soft blue like the BeatPlayer screen
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Binaural Beats",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            color = Color(0xFF333333)
        )

        if (suggestedBeat != null) {
            Text(
                text = "Suggested Beat: $suggestedBeat",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF666666)
            )
        }

        beats.forEach { beat ->
            Button(
                onClick = {
                    selectedBeat = beat
                    navController.navigate("binaural_${beat.lowercase()}")
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .height(48.dp)
            ) {
                Text("Listen to $beat Beat", color = Color(0xFF333333))
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                navController.navigate("soundLibrary")
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF88BDBC)),
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .height(48.dp)
        ) {
            Text("Try our SoundLibrary", color = Color.White)
        }
    }
}
