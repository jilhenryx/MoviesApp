package com.example.moviesapp.ui.composables

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun App(isDarkMode: Boolean) {
    WelcomeScreen(
        isDarkMode = isDarkMode
    )
}

@Composable
fun SystemUiTheme() {
    // Remember a SystemUiController
    val systemUiController = rememberSystemUiController()
    val darkIcons = MaterialTheme.colors.isLight
    SideEffect {
        // Update status bar color to be transparent, and use
        // dark icons if we're in light theme
        systemUiController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = darkIcons,
        )

//            windowInsetsController.isAppearanceLightNavigationBars = darkIcons
    }
}