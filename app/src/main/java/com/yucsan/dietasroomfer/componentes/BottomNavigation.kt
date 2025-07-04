package com.yucsan.dietasroomfer.componentes

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AppsOutage
import androidx.compose.material.icons.filled.Castle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.yucsan.dietasroomfer.R
import com.yucsan.dietasroomfer.screens.Ruta


@Composable
fun NavigationBar(
   navController: NavHostController,
) {

   //val currentRoute by navController.currentBackStackEntryAsState().map { it?.destination?.route } // Extraemos la ruta actual como un String */
   val currentRoute = navController.currentBackStackEntryAsState()?.value?.destination?.route

   NavigationBar(
      containerColor = MaterialTheme.colorScheme.primary,
      windowInsets = NavigationBarDefaults.windowInsets
   ) {
      NavigationBarItem(
         selected = currentRoute == Ruta.Pantalla1.ruta, // Comparamos con la ruta de Pantalla1
         onClick = {
            if (currentRoute != Ruta.Pantalla1.ruta) { // Evitamos navegar si ya estamos en la pantalla
               navController.navigate(Ruta.Pantalla1.ruta)
               /* { popUpTo(navController.graph.startDestinationId)
                   { saveState = true }
                   launchSingleTop = true
                   restoreState = true } */
            }
         },
         icon = {
            Image(
               painter = painterResource(id = R.drawable.inicio), // Reemplaza con tu imagen en drawable
               modifier = Modifier.size(45.dp),
               contentDescription = "Inicio"
            )
         },
         label = { Text(text = "Inicio", color = MaterialTheme.colorScheme.onSecondary) }
      )

      NavigationBarItem(
         selected = currentRoute == Ruta.Pantalla2.ruta, // Comparamos con la ruta de Pantalla2
         onClick = {
            if (currentRoute != Ruta.Pantalla2.ruta) {
               navController.navigate(Ruta.Pantalla2.ruta)
            }
         },
         icon = {
            Image(
               painter = painterResource(id = R.drawable.creaicono), // Reemplaza con tu imagen en drawable
               modifier = Modifier.size(45.dp),
               contentDescription = "Inicio"
            )
         },
         label = { Text(text = "Crear Alimento", color = MaterialTheme.colorScheme.onSecondary) }
      )
      NavigationBarItem(
         selected = currentRoute == Ruta.Pantalla3.ruta, // Comparamos con la ruta de Pantalla2
         onClick = {
            if (currentRoute != Ruta.Pantalla3.ruta) {
               navController.navigate(Ruta.Pantalla3.ruta)
            }
         },
         icon = { Image(
            painter = painterResource(id = R.drawable.queso), // Reemplaza con tu imagen en drawable
            modifier = Modifier.size(45.dp),
            contentDescription = "Inicio"
         ) },

         label = { Text(text = "Listado") }
      )


   }
}


