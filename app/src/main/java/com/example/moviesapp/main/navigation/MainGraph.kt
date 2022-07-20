package com.example.moviesapp.main.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation


private sealed class MainScreen(route: String) : Screen {
    final override val graph = NavGraph.MainGraph
    override val route = "${graph.route}.$route"

    object Main : MainScreen(route = "main")
}

internal fun NavGraphBuilder.addMainGraph(
    navController: NavHostController,
) {
    navigation(startDestination = MainScreen.Main.route, route = NavGraph.MainGraph.route) {
        addMainScreen(navController)
    }
}

private fun NavGraphBuilder.addMainScreen(navController: NavHostController) {
    composable(route = MainScreen.Main.route) {

    }
}