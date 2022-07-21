package com.example.moviesapp.main.navigation

import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.moviesapp.ui.home.HomeScreen


private sealed class MainScreen(route: String) : Screen {
    final override val graph = NavGraph.MainGraph
    override val route = "${graph.route}.$route"

    object Home : MainScreen(route = "main")
}

internal fun NavGraphBuilder.addMainGraph(
    navController: NavHostController,
) {
    navigation(startDestination = MainScreen.Home.route, route = NavGraph.MainGraph.route) {
        addMainScreen(navController)
    }
}

private fun NavGraphBuilder.addMainScreen(navController: NavHostController) {
    composable(route = MainScreen.Home.route) {
        HomeScreen()
    }
}