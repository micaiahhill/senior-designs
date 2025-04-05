import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import com.example.firstapp.R

@Composable
fun SoundLibraryScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Sound Library",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        // Preloaded Sound Library Section (Larger Icon)
        SoundLibrarySection(
            title = "Preloaded Sound Library",
            onClick = { navController.navigate("preloaded_sound_library") },
            iconResId = R.drawable.baseline_library_music_24, // Replace with your icon resource
            isLargeIcon = true // Indicate it's a large icon
        )

        // Binaural Beats Integration Section (Default Icon)
        SoundLibrarySection(
            title = "Binaural Beats Integration",
            onClick = { navController.navigate("binaural_beats") },
            iconResId = R.drawable.cadence_48px, // Replace with your icon resource
            isLargeIcon = true // Indicate it's a default icon
        )

        Button(onClick = { navController.popBackStack() }) {
            Text("Back to Home")
        }
    }
}

@Composable
fun SoundLibrarySection(title: String, onClick: () -> Unit, iconResId: Int, isLargeIcon: Boolean) { // Add isLargeIcon parameter
    Column(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = iconResId),
            contentDescription = title,
            modifier = if (isLargeIcon) Modifier.size(150.dp) else Modifier.size(24.dp) // Conditional icon size
        )
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}