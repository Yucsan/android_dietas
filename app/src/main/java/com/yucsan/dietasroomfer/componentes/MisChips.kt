package com.yucsan.dietasroomfer.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.yucsan.dietasroomfer.modeloRoom.TipoComponente
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material3.MaterialTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MisChips(
   opciones: List<TipoComponente>,
   seleccion: MutableState<TipoComponente>,
   esSimple: MutableState<Boolean>
) {
   Column(
      modifier = Modifier
         .fillMaxWidth()
         .padding(3.dp),

      verticalArrangement = Arrangement.spacedBy(4.dp),
      horizontalAlignment = Alignment.CenterHorizontally
   ) {

      FlowRow(
         modifier = Modifier.fillMaxWidth(),
         horizontalArrangement = Arrangement.spacedBy(10.dp),
         verticalArrangement = Arrangement.spacedBy(4.dp)
      ) {
         opciones.forEach { opcion ->
            FilterChip(
               selected = seleccion.value == opcion,
               onClick = {
                  seleccion.value = opcion
                  esSimple.value = opcion == TipoComponente.SIMPLE
               },
               label = { Text(text = opcion.name) },
               colors = FilterChipDefaults.filterChipColors(
                  selectedContainerColor = MaterialTheme.colorScheme.primary,
               )
            )
         }
      }
   }
}
