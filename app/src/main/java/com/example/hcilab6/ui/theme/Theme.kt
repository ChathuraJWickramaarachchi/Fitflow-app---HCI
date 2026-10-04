package com.example.hcilab6.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Teal = Color(0xFF00B8A9)
val Orange = Color(0xFFFF7A45)
val Navy = Color(0xFF1B2A49)

private val LightColors = lightColorScheme(
    primary = Teal, onPrimary = Color.White,
    secondary = Orange, onSecondary = Color.White,
    background = Color(0xFFF7F9FB), onBackground = Navy,
    surface = Color.White, onSurface = Navy
)

private val DarkColors = darkColorScheme(
    primary = Teal, onPrimary = Color.Black,
    secondary = Orange, onSecondary = Color.Black,
    background = Color(0xFF101828), onBackground = Color(0xFFF2F4F7),
    surface = Color(0xFF1D2939), onSurface = Color(0xFFF2F4F7)
)

@Composable
fun FitFlowTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = if (darkTheme) DarkColors else LightColors, content = content)
}