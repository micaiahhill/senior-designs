import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun FactDisplayScreen(
    navController: NavController,
    factMessage: String,
    recommendedBeat: String
) {
    var isVisible by remember { mutableStateOf(false) }

    // Animate visibility
    LaunchedEffect(Unit) {
        isVisible = true
    }

    // Infinite transition for gradient animation
    val infiniteTransition = rememberInfiniteTransition()
    val offset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 10000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    // Animated gradient background
    val animatedGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFB3D6FC), // sky blue
            Color(0xFFB8D5EA), // light pink
            Color(0xFFE4E4E7)  // soft yellow
        ),
        startY = 0f + offset,
        endY = 1000f + offset
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(animatedGradient)
            .padding(24.dp)
    ) {
        AnimatedVisibility(visible = isVisible) {
            Column(
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "🌿 Take a breath. You’re in the right place.",
                    fontSize = 20.sp
                )

                Text(
                    text = "🎧 For the best experience, use headphones.",
                    fontSize = 16.sp
                )

                Text(
                    text = "Why this Beat is Recommended:",
                    fontSize = 18.sp
                )

                Text(
                    text = factMessage,
                    fontSize = 16.sp
                )

                Button(onClick = {
                    navController.navigate(recommendedBeat)
                }) {
                    Text("Start Listening")
                }
            }
        }
    }
}
