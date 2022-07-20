package com.example.moviesapp.main.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

interface Screen {
    val graph: NavGraph
    val route: String
}

sealed class NavGraph(val route: String) {
    object AuthGraph : NavGraph("auth")
    object MainGraph : NavGraph("main")
}

@Composable
fun AppNavigation(modifier: Modifier, navController: NavHostController, startDestination: String) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        addAuthGraph(navController)
        addMainGraph(navController)
    }
}
