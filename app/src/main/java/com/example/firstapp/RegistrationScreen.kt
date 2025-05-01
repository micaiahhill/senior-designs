package com.example.firstapp

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.firstapp.SunriseBackground
import kotlinx.coroutines.launch
import android.content.Context

@Composable
fun RegistrationScreen(navController: NavController, context: Context) {
    SunriseBackground {
        val scope = rememberCoroutineScope()
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var error by remember { mutableStateOf<String?>(null) }
        var passwordVisible by remember { mutableStateOf(false) }

        Column(Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.Center) {
            Text("Register", style = MaterialTheme.typography.headlineSmall)
            TextField(value = email, onValueChange = { email = it }, label = { Text("username") })
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("password") },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val icon = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                    val desc = if (passwordVisible) "Hide password" else "Show password"
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = icon, contentDescription = desc)
                    }
                },
                singleLine = true
            )
            error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            Spacer(Modifier.height(16.dp))
            Button(onClick = {
                scope.launch {
                    val success = registerUser(context, email, password)
                    if (success) navController.navigate("login") else error = "User already exists."
                }
            }) {
                Text("Create Account")
            }
        }
    }
}
