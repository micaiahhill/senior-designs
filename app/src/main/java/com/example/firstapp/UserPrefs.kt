// UserPrefs.kt
package com.example.firstapp

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "user_prefs")

object UserPrefsKeys {
    val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
    val LAST_SCREEN = stringPreferencesKey("last_screen")
    val USER_CREDENTIALS = stringSetPreferencesKey("user_credentials")
    val ACTIVE_USER = stringPreferencesKey("active_user")
}

// -------------------------
// Login State
// -------------------------

suspend fun setLoggedIn(context: Context, value: Boolean) {
    context.dataStore.edit { prefs ->
        prefs[UserPrefsKeys.IS_LOGGED_IN] = value
    }
}

suspend fun isLoggedIn(context: Context): Boolean {
    return context.dataStore.data.map { it[UserPrefsKeys.IS_LOGGED_IN] ?: false }.first()
}

// -------------------------
// Last Screen
// -------------------------

suspend fun saveLastScreen(context: Context, screen: String) {
    context.dataStore.edit { prefs ->
        prefs[UserPrefsKeys.LAST_SCREEN] = screen
    }
}

suspend fun getLastScreen(context: Context): String? {
    return context.dataStore.data.map { it[UserPrefsKeys.LAST_SCREEN] }.first()
}

// -------------------------
// Active User
// -------------------------

suspend fun setActiveUser(context: Context, email: String) {
    context.dataStore.edit { prefs -> prefs[UserPrefsKeys.ACTIVE_USER] = email }
}

suspend fun getActiveUser(context: Context): String? {
    return context.dataStore.data.map { it[UserPrefsKeys.ACTIVE_USER] }.first()
}

// -------------------------
// Local User Registration / Validation
// -------------------------

suspend fun registerUser(context: Context, email: String, password: String): Boolean {
    val entry = "$email:$password"
    val existing = context.dataStore.data.map { it[UserPrefsKeys.USER_CREDENTIALS] ?: emptySet() }.first()
    if (existing.any { it.startsWith("$email:") }) return false  // Already exists
    context.dataStore.edit { it[UserPrefsKeys.USER_CREDENTIALS] = existing + entry }
    return true
}

suspend fun isValidUser(context: Context, email: String, password: String): Boolean {
    val entry = "$email:$password"
    val users = context.dataStore.data.map { it[UserPrefsKeys.USER_CREDENTIALS] ?: emptySet() }.first()
    return users.contains(entry)
}
