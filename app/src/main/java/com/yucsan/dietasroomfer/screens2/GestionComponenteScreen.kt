package com.yucsan.dietasroomfer.screens2

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.yucsan.dietasroomfer.modeloRoom.ComponenteDieta
import com.yucsan.dietasroomfer.modeloRoom.ComponenteDietaViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GestionComponenteScreen(
   viewModel: ComponenteDietaViewModel,
   componenteId: String,
   navController: NavController,
) {
   val coroutineScope = rememberCoroutineScope()
   val componente by viewModel.getComponenteDietaById(componenteId).observeAsState()
   val ingredientes by viewModel.obtenerComponenteDietaConIngredientes(componenteId)
      .observeAsState()
   val todosLosComponentes by viewModel.componentesDieta.observeAsState(emptyList())

   var mostrarDialogoEliminar by remember { mutableStateOf(false) }

   val ingredientesDisponibles = todosLosComponentes
      .filter { it.id != componenteId }
      .filterNot { ing -> ingredientes?.ingredientes?.any { it.ingrediente.id == ing.id } == true }

   Scaffold(
      topBar = {
         TopAppBar(
            title = { Text(text = "Editar ${componente?.nombre ?: "Cargando..."}") },
            navigationIcon = {
               IconButton(onClick = { navController.popBackStack() }) {
                  Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
               }
            },
            actions = {
               IconButton(onClick = { mostrarDialogoEliminar = true }) {
                  Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = Color.Red)
               }


            }
         )
      }
   ) { paddingValues ->
      Box(
         modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
      ) {
         var nombreComponente by remember { mutableStateOf(componente?.nombre ?: "") }
         val esHabilitado = remember(nombreComponente) {
            nombreComponente.isNotBlank() && nombreComponente != (componente?.nombre ?: "")
         }

         Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            TextField(
               value = nombreComponente,
               onValueChange = { nombreComponente = it },
               label = { Text("Nombre del componente") },
               modifier = Modifier.fillMaxWidth()
            )

            Button(
               onClick = {
                  componente?.let {
                     viewModel.actualizarComponenteDieta(it.copy(nombre = nombreComponente))
                  }
                  navController.popBackStack()
               },
               enabled = esHabilitado,
               modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
               shape = RoundedCornerShape(12.dp),
               colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) {
               Text(text = "Cambiar Nombre")
            }

            Text(
               text = "Ingredientes",
               style = MaterialTheme.typography.titleMedium,
               color = MaterialTheme.colorScheme.primary,
               fontWeight = FontWeight.Bold,
               modifier = Modifier.padding(vertical = 4.dp)
            )

            Box(
               modifier = Modifier
                  .weight(1f)
                  .fillMaxWidth().background(MaterialTheme.colorScheme.background)
            ) {
               LazyColumn {
                  ingredientes?.ingredientes?.forEach { ingrediente ->
                     item {
                        Card(
                           modifier = Modifier
                              .fillMaxWidth()
                              .padding(8.dp),
                           elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                           Row(
                              modifier = Modifier
                                 .fillMaxWidth()
                                 .padding(8.dp),
                              horizontalArrangement = Arrangement.SpaceBetween,
                              verticalAlignment = Alignment.CenterVertically
                           ) {
                              var cantidadTexto by remember { mutableStateOf(ingrediente.relacion.cantidad.toString()) }

                              Column(modifier = Modifier.weight(1f)) {
                                 Text(
                                    text = ingrediente.ingrediente.nombre,
                                    modifier = Modifier
                                       .fillMaxWidth()
                                       .padding(start = 35.dp),  // Hace que el texto ocupe todo el ancho disponible
                                    textAlign = TextAlign.Center, // Centra el texto
                                    fontWeight = FontWeight.Bold, // Hace el texto en negrita
                                    color = MaterialTheme.colorScheme.onBackground // Cambia el color al color primario del esquema de Material
                                 )


                                 OutlinedTextField(
                                    value = cantidadTexto,
                                    onValueChange = { nuevaCantidad ->
                                       // Permite vacío mientras el usuario edita
                                       cantidadTexto = nuevaCantidad

                                       // Solo actualiza en ViewModel si el valor es válido
                                       val cantidad = nuevaCantidad.toDoubleOrNull()
                                       if (cantidad != null) {
                                          viewModel.agregarIngrediente(
                                             ingrediente.relacion.componentePadreId,
                                             ingrediente.relacion.componenteHijoId,
                                             cantidad
                                          )
                                       }
                                    },
                                    label = { Text("Cantidad (g)") },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    modifier = Modifier
                                       .fillMaxWidth(0.6f)  // Esto hace que ocupe un 30% del ancho disponible (un tercio)
                                       .padding(end = 8.dp)  // Puedes agregar padding si lo deseas
                                       .padding(horizontal = 8.dp, vertical = 2.dp),
                                    singleLine = true,
                                    shape = RoundedCornerShape(12.dp),
                                    colors = OutlinedTextFieldDefaults.colors(
                                       focusedBorderColor = MaterialTheme.colorScheme.primary,
                                       unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                       cursorColor = MaterialTheme.colorScheme.primary
                                    )
                                 )
                              }

                              IconButton(
                                 onClick = {
                                    viewModel.eliminarIngrediente(
                                       ingrediente.relacion.componentePadreId,
                                       ingrediente.relacion.componenteHijoId
                                    )
                                 }
                              ) {
                                 Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                              }
                           }
                        }
                     }
                  }
               }

            }

            Button(
               onClick = { navController.navigate("seleccionar_ingredientes/$componenteId") },
               enabled = ingredientesDisponibles.isNotEmpty(),
               modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
               shape = RoundedCornerShape(12.dp)
            ) {
               Text(text = "Agregar Ingrediente")
            }
         }
      }
   }

   if (mostrarDialogoEliminar) {
      AlertDialog(
         onDismissRequest = { mostrarDialogoEliminar = false },
         title = { Text("Confirmar Eliminación") },
         text = { Text("Primero elimina los ingredientes antes de eliminar el componente.") },
         confirmButton = {
            Column {
               var ingredientesEliminados by remember { mutableStateOf(false) }
               var mostrarConfirmacionBorrarAlimento by remember { mutableStateOf(false) }

               // Botón para eliminar solo los ingredientes
               Button(
                  onClick = {
                     componente?.let { comp ->
                        coroutineScope.launch {
                           withContext(Dispatchers.IO) {
                              viewModel.borrarTodasLasRelacionesDeComponente(comp.id)
                              delay(300)
                           }
                           ingredientesEliminados =
                              true // Se activa cuando los ingredientes han sido eliminados
                        }
                     }
                  },
                  enabled = !ingredientesEliminados
               ) {
                  Text("Borra ingredientes")
               }

               Spacer(modifier = Modifier.height(8.dp)) // Espaciado entre botones

               // Botones alineados en una fila
               Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween
               ) {
                  // Botón Cancelar
                  Button(onClick = { mostrarDialogoEliminar = false }) {
                     Text("Cancelar")
                  }

                  // Botón Eliminar Componente (ahora muestra la alerta de confirmación)
                  Button(
                     onClick = { mostrarConfirmacionBorrarAlimento = true },
                     colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                     enabled = ingredientesEliminados
                  ) {
                     Text("Borra Alimento")
                  }
               }

               // Segunda alerta de confirmación antes de borrar el alimento
               if (mostrarConfirmacionBorrarAlimento) {
                  AlertDialog(
                     onDismissRequest = { mostrarConfirmacionBorrarAlimento = false },
                     title = { Text("Confirmación") },
                     text = { Text("¿Está seguro de que desea borrar el alimento?") },
                     confirmButton = {
                        Button(
                           onClick = {
                              componente?.let {
                                 coroutineScope.launch {
                                    viewModel.borrarComponenteconTodo(it) // Borra el componente
                                    mostrarConfirmacionBorrarAlimento = false
                                    mostrarDialogoEliminar = false
                                    navController.popBackStack()
                                 }
                              }
                           }
                        ) {
                           Text("Confirmar")
                        }
                     },
                     dismissButton = {
                        Button(onClick = { mostrarConfirmacionBorrarAlimento = false }) {
                           Text("Cancelar")
                        }
                     }
                  )
               }
            }
         }
      )
   }
}









