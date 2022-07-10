package com.example.moviesapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Indigo200,
    primaryVariant = Indigo100,
    secondary = Blue200,
    secondaryVariant = Blue100,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onSurface = Grey100,
    onBackground = Grey100,
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

    MaterialTheme(
        colors = colors,
        typography = ThemedTypography.invoke(colors.onSurface),
        shapes = Shapes,
        content = content
    )
}