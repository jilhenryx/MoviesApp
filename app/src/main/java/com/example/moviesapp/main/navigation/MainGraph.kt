@file:OptIn(ExperimentalAnimationApi::class)

package com.example.moviesapp.main.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.example.moviesapp.ui.home.HomeScreen
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation


private sealed class MainScreen(route: String) : Screen {
    final override val graph = NavGraph.MainGraph
    override val route = "${graph.route}.$route"

    object Home : MainScreen(route = "main")
}

@OptIn(ExperimentalAnimationApi::class)
internal fun NavGraphBuilder.addMainGraph(
    navController: NavHostController,
) {
    navigation(startDestination = MainScreen.Home.route, route = NavGraph.MainGraph.route) {
        addHomeScreen(navController)
    }
}

@OptIn(ExperimentalAnimationApi::class)
private fun NavGraphBuilder.addHomeScreen(navController: NavHostController) {
    composable(route = MainScreen.Home.route) {
        HomeScreen(
            navigateToLogin = {
                navController.navigate(NavGraph.AuthGraph.route) {
                    popUpTo(NavGraph.MainGraph.route) { inclusive = true }
                }
            }
        )
    }
}