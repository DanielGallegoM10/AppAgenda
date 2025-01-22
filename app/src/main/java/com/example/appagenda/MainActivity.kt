package com.example.appagenda

import android.annotation.SuppressLint
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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppAgendaTheme {
                Surface {
                    Column(Modifier.fillMaxSize().padding(10.dp)) {
                        val registros = listOf(
                                    EntRegistro(1, "Nombre1", "Descripcion1", java.util.Date()),
                            EntRegistro(2, "Nombre2", "Descripcion2", java.util.Date()),
                            EntRegistro(3, "Nombre3", "Descripcion3", java.util.Date()),
                            EntRegistro(4, "Nombre4", "Descripcion4", java.util.Date()),
                            EntRegistro(5, "Nombre5", "Descripcion5", java.util.Date()),
                            EntRegistro(6, "Nombre6", "Descripcion6", java.util.Date()),
                            EntRegistro(7, "Nombre7", "Descripcion7", java.util.Date()),
                            EntRegistro(8, "Nombre8", "Descripcion8", java.util.Date()),
                            EntRegistro(9, "Nombre9", "Descripcion9", java.util.Date()),
                            EntRegistro(10, "Nombre10", "Descripcion10", java.util.Date()),
                            EntRegistro(11, "Nombre11", "Descripcion11", java.util.Date()),
                            EntRegistro(12, "Nombre12", "Descripcion12", java.util.Date())

                            )
                        Titulo("ESTA ES TU AGENDA")
                        ListaDeElementos(registros, itemClickado = { registro -> Log.i("Daniel", registro.nombre) })

                    }
                }
            }
        }
    }
}



