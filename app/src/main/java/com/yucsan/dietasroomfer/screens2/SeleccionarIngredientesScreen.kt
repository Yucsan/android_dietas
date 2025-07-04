package com.yucsan.dietasroomfer.screens2

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.yucsan.dietasroomfer.modeloRoom.ComponenteDietaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeleccionarIngredientesScreen(
   viewModel: ComponenteDietaViewModel,
   componenteId: String,
   navController: NavController
) {
   val ingredientesAgregados by viewModel.obtenerIngredientesComponente(componenteId)
      .observeAsState(emptyList())

   val ingredientesDisponibles by viewModel.componentesDieta.observeAsState(emptyList())
      .let { state ->
         derivedStateOf {
            state.value.filter { ing ->
               ing.id != componenteId && ingredientesAgregados.none { it.ingrediente.id == ing.id }
            }
         }
      }

   // Estado mutable para manejar selección y cantidades
   var ingredientesSeleccionados by remember {
      mutableStateOf(ingredientesAgregados.associate { it.ingrediente.id to it.relacion.cantidad }.toMutableMap())
   }

   Scaffold(
      topBar = {
         TopAppBar(
            title = { Text("Seleccionar Ingredientes") },
            navigationIcon = {
               IconButton(onClick = { navController.popBackStack() }) {
                  Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
               }
            }
         )
      }
   ) { paddingValues ->
      Column(
         modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
      ) {
         LazyColumn(modifier = Modifier.weight(1f)) {
            items(ingredientesDisponibles) { ingrediente ->
               Card(
                  modifier = Modifier
                     .fillMaxWidth()
                     .padding(4.dp),
                  elevation = CardDefaults.cardElevation(2.dp)
               ) {
                  Row(
                     modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                     horizontalArrangement = Arrangement.SpaceBetween,
                     verticalAlignment = Alignment.CenterVertically
                  ) {
                     Checkbox(
                        checked = ingrediente.id in ingredientesSeleccionados.keys,
                        onCheckedChange = { isChecked ->
                           ingredientesSeleccionados = ingredientesSeleccionados.toMutableMap().apply {
                              if (isChecked) put(ingrediente.id, 0.0) else remove(ingrediente.id)
                           }
                        }
                     )

                     Text(
                        text = ingrediente.nombre,
                        modifier = Modifier.weight(1f)
                     )

                     OutlinedTextField(
                        value = ingredientesSeleccionados[ingrediente.id]?.takeIf { it > 0 }?.toString() ?: "",
                        onValueChange = { cantidad ->
                           ingredientesSeleccionados = ingredientesSeleccionados.toMutableMap().apply {
                              put(ingrediente.id, cantidad.toDoubleOrNull() ?: 0.0)
                           }
                        },
                        label = { Text("g") },
                        modifier = Modifier.width(90.dp),
                        keyboardOptions = KeyboardOptions.Default.copy(
                           keyboardType = KeyboardType.Number
                        ),
                        enabled = ingrediente.id in ingredientesSeleccionados.keys,
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                           focusedBorderColor = MaterialTheme.colorScheme.primary,
                           unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                           cursorColor = MaterialTheme.colorScheme.primary
                        )
                     )
                  }
               }
            }
         }

         Button(
            onClick = {
               ingredientesSeleccionados.forEach { (id, cantidad) ->
                  if (cantidad > 0) {
                     viewModel.agregarIngrediente(componenteId, id, cantidad)
                  }
               }
               navController.popBackStack()
            },
            enabled = ingredientesSeleccionados.values.any { it > 0 },
            modifier = Modifier
               .fillMaxWidth()
               .padding(8.dp)
         ) {
            Text("Confirmar Selección")
         }
      }
   }
}




