package com.example.appagenda

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Button
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
import androidx.compose.ui.res.painterResource
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

        val bdd = BDDAgenda(this)
        val dbHelper = RegistroDBHelper(this)


        registros.addAll(dbHelper.getRegistros())

        // Registrar launcher
        val fichaLauncher = createFichaLauncher()

        // Configurar contenido
        setContent {
            AppAgendaTheme {
                Surface {
                    Column(
                        Modifier
                            .fillMaxSize()

                            .background(Color.Cyan)
                    ) {

                        Row(
                            Modifier
                                .fillMaxWidth()
                                .height(100.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Titulo("MY AGENDA")
                        }
                        Row (Modifier
                            .fillMaxWidth()
                            .height(30.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.Top){
                            Text(
                                text = "Añade aqui tus eventos",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                fontFamily = FontFamily.Serif ,
                            )
                        }

                        ListaDeElementos(registros, itemClickado = { registro ->
                            val intentFicha = Intent(this@MainActivity, FichaElemento::class.java)
                            intentFicha.putExtra("id", registro.codigoRegistro)
                            intentFicha.putExtra("nombre", registro.nombre)
                            intentFicha.putExtra("descripcion", registro.descripcion)
                            intentFicha.putExtra("fechaHora", registro.fecha)

                            fichaLauncher.launch(intentFicha) // Abrir actividad de edición
                        })
                        Row(
                            Modifier
                                .fillMaxSize()
                                .height(50.dp)
                                .padding(30.dp),
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Button(
                                onClick = {
                                    val intentFicha =
                                        Intent(this@MainActivity, FichaElemento::class.java)
                                    intentFicha.putExtra("id", 0)

                                    fichaLauncher.launch(intentFicha) // Abrir actividad de edición
                                }
                            ) {
                                Text("NUEVO EVENTO")
                            }

                        }
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

        // Si se solicita eliminar un registro
        if (idAEliminar != null && idAEliminar != -1) {
            registros.removeAll { it.codigoRegistro == idAEliminar }
        }

        // Si hay un registro actualizado o nuevo
        if (idActualizado != null && idActualizado > 0) {
            val index = registros.indexOfFirst { it.codigoRegistro == idActualizado }
            if (index != -1) {
                // Actualiza un registro existente
                registros[index] = registros[index].copy(
                    nombre = nombreActualizado,
                    descripcion = descripcionActualizada,
                    fecha = fechaHoraActualizada
                )
            } else {
                // Agrega un nuevo registro
                registros.add(
                    EntRegistro(
                        codigoRegistro = idActualizado,
                        nombre = nombreActualizado,
                        descripcion = descripcionActualizada,
                        fecha = fechaHoraActualizada
                    )
                )
            }
        }
    }


}







