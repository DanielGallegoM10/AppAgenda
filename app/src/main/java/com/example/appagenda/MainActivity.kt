package com.example.appagenda

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.appagenda.Componentes.ListaDeElementos
import com.example.appagenda.Componentes.Titulo
import com.example.appagenda.Entidades.EntRegistro
import com.example.appagenda.ui.theme.AppAgendaTheme
import java.util.Date

class MainActivity : ComponentActivity() {

    // Lista reactiva de registros
    private val registros = mutableStateListOf<EntRegistro>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Registrar launcher
        val fichaLauncher = createFichaLauncher()

        // Inicializar registros
        val fechaPorDefecto = "2025-01-01 00:00"
        registros.addAll(
            listOf(
                EntRegistro(1, "Nombre1", "Descripcion1", fechaPorDefecto),
                EntRegistro(2, "Nombre2", "Descripcion2", fechaPorDefecto),
                EntRegistro(3, "Nombre3", "Descripcion3", fechaPorDefecto),
                EntRegistro(4, "Nombre4", "Descripcion4", fechaPorDefecto),
                EntRegistro(5, "Nombre5", "Descripcion5", fechaPorDefecto),
                EntRegistro(6, "Nombre6", "Descripcion6", fechaPorDefecto),
                EntRegistro(7, "Nombre7", "Descripcion7", fechaPorDefecto),
                EntRegistro(8, "Nombre8", "Descripcion8", fechaPorDefecto),
                EntRegistro(9, "Nombre9", "Descripcion9", fechaPorDefecto),
                EntRegistro(10, "Nombre10", "Descripcion10", fechaPorDefecto),
                EntRegistro(11, "Nombre11", "Descripcion11", fechaPorDefecto),
                EntRegistro(12, "Nombre12", "Descripcion12", fechaPorDefecto)
            )
        )

        // Configurar contenido
        setContent {
            AppAgendaTheme {
                Surface {
                    Column(Modifier
                        .fillMaxSize()
                        .padding(10.dp)) {
                        Titulo("ESTA ES TU AGENDA")
                        ListaDeElementos(registros, itemClickado = { registro ->
                            val intentFicha = Intent(this@MainActivity, FichaElemento::class.java)
                            intentFicha.putExtra("id", registro.codigoRegistro)
                            intentFicha.putExtra("nombre", registro.nombre)
                            intentFicha.putExtra("descripcion", registro.descripcion)
                            intentFicha.putExtra("fechaHora", registro.fecha)

                            fichaLauncher.launch(intentFicha) // Abrir actividad de edición
                        })
                    }
                }
            }
        }
    }

    // Función para registrar y manejar el resultado de FichaElemento
    private fun createFichaLauncher() =
        registerForActivityResult(
            androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                handleFichaResult(result.data)
            }
        }

    // Función para manejar los datos devueltos por FichaElemento
    private fun handleFichaResult(intent: Intent?) {
        val idActualizado = intent?.getIntExtra("id", -1)
        val nombreActualizado = intent?.getStringExtra("nombreGuardado").orEmpty()
        val descripcionActualizada = intent?.getStringExtra("descripcionGuardada").orEmpty()
        val fechaHoraActualizada = intent?.getStringExtra("fechaHoraGuardada").orEmpty()

        val idAEliminar = intent?.getIntExtra("idAEliminar", -1)


        if (idAEliminar != null && idAEliminar != -1) {
            for (item in registros) {
                if (item.codigoRegistro == idAEliminar) {
                    registros.remove(item)
                    break
                }
            }
        }

        if (idActualizado != null && idActualizado != -1) {
            val index = registros.indexOfFirst { it.codigoRegistro == idActualizado }
            if (index != -1) {
                registros[index] = registros[index].copy(
                    nombre = nombreActualizado,
                    descripcion = descripcionActualizada,
                    fecha = fechaHoraActualizada
                )
            }
        }
    }

    //Funcion para eliminar un elemento de la lista
    private fun eliminarElemento(intent: Intent?) {
        val idEncontrado = intent?.getIntExtra("id", -1)

        if (idEncontrado != null && idEncontrado != -1) {
            val index = registros.indexOfFirst { it.codigoRegistro == idEncontrado }
            if (index != -1) {
                registros.removeAt(index)
            }
        }
    }
}







