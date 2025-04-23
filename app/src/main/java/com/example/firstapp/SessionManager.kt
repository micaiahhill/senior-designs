import android.content.Context
import android.content.SharedPreferences
import com.example.firstapp.User

class SessionManager(private val context: Context) {

    private val pref: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = pref.edit()

    companion object {
        private const val PREF_NAME = "UserSessionPref"
        private const val IS_USER_LOGIN = "IsLoggedIn"
        const val KEY_USERNAME = "username"
        const val KEY_EMAIL = "email"

        private const val KEY_LAST_SESSION = "last_active_session"
        private const val SESSION_DATA_PREFIX = "session_data_"
        private const val SESSION_EXISTS_PREFIX = "session_exists_"
    }

    /**
     * Create login session
     */
    fun createLoginSession(user: User) {
        editor.putBoolean(IS_USER_LOGIN, true)
        editor.putString(KEY_USERNAME, user.username)
        editor.putString(KEY_EMAIL, user.email)
        editor.apply()
    }

    /**
     * Check login status
     */
    fun isLoggedIn(): Boolean {
        return pref.getBoolean(IS_USER_LOGIN, false)
    }

    /**
     * Get stored session data
     */
    fun getUserDetails(): HashMap<String, String?> {
        val user = HashMap<String, String?>()
        user[KEY_USERNAME] = pref.getString(KEY_USERNAME, null)
        user[KEY_EMAIL] = pref.getString(KEY_EMAIL, null)
        return user
    }

    /**
     * Clear only user login details, but keep session data intact
     */
    fun logoutUser() {
        editor.remove(KEY_USERNAME)
        editor.remove(KEY_EMAIL)
        editor.remove(IS_USER_LOGIN) // Only remove login data
        editor.apply()

        // No need to clear session data here, as we want to retain it for later
    }

    /**
     * Save last active session ID
     */
    fun saveActiveSession(sessionId: String) {
        editor.putString(KEY_LAST_SESSION, sessionId)
        editor.apply()
    }

    fun getLastActiveSession(): String? {
        return pref.getString(KEY_LAST_SESSION, null)
    }

    fun clearLastSession() {
        editor.remove(KEY_LAST_SESSION)
        editor.apply()
    }

    /**
     * Save specific session data
     */
    fun saveSessionData(sessionId: String, technique: String, currentStep: Int) {
        editor.putString("${SESSION_DATA_PREFIX}${sessionId}_technique", technique)
        editor.putInt("${SESSION_DATA_PREFIX}${sessionId}_currentStep", currentStep)
        editor.apply()
    }

    fun getSessionTechnique(sessionId: String): String? {
        return pref.getString("${SESSION_DATA_PREFIX}${sessionId}_technique", null)
    }

    fun getSessionCurrentStep(sessionId: String): Int {
        return pref.getInt("${SESSION_DATA_PREFIX}${sessionId}_currentStep", -1)
    }

    fun markSessionAsExisting(sessionId: String) {
        editor.putBoolean("$SESSION_EXISTS_PREFIX$sessionId", true)
        editor.apply()
    }

    fun doesSessionExist(sessionId: String): Boolean {
        return pref.getBoolean("$SESSION_EXISTS_PREFIX$sessionId", false)
    }

    fun removeSessionData(sessionId: String) {
        editor.remove("${SESSION_DATA_PREFIX}${sessionId}_technique")
        editor.remove("${SESSION_DATA_PREFIX}${sessionId}_currentStep")
        editor.remove("$SESSION_EXISTS_PREFIX$sessionId")
        editor.apply()
    }

    fun getAllSessionIds(): Set<String> {
        val sessionIds = HashSet<String>()
        val allEntries = pref.all
        for ((key, _) in allEntries) {
            if (key.startsWith(SESSION_EXISTS_PREFIX)) {
                sessionIds.add(key.removePrefix(SESSION_EXISTS_PREFIX))
            }
        }
        return sessionIds
    }
}
