package com.example.firstapp

import android.content.Context
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.*
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@Composable
fun LandingScreen(navController: NavController) {
    val scope = rememberCoroutineScope()

    // For the one-time rise (0f to 1f over 4s)
    val riseProgress = remember { Animatable(0f) }

    // After rise completes, bobbing begins
    val bobbingTransition = rememberInfiniteTransition()
    val bobbingOffset by bobbingTransition.animateFloat(
        initialValue = -10f, // controls bobbing distance of circle
        targetValue = 10f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    // Trigger the sunrise animation once
    LaunchedEffect(Unit) {
        riseProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 4000, easing = FastOutSlowInEasing)
        )
    }

    // Colors
    val bottomColor = Color(0xFFE3F2FD)
    val topStartColor = Color(0xFFE3F2FD)
    val topEndColor = Color(0xFFFFEB3B)

    // Interpolated top background color
    val topBackgroundColor = lerp(topStartColor, topEndColor, riseProgress.value)

    val screenHeight = LocalDensity.current.run { LocalConfiguration.current.screenHeightDp.dp.toPx() }

    // Calculate rising Y offset from center to top half
    val startOffsetY = screenHeight / 2
    val endOffsetY = screenHeight / 5  // Top half center, controls final position of circle on screen
    val baseOffsetY = lerp(startOffsetY, endOffsetY, riseProgress.value)

    val finalOffsetY = with(LocalDensity.current) { (baseOffsetY + bobbingOffset).toDp() }

    Box(modifier = Modifier.fillMaxSize()) {
        // Top Half Background (animated)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .background(topBackgroundColor)
                .align(Alignment.TopCenter)
        )

        // Bottom Half Background (static)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .background(bottomColor)
                .align(Alignment.BottomCenter)
        )

        // Rising + Bobbing Sun
        Box(
            modifier = Modifier
                .size(100.dp)
                .offset(y = finalOffsetY)
                .align(Alignment.TopCenter)
                .clip(CircleShape)
                .background(Color.White)
        )

        // Bottom content: app name + button
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 64.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "PreZen",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { navController.navigate("questionnaire") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
            ) {
                Text("Start Assessment", fontSize = 18.sp, color = Color.White)
            }
            Spacer(modifier = Modifier.height(16.dp))

            // 👇 Add this line
            LogoutButton(navController, LocalContext.current)
        }
    }
}

@Composable
fun LogoutButton(navController: NavController, context: Context) {
    val scope = rememberCoroutineScope()
    Button(onClick = {
        scope.launch {
            setLoggedIn(context, false)
            setActiveUser(context, "")
            navController.navigate("auth_choice") {
                popUpTo(0) { inclusive = true }
            }
        }
    }) {
        Text("Logout")
    }
}

