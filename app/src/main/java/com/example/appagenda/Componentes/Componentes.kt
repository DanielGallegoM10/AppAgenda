package com.example.appagenda.Componentes

import PreferencesManager
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.coerceAtLeast
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appagenda.Entidades.EntRegistro
import com.example.appagenda.R
import com.example.appagenda.ui.theme.ThemeMode
import kotlinx.coroutines.launch
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
            fontFamily = FontFamily.SansSerif,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun TituloFicha(titulo: String) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(60.dp), Arrangement.Center, Alignment.CenterVertically
    ) {
        Text(
            text = titulo,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontFamily = FontFamily.SansSerif,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun ListaDeElementos(registros: List<EntRegistro>, itemClickado: (EntRegistro) -> Unit) {
    // Mantenemos un estado independiente para cada checkbox en el mapa
    val checkboxStates = remember { mutableStateMapOf<Int, Boolean>() }

    LazyColumn {
        items(registros, key = { it.codigoRegistro }) { registro ->
            if (!checkboxStates.containsKey(registro.codigoRegistro)) {
                checkboxStates[registro.codigoRegistro] = false
            }

            Column {
                Elemento(
                    registro = registro,
                    itemClickado = itemClickado,
                    isChecked = checkboxStates[registro.codigoRegistro] ?: false,
                    onCheckedChange = { isChecked ->
                        // Actualizar el estado de ese checkbox específico
                        checkboxStates[registro.codigoRegistro] = isChecked
                    }
                )
            }
        }
    }
}

@Composable
fun Elemento(
    registro: EntRegistro,
    itemClickado: (EntRegistro) -> Unit,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    val context = LocalContext.current

    var expanded by rememberSaveable { mutableStateOf(false) }
    val extraPadding by animateDpAsState(
        if (expanded) 48.dp else 0.dp, animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .clickable { itemClickado(registro) }
    ) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = extraPadding.coerceAtLeast(0.dp))
            ) {
                Text(text = registro.nombre, fontSize = 20.sp)
                Text(
                    text = registro.fecha, style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold
                    )
                )
                if (expanded) {
                    Text(
                        text = registro.descripcion,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
            Column(
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(
                    imageVector = if (expanded) Icons.Rounded.VisibilityOff else Icons.Rounded.Visibility,
                    contentDescription = "Ver más",
                    modifier = Modifier
                        .height(50.dp)
                        .width(40.dp)
                        .clickable { expanded = !expanded }
                )

                CheckboxScreen(
                    context = context,
                    codigoRegistro = registro.codigoRegistro,
                    state = remember { mutableStateOf(isChecked) },
                    onCheckedChange = onCheckedChange
                )
            }
        }
    }
}

@Composable
fun CheckboxScreen(
    context: Context,
    codigoRegistro: Int,
    state: MutableState<Boolean>,
    onCheckedChange: (Boolean) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    val savedState = remember { mutableStateOf(PreferencesManager.getCheckboxState(context, codigoRegistro)) }
    LaunchedEffect(savedState.value) {
        state.value = savedState.value
    }

    Checkbox(
        checked = state.value,
        onCheckedChange = { isChecked ->
            state.value = isChecked
            onCheckedChange(isChecked)
            coroutineScope.launch {
                PreferencesManager.saveCheckboxState(context, codigoRegistro, isChecked)
            }
        },
        enabled = true,
        colors = CheckboxDefaults.colors(
            checkedColor = Color.Green,
            uncheckedColor = Color.Green,
            checkmarkColor = Color.Blue
        )
    )
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
            val registro = EntRegistro(1, "Nombre1", "Descripcion1", "20-10-2025 20:00")
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
//            Elemento(registro, itemClickado = {})
        }


    }

}