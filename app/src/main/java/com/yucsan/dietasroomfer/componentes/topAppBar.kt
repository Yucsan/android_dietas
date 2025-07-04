package com.yucsan.dietasroomfer.componentes


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.rounded.DarkMode
import androidx.compose.material.icons.rounded.LightMode
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.yucsan.dietasroomfer.R
import com.yucsan.dietasroomfer.Tema.ThemeViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MiTopAppBar(onMenuClick: () -> Unit,  themeViewModel: ThemeViewModel) {
    val isDarkMode by themeViewModel.isDarkMode.collectAsState()
    val popins = FontFamily(
        Font(R.font.poppins_regular, FontWeight.Normal)
    )
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = onMenuClick) {
                Icon(
                    imageVector = Icons.Rounded.Menu,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground // Color blanco para el icono
                )
            }
        },
        title = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Proyecto Dieta",
                    color = MaterialTheme.colorScheme.onBackground, // Color del texto
                    fontSize = 20.sp, // Tamaño de fuente
                    fontWeight = FontWeight.Bold, // Negrita
                    fontFamily = popins // Cambia la tipografía
                )
            }
        },
        actions = {
            IconButton(onClick = { themeViewModel.toggleTheme() }) {
                Icon(
                    imageVector = if (isDarkMode) Icons.Rounded.DarkMode else Icons.Rounded.LightMode,
                    contentDescription = "Toggle Theme"
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent, // Fondo transparente
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}


