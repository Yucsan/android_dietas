package com.yucsan.dietasroomfer.ui.theme


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme

import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.yucsan.dietasroomfer.R

// 🌿 Tema Verde


// 🎨 Nueva gama de colores
val DarkPrimary = Color(0xFF442673)
val DarkSecondary = Color(0xFF9E78DC)
val DarkTertiary = Color(0xFF4895EF)

val LightPrimary = Color(0xFF7B50BF)
val LightSecondary = Color(0xFFBB9AF2)
val LightTertiary = Color(0xFFFAFAF0)

val DarkColorScheme = darkColorScheme(
  primary = DarkPrimary,
  secondary = DarkSecondary,
  tertiary = DarkTertiary,
  background = Color(0xFF121212),
  surface = Color(0xFF1E1E1E),
  onPrimary = Color.White,
  onSecondary = Color(0xFFFFFFFF)
)

val LightColorScheme = lightColorScheme(
  primary = LightPrimary,
  secondary = LightSecondary,
  tertiary = LightTertiary,
  background = Color(0xFFFAFAF0),
  surface = Color(0xFFFFE089),
  onPrimary = Color.Black,
  onSecondary = Color(0xFFFFFFFF)
)

@Composable
fun DietasRoomFerTheme(
  darkTheme: Boolean,
  content: @Composable () -> Unit
) {
  val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme


  MaterialTheme(
    colorScheme = colorScheme,
    typography = CustomTypography,
    content = content
  )

}



