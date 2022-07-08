package com.example.moviesapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = darkColors(
    primary = Grey800,
    primaryVariant = Grey700,
    secondary = BlueGrey700,
    secondaryVariant = BlueGrey600,
    onPrimary = Color.White,
    onSecondary = Color.White,
)

private val LightColorPalette = lightColors(
    primary = Indigo800,
    primaryVariant = Indigo600,
    secondary = Blue700,
    secondaryVariant = Blue600,
    onSecondary = Color.White,
)

@Composable
fun MoviesAppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) DarkColorPalette else LightColorPalette
    val textColor = if (darkTheme) Color.White else Color.Black

    MaterialTheme(
        colors = colors,
        typography = ThemedTypography(textColor),
        shapes = Shapes,
        content = content
    )
}