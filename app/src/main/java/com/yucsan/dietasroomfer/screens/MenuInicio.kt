package com.yucsan.dietasroomfer.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.yucsan.dietasroomfer.R

@Composable
fun MenuInicio(navController: NavController) {


   Box(
      modifier = Modifier.fillMaxSize()
   ) {
      // Imagen de fondo

      Image(
         painter = painterResource(id = R.drawable.fondoclaro), // Cambia esto por tu imagen
         contentDescription = null,
         modifier = Modifier.fillMaxSize(),
         contentScale = ContentScale.Crop
      )

      // Contenedor de los botones
      Column(
         modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), // Margen alrededor
         verticalArrangement = Arrangement.Center,
         horizontalAlignment = Alignment.CenterHorizontally
      ) {
         ImageButton(
            imageResId = R.drawable.crear, // Imagen para Agregar Alimento
            onClick = { navController.navigate(Ruta.Pantalla2.ruta) }
         )


         Spacer(modifier = Modifier.height(26.dp)) // Espacio entre botones

         ImageButton(
            imageResId = R.drawable.listado, // Imagen para Listado de Alimentos
            onClick = { navController.navigate(Ruta.Pantalla3.ruta) }
         )
      }
   }
}

@Composable
fun ImageButton(imageResId: Int, onClick: () -> Unit) {
   Box(
      modifier = Modifier
         .size(200.dp) // Tamaño del botón imagen
         .clip(RoundedCornerShape(16.dp)) // Bordes redondeados
         .clickable { onClick() }
         .background(Color.White, shape = RoundedCornerShape(16.dp)), // Fondo blanco opcional
      contentAlignment = Alignment.Center
   ) {
      Image(
         painter = painterResource(id = imageResId),
         contentDescription = null,
         modifier = Modifier.fillMaxSize(),
         contentScale = ContentScale.Crop
      )
   }
}
