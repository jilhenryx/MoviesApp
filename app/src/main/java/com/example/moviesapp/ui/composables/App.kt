package com.example.moviesapp.ui.composables

import android.content.Intent
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.moviesapp.R
import com.example.moviesapp.ui.composables.reusablecomposables.AppScaffold
import com.example.moviesapp.ui.constants.*
import com.example.moviesapp.ui.navigation.LoginFlow
import com.example.moviesapp.ui.navigation.loginGraph
import com.example.moviesapp.viewmodels.AuthViewModel

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