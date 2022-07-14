package com.example.moviesapp.ui.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.compose.rememberNavController
import com.example.moviesapp.ui.navigation.LoginFlow

@Composable
fun App(isDarkMode: Boolean, isFirstLaunch: Boolean) {
    val navController = rememberNavController()
    var isLaunching by rememberSaveable { mutableStateOf(isFirstLaunch) }

    if (isLaunching) {
        WelcomeScreen(
            isDarkMode = isDarkMode,
            onButtonClick = {
                isLaunching = false
            })
    } else {
        LoginFlow(navController)
    }
}

//@Composable
//fun SystemUiTheme() {
//    // Remember a SystemUiController
//    val systemUiController = rememberSystemUiController()
//    val darkIcons = MaterialTheme.colors.isLight
//    SideEffect {
//        // Update status bar color to be transparent, and use
//        // dark icons if we're in light theme
//        systemUiController.setStatusBarColor(
//            color = Color.Transparent,
//            darkIcons = darkIcons,
//        )
//    }
//}