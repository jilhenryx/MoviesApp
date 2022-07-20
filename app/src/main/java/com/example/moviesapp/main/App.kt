package com.example.moviesapp.main

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.moviesapp.main.navigation.AppNavigation
import com.example.moviesapp.main.navigation.NavGraph

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
        AppNavigation(
            modifier = Modifier.navigationBarsPadding(),
            navController = navController,
            startDestination = NavGraph.AuthGraph.route)
    }
}