package com.example.firstapp

import android.content.Context
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.launch
import com.example.firstapp.dataStore
import androidx.datastore.preferences.core.edit


@Composable
fun ClearAllUserPrefsButton(context: Context) {
    val scope = rememberCoroutineScope()

    Button(
        onClick = {
            scope.launch {
                context.dataStore.edit { it.clear() }
            }
        },
        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
    ) {
        Text("Clear All Data", color = Color.White)
    }
}
