package com.example.appagenda

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.appagenda.Componentes.DateTimeField
import com.example.appagenda.Componentes.Texto
import com.example.appagenda.Componentes.TextoLargo
import com.example.appagenda.Componentes.Titulo
import com.example.appagenda.ui.theme.AppAgendaTheme

class FichaElemento : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppAgendaTheme {
                Surface {
                    val nombreIntent = intent.getStringExtra("nombre")
                    val descripcion = intent.getStringExtra("descripcion")
                    val fechaYHora = intent.getStringExtra("fechaHora")

                    var nombre by rememberSaveable { mutableStateOf(nombreIntent) }
                    var descripcionValor by rememberSaveable { mutableStateOf(descripcion) }
                    var fechaHora by rememberSaveable { mutableStateOf(fechaYHora) }

                    Column(Modifier.fillMaxSize().padding(10.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                        Titulo("AÑADE UN EVENTO")
                        Texto(name = nombre.toString(), labelName = "Dime el nombre del evento", onValueChange = {nombre = it})
                        TextoLargo(name = descripcionValor.toString(), labelName = "Dime la descripcion del evento", onValueChange = {descripcionValor = it})
                        DateTimeField(fechaHora.toString(), onValueChange = {newDateTime -> fechaHora = newDateTime})

                        Row (Modifier.fillMaxWidth(), Arrangement.Center){
                            Button(
                                onClick = { /*TODO*/ },
                                modifier = Modifier.padding(10.dp)
                            ) {
                                Text(text = "Guardar")
                            }
                            Button(
                                onClick = { startActivity(Intent(this@FichaElemento, MainActivity::class.java)) },
                                modifier = Modifier.padding(10.dp)
                            ) {
                                Text(text = "Volver")
                            }
                            Button(
                                onClick = {

                                },
                                modifier = Modifier.padding(10.dp)
                            ) {
                                Text(text = "Eliminar")
                            }
                        }
                    }
                }
            }
        }
    }
}

