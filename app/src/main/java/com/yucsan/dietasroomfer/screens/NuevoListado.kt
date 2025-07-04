package com.yucsan.dietasroomfer.screens

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add

import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState

import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


import com.yucsan.dietasroomfer.modeloRoom.ComponenteDieta
import com.yucsan.dietasroomfer.modeloRoom.ComponenteDietaViewModel

import com.yucsan.dietasroomfer.modeloRoom.TipoComponente


// RAMA MASTER PROYECTO DIETAS FERNANDO

@Composable
fun NuevoListado(
   viewModel: ComponenteDietaViewModel,
   navController: NavController,
   opciones: List<TipoComponente>,
   context: Context,
) {


   Column {
      Box(modifier = Modifier.fillMaxSize()) { // Usamos un Box para colocar elementos encima
         Column(modifier = Modifier.fillMaxWidth()) {
            ListaComponentesDieta(viewModel, navController)
         }
         // Botón flotante en la parte inferior derecha
         FloatingActionButton(
            onClick = { navController.navigate(Ruta.Pantalla2.ruta) },
            modifier = Modifier
               .align(Alignment.BottomEnd) // Posiciona el botón en la parte inferior derecha
               .padding(16.dp) // Agrega un pequeño margen
         ) {
            Icon(Icons.Default.Add, contentDescription = "Crear Alimento") // Icono del botón
         }
      }

   }

}

@Composable
fun getTipoColor(tipo: TipoComponente): Color {
   return when (tipo) {
      TipoComponente.SIMPLE -> Color(0xFF81C784) // Verde
      TipoComponente.PROCESADO -> Color(0xFFEB9735) // naranja
      TipoComponente.MENU -> Color(0xFFE57373) // Rojo
      TipoComponente.RECETA -> Color(0xFF64B5F6) // Azul
      TipoComponente.DIETA -> Color(0xFFBA68C8) // Morado
   }
}




















