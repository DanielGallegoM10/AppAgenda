import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object PreferencesManager {
    private const val PREFS_NAME = "theme_prefs"
    private const val KEY_PREFIX = "checkbox_state_"

    // Función para guardar el estado de un checkbox específico
    fun saveCheckboxState(context: Context, codigoRegistro: Int, isChecked: Boolean) {
        val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putBoolean(KEY_PREFIX + codigoRegistro, isChecked).apply()  // Usamos codigoRegistro como clave
    }

    // Función para obtener el estado de un checkbox específico
    fun getCheckboxState(context: Context, codigoRegistro: Int): Boolean {
        val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getBoolean(KEY_PREFIX + codigoRegistro, false)
    }
}

