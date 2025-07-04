package com.yucsan.dietasroomfer.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.yucsan.dietasroomfer.R
import com.yucsan.dietasroomfer.modeloRoom.ComponenteDieta
import com.yucsan.dietasroomfer.modeloRoom.ComponenteDietaViewModel
import com.yucsan.dietasroomfer.modeloRoom.TipoComponente

@Composable
fun ListaComponentesDieta(viewModel: ComponenteDietaViewModel, navController: NavController) {
   val componentes by viewModel.componentesDieta.observeAsState(initial = emptyList())
   var componenteSeleccionado by remember { mutableStateOf<ComponenteDieta?>(null) }

   val poppins = FontFamily(Font(R.font.poppins_regular, FontWeight.Normal))

   LazyColumn(
      modifier = Modifier.fillMaxSize().padding(8.dp)
   ) {
      items(componentes) { componente ->
         OutlinedCard(
            modifier = Modifier
               .fillMaxWidth()
               .padding(8.dp)
               .clickable {
                  if (componente.tipo.esCompuesto()) {
                     navController.navigate("gestion_componente/${componente.id}")
                  } else {
                     componenteSeleccionado = componente
                  }
               },
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.onPrimary)
         ) {
            Box(modifier = Modifier.padding(16.dp)) {
               Column {
                  Row(
                     modifier = Modifier.fillMaxWidth().padding(horizontal = 3.dp, vertical = 4.dp),
                     verticalAlignment = Alignment.CenterVertically
                  ) {
                     Column(modifier = Modifier.weight(3f), horizontalAlignment = Alignment.Start) {
                        Text(
                           text = componente.nombre,
                           style = MaterialTheme.typography.titleMedium,
                           fontWeight = FontWeight.Bold,
                           fontSize = 16.sp,
                           fontFamily = poppins
                        )
                     }
                     Box(
                        modifier = Modifier.weight(1f).fillMaxWidth(),
                        contentAlignment = Alignment.TopEnd
                     ) {
                        Text(
                           text = if (componente.tipo == TipoComponente.PROCESADO) "PROCES"  else  componente.tipo.name,
                           color = Color.White,
                           style = MaterialTheme.typography.bodySmall,
                           fontWeight = FontWeight.SemiBold,
                           modifier = Modifier
                              .background(getTipoColor(componente.tipo), RoundedCornerShape(8.dp))
                              .padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                     }
                  }
                  if (componente.tipo in listOf(TipoComponente.DIETA, TipoComponente.RECETA, TipoComponente.MENU)) {
                     MostrarTotalesIngredientes(viewModel, componente.id)
                  }
                  if (componente?.tipo in listOf(TipoComponente.SIMPLE, TipoComponente.PROCESADO)) {
                     Spacer(modifier = Modifier.height(8.dp))
                     Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                     ) {
                        Text("HC: ${componente?.totalGrHC}", style = MaterialTheme.typography.titleSmall)
                        Text("Lip: ${componente?.totalGrLip}", style = MaterialTheme.typography.titleSmall)
                        Text("Pro: ${componente?.totalGrPro}", style = MaterialTheme.typography.titleSmall)
                        Text(
                           "Cal: ${String.format("%.2f", componente?.totalKcal ?: 0.0)}",
                            style = MaterialTheme.typography.titleSmall,
                           fontWeight = FontWeight.Bold
                        )
                     }
                  }
               }
            }
         }
      }
   }

   if (componenteSeleccionado != null) {
      EditarComponenteDialog(
         componenteDieta = componenteSeleccionado!!,
         onDismiss = { componenteSeleccionado = null },
         onSave = { componenteEditado ->
            viewModel.actualizarComponenteDieta(componenteEditado)
            componenteSeleccionado = null
         },
         onDelete = { componente ->
            viewModel.borrarComponenteDieta(componente)
            componenteSeleccionado = null
         }
      )
   }
}

@Composable
fun EditarComponenteDialog(
   componenteDieta: ComponenteDieta,
   onDismiss: () -> Unit,
   onSave: (ComponenteDieta) -> Unit,
   onDelete: (ComponenteDieta) -> Unit
) {
   var nombre by remember { mutableStateOf(componenteDieta.nombre) }
   var hc by remember { mutableStateOf(componenteDieta.grHC_ini.toString()) }
   var lip by remember { mutableStateOf(componenteDieta.grLip_ini.toString()) }
   var pro by remember { mutableStateOf(componenteDieta.grPro_ini.toString()) }

   AlertDialog(
      onDismissRequest = onDismiss,
      title = { Text(text = "Editar Componente Dieta") },
      text = {
         Column {
            OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre") })
            OutlinedTextField(value = hc, onValueChange = { hc = it }, label = { Text("Hidratos de Carbono") },
               keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
            OutlinedTextField(value = lip, onValueChange = { lip = it }, label = { Text("Lípidos") },
               keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
            OutlinedTextField(value = pro, onValueChange = { pro = it }, label = { Text("Proteínas") },
               keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
         }
      },
      confirmButton = {
         Button(onClick = {
            val componenteEditado = componenteDieta.copy(
               nombre = nombre,
               grHC_ini = hc.toDoubleOrNull() ?: 0.0,
               grLip_ini = lip.toDoubleOrNull() ?: 0.0,
               grPro_ini = pro.toDoubleOrNull() ?: 0.0
            )
            onSave(componenteEditado)
         }) {
            Text("Guardar")
         }
      },
      dismissButton = {
         Row {
            Button(onClick = onDismiss) {
               Text("Cancelar")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { onDelete(componenteDieta) }, colors = ButtonDefaults.buttonColors(Color.Red)) {
               Icon(Icons.Default.Delete, contentDescription = "Eliminar")
               /*Spacer(modifier = Modifier.width(4.dp))
               Text("Borrar")*/
            }
         }
      }
   )
}

@Composable
fun MostrarTotalesIngredientes(viewModel: ComponenteDietaViewModel, componenteId: String) {
   val totales by viewModel.calcularTotalesIngredientes(componenteId).observeAsState(initial = emptyMap())

   Column(modifier = Modifier.padding(4.dp)) {
      Text(text = "Valores Totales de Ingredientes", style = MaterialTheme.typography.titleSmall)
      Row(
         modifier = Modifier.fillMaxWidth(),
         verticalAlignment = Alignment.CenterVertically,
         horizontalArrangement = Arrangement.spacedBy(12.dp)
      ) {

         Text(
            text = "HC: ${String.format("%.2f", totales["HC"] ?: 0.0)}",
            style = MaterialTheme.typography.titleSmall // Estilo aplicado
         )
         Text(
            text = "Lip: ${String.format("%.2f", totales["Lip"] ?: 0.0)}",
            style = MaterialTheme.typography.titleSmall // Estilo aplicado
         )
         Text(
            text = "Pro: ${String.format("%.2f", totales["Pro"] ?: 0.0)}",
            style = MaterialTheme.typography.titleSmall // Estilo aplicado
         )
         Text(
            text = "Cal: ${String.format("%.2f", totales["Kcal"] ?: 0.0)}",
            fontWeight = FontWeight.Bold, // Mantener negrita para "Cal"
            style = MaterialTheme.typography.titleSmall // Estilo aplicado
         )
      }
   }
}






















