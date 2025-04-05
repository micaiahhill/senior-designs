package com.example.firstapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale

@Composable
fun PreLoadedSoundLibraryScreen(navController: NavController? = null) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Title and Description
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Preloaded Sound Library",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = "Explore a collection of pre-recorded sounds.",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Grid Layout for Icons - Adaptive Columns
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 120.dp), // Adaptive columns
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) // Make the grid expand
        ) {
            items(6) { index ->
                Image(
                    painter = painterResource(id = R.drawable.baseline_add_box_24), // Use your image resource
                    contentDescription = "Icon ${index + 1}",
                    modifier = Modifier
                        .aspectRatio(1f) // Square aspect ratio
                        .clickable {
                            // Handle click for each icon
                        },
                    contentScale = ContentScale.Crop // Use Crop to fill the box without distortion
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // "See More" Button
        if (navController != null) {
            Text(
                text = "See More",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { navController.navigate("preloaded_sound_library_details") }
            )
        }
    }
}