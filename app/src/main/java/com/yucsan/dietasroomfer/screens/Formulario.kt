package com.yucsan.dietasroomfer.screens

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


import com.yucsan.dietasroomfer.modeloRoom.ComponenteDieta
import com.yucsan.dietasroomfer.modeloRoom.ComponenteDietaViewModel
import com.yucsan.dietasroomfer.modeloRoom.TipoComponente

@Composable
fun Formulario(
   navController: NavController,
   opcionesRadio: List<TipoComponente>,
   view: ComponenteDietaViewModel,
   context: Context
) {


   var nombre by remember { mutableStateOf("") }
   var grProt by remember { mutableStateOf("") }
   var grHC by remember { mutableStateOf("") }
   var grLip by remember { mutableStateOf("") }

   var esSimple = remember { mutableStateOf(true) }
   var seleccion = remember { mutableStateOf<TipoComponente>(TipoComponente.SIMPLE) }

   val nuevoComponenteDieta = ComponenteDieta(
      nombre = nombre,
      tipo = seleccion.value,
      grPro_ini = grProt.toDoubleOrNull() ?: 0.0,
      grHC_ini = grHC.toDoubleOrNull() ?: 0.0,
      grLip_ini = grLip.toDoubleOrNull() ?: 0.0
   )

   LazyColumn (modifier = Modifier.padding(16.dp)) {
      item {
         Column {
            Box(modifier = Modifier.fillMaxWidth()) {
               Column {
                  MisChips(opcionesRadio, seleccion, esSimple)

                  Text(
                     modifier = Modifier.padding(8.dp),
                     text = "${nuevoComponenteDieta.totalKcal} calorías"
                  )

                  OutlinedTextField(
                     value = nombre,
                     onValueChange = { newText ->
                        nuevoComponenteDieta.nombre = newText
                        nombre = newText
                     },
                     label = { Text("Escribe nombre") },
                     placeholder = { Text("Nombre") },
                     modifier = Modifier.fillMaxWidth()
                  )

                  if (seleccion.value == TipoComponente.PROCESADO || seleccion.value == TipoComponente.SIMPLE) {
                     Row(
                        modifier = Modifier
                           .fillMaxWidth()
                           .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                     ) {
                        OutlinedTextField(
                           value = grProt,
                           onValueChange = { newText ->
                              nuevoComponenteDieta.grPro_ini = newText.toDoubleOrNull() ?: 0.0
                              grProt = newText
                           },
                           label = { Text("Proteínas", fontSize = 14.sp, textAlign = TextAlign.Center) },
                           modifier = Modifier.weight(1f).padding(horizontal = 4.dp),

                           keyboardOptions = KeyboardOptions.Default.copy(
                              keyboardType = KeyboardType.Number
                           ),
                        )

                        OutlinedTextField(
                           value = grHC,
                           onValueChange = { newText ->
                              nuevoComponenteDieta.grHC_ini = newText.toDoubleOrNull() ?: 0.0
                              grHC = newText
                           },
                           label = { Text("Hidratos", fontSize = 14.sp, textAlign = TextAlign.Center) },
                           modifier = Modifier.weight(1f).padding(horizontal = 4.dp),
                           keyboardOptions = KeyboardOptions.Default.copy(
                              keyboardType = KeyboardType.Number
                           ),
                        )
                        OutlinedTextField(
                           value = grLip,
                           onValueChange = { newText ->
                              nuevoComponenteDieta.grLip_ini = newText.toDoubleOrNull() ?: 0.0
                              grLip = newText
                           },
                           label = { Text("Lípidos", fontSize = 14.sp, textAlign = TextAlign.Center) },
                           modifier = Modifier.weight(1f).padding(horizontal = 4.dp),
                           keyboardOptions = KeyboardOptions.Default.copy(
                              keyboardType = KeyboardType.Number
                           ),
                        )
                     }
                  }

                  Column(
                     verticalArrangement = Arrangement.spacedBy(8.dp), // Espacio entre botones
                     modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                  ) {
                     Button(
                        onClick = {
                           view.insertarComponenteDieta(nuevoComponenteDieta)
                           nombre = ""; grProt = ""; grHC = ""; grLip = ""
                        },
                        colors = ButtonDefaults.buttonColors(
                           containerColor = MaterialTheme.colorScheme.primary, // Color según el tema
                           contentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        modifier = Modifier.fillMaxWidth()
                     ) {
                        Text(text = "Guardar", fontSize = 18.sp)
                     }

                     Button(
                        onClick = {
                           nombre = ""; grProt = ""; grHC = ""; grLip = ""
                        },
                        colors = ButtonDefaults.buttonColors(
                           containerColor = MaterialTheme.colorScheme.secondary, // Color dinámico
                           contentColor = MaterialTheme.colorScheme.onSecondary
                        ),
                        modifier = Modifier.fillMaxWidth()
                     ) {
                        Text(text = "Borrar Formulario", fontSize = 18.sp)
                     }
                  }

               }
            }
         }
      }
   }

}

