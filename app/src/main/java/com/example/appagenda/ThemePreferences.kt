import android.content.Context
import android.content.SharedPreferences
import com.example.appagenda.ui.theme.ThemeMode

object ThemePreferences {
    private const val PREFS_NAME = "theme_prefs"
    private const val KEY_THEME = "theme_mode"

    fun saveTheme(context: Context, themeMode: ThemeMode) {
        val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_THEME, themeMode.name).apply()
    }

    fun getTheme(context: Context): ThemeMode {
        val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return ThemeMode.valueOf(prefs.getString(KEY_THEME, ThemeMode.SYSTEM.name) ?: ThemeMode.SYSTEM.name)
    }
}
