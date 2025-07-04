package com.yucsan.dietasroomfer


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

import com.yucsan.dietasroomfer.Tema.ThemeViewModel
import com.yucsan.dietasroomfer.componentes.DrawerContent
import com.yucsan.dietasroomfer.componentes.MiTopAppBar
import com.yucsan.dietasroomfer.componentes.NavigationBar
import com.yucsan.dietasroomfer.screens.Formulario
import com.yucsan.dietasroomfer.screens.Ruta


import com.yucsan.dietasroomfer.ui.theme.DietasRoomFerTheme


import kotlinx.coroutines.launch

import com.yucsan.dietasroomfer.modeloRoom.ComponenteDietaViewModel

import com.yucsan.dietasroomfer.modeloRoom.TipoComponente
import com.yucsan.dietasroomfer.screens.MenuInicio
import com.yucsan.dietasroomfer.screens.NuevoListado
import com.yucsan.dietasroomfer.screens2.GestionComponenteScreen
import com.yucsan.dietasroomfer.screens2.SeleccionarIngredientesScreen

class MainActivity : ComponentActivity() {

   val viewModelCD: ComponenteDietaViewModel by viewModels()
   private val themeViewModel: ThemeViewModel by viewModels()

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)

      //prueba1()

      setContent {


         val isDarkMode by themeViewModel.isDarkMode.collectAsStateWithLifecycle()
         val context = LocalContext.current


         // variables Navegación
         val navigationController = rememberNavController()
         val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
         val scope = rememberCoroutineScope()

         // Opciones Formulario
         val opciones = listOf(
            TipoComponente.SIMPLE,
            TipoComponente.PROCESADO,
            TipoComponente.MENU,
            TipoComponente.RECETA,
            TipoComponente.DIETA
         )
         DietasRoomFerTheme(darkTheme = isDarkMode) {  // Aplica el tema dinámico
            ModalNavigationDrawer(
               drawerState = drawerState,
               drawerContent = {
                  DrawerContent { route ->
                     scope.launch { drawerState.close() }
                     navigationController.navigate(route)
                  }
               }
            ) {

               val backStackEntry by navigationController.currentBackStackEntryAsState()
               val currentRoute =
                  backStackEntry?.destination?.route.orEmpty() // Asegura que no sea nulo

               val isBottomBarVisible = remember(currentRoute) {
                  !currentRoute.startsWith("gestion_componente/")
               }


               Scaffold(
                  topBar = {
                     MiTopAppBar(
                        onMenuClick = { scope.launch { drawerState.open() } },
                        themeViewModel = themeViewModel
                     )
                  },
                  bottomBar = {

                     Box(
                        modifier = Modifier.animateContentSize() // Suaviza el cambio de tamaño
                     ) {
                        if (isBottomBarVisible) {
                           NavigationBar(navigationController)
                        }
                     }

                  }
               ) { paddingValues ->
                  NavHost(
                     navController = navigationController,
                     startDestination = Ruta.Pantalla1.ruta,
                     modifier = Modifier.padding(paddingValues)
                  ) {
                     composable(Ruta.Pantalla1.ruta) {
                        MenuInicio(navigationController)
                     }

                     composable(Ruta.Pantalla2.ruta) {
                        //principal.value=true
                        Formulario(
                           navigationController,
                           opciones,
                           viewModelCD,
                           context
                        )
                     }
                     composable(Ruta.Pantalla3.ruta) {
                        NuevoListado(
                           viewModelCD,
                           navigationController,
                           opciones,
                           context
                        )

                     }
                     composable("gestion_componente/{componenteId}") { backStackEntry ->
                        val componenteId = backStackEntry.arguments?.getString("componenteId")
                           ?: return@composable
                        GestionComponenteScreen(viewModelCD, componenteId, navigationController)
                     }
                     composable("seleccionar_ingredientes/{componenteId}") { backStackEntry ->
                        val componenteId = backStackEntry.arguments?.getString("componenteId")
                           ?: return@composable
                        SeleccionarIngredientesScreen(
                           viewModelCD,
                           componenteId,
                           navigationController
                        )
                     }

                  }
               }
            }
         }
      }

   }

}

















