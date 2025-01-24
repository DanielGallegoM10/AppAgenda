package com.example.appagenda.Componentes

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.appagenda.Entidades.EntRegistro
import java.sql.Date
import java.util.Calendar

@Composable
fun Titulo(titulo: String) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(60.dp), Arrangement.Center, Alignment.CenterVertically
    ) {
        Text(
            text = titulo,
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontFamily = FontFamily.Cursive,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

//@Composable
//fun ListaDeElementos(){
//    val scrollState = rememberScrollState()
//    Column(Modifier.verticalScroll(scrollState)) {
//        Titulo("Agenda")
//        Titulo("Agenda")
//        Titulo("Agenda")
//        Titulo("Agenda")
//        Titulo("Agenda")
//        Titulo("Agenda")
//        Titulo("Agenda")
//        Titulo("Agenda")
//        Titulo("Agenda")
//        Titulo("Agenda")
//        Titulo("Agenda")
//        Titulo("Agenda")
//        Titulo("Agenda")
//        Titulo("Agenda")
//        Titulo("Agenda")
//        Titulo("Agenda")
//        Titulo("Agenda")
//
//    }
//}

@Composable
fun ListaDeElementos(registros: List<EntRegistro>, itemClickado: (EntRegistro) -> Unit) {
    LazyColumn {
        items(registros, key = { it.codigoRegistro }) { registro ->
            Column(){
                Elemento(registro, itemClickado)
            }
        }
    }
}

@Composable
fun Elemento(registro: EntRegistro, itemClickado: (EntRegistro) -> Unit) {
    Row(Modifier
        .fillMaxWidth()
        .height(100.dp)
        .clickable { itemClickado(registro) }) {
        Column(
            Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {

            Text(text = registro.nombre)
            Text(text = registro.descripcion)
            Text(text = registro.fecha)
        }
    }
}

@Composable
fun Texto(name: String, labelName: String, onValueChange: (String) -> Unit) {
    TextField(value = name, onValueChange = { onValueChange(it) }, Modifier.padding(20.dp),
        label = { Text(text = labelName) })
}

@Composable
fun TextoLargo(name: String, labelName: String, onValueChange: (String) -> Unit) {
    TextField(value = name,
        onValueChange = { onValueChange(it) },
        Modifier
            .height(200.dp)
            .padding(20.dp),
        label = { Text(text = labelName) })
}


@Composable
fun DateTimeField(selectedDateTime: String, onValueChange: (String) -> Unit) {
    val context = LocalContext.current

    // Obtener la fecha y hora actuales
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)

    // Mostrar el campo y los diálogos de selección
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { // Esto hace que toda el área sea clicable
                DatePickerDialog(
                    context,
                    { _, selectedYear, selectedMonth, selectedDay ->
                        TimePickerDialog(
                            context,
                            { _, selectedHour, selectedMinute ->
                                onValueChange(
                                    "$selectedDay/${selectedMonth + 1}/$selectedYear $selectedHour:$selectedMinute"
                                )
                            },
                            hour,
                            minute,
                            true // Usa formato de 24 horas
                        ).show()
                    },
                    year, month, day
                ).show()
            }
    ) {
        OutlinedTextField(
            value = selectedDateTime,
            onValueChange = {}, // Sin cambios directos en el campo
            label = { Text("Selecciona fecha y hora") },
            readOnly = true,
            enabled = false, // Deshabilitamos interacción directa con el campo
            modifier = Modifier.fillMaxWidth()
        )
    }
}


@Composable
fun ToolBar() {

}

@Preview(showBackground = true)
@Composable
fun VerComponentes() {
    Surface {
        Column(
            Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
//        val registros = listOf(
//            EntRegistro(1, "Nombre1", "Descripcion1", java.util.Date()),
//            EntRegistro(2, "Nombre2", "Descripcion2", java.util.Date()),
//            EntRegistro(3, "Nombre3", "Descripcion3", java.util.Date()),
//            EntRegistro(4, "Nombre4", "Descripcion4", java.util.Date()),
//            EntRegistro(5, "Nombre5", "Descripcion5", java.util.Date()),
//            EntRegistro(6, "Nombre6", "Descripcion6", java.util.Date()),
//            EntRegistro(7, "Nombre7", "Descripcion7", java.util.Date()),
//            EntRegistro(8, "Nombre8", "Descripcion8", java.util.Date()),
//            EntRegistro(9, "Nombre9", "Descripcion9", java.util.Date()),
//            EntRegistro(10, "Nombre10", "Descripcion10", java.util.Date()),
//            EntRegistro(11, "Nombre11", "Descripcion11", java.util.Date()),
//            EntRegistro(12, "Nombre12", "Descripcion12", java.util.Date())
//
//        )
//        Titulo("ESTA ES TU AGENDA")
//        ListaDeElementos(registros, itemClickado = {})
//                var name by rememberSaveable { mutableStateOf("") }
//                TextoLargo(name = name, labelName = "Dime el nombre del evento", onValueChange = {})

            ToolBar()
        }


    }

}