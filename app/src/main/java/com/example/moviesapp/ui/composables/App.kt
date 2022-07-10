package com.example.moviesapp.ui.composables

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.moviesapp.ui.constants.*
import com.google.accompanist.systemuicontroller.rememberSystemUiController

fun NavGraphBuilder.loginGraph(navController: NavController) {
    navigation(
        startDestination = ROUTE_LOGIN_SCREEN,
        route = ROUTE_LOGIN_GRAPH,
    ) {
        composable(route = ROUTE_LOGIN_SCREEN) {
            LoginScreen(navController)
        }
        composable(route = ROUTE_SIGN_UP_SCREEN) {
            SignUpScreen(navController)
        }
        composable(route = "$ROUTE_CONFIRM_EMAIL_SCREEN/{email}") { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            ConfirmEmailScreen(navController, email)
        }
        composable(route = "$ROUTE_FORGOT_PASSWORD_SCREEN/{email}") { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            ForgotPasswordScreen(navController, email)
        }
        composable(route = "$ROUTE_CHECK_EMAIL_SCREEN/{email}") { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            CheckEmailScreen(navController, email)
        }
        composable(route = ROUTE_RESET_PASSWORD_SCREEN) { ResetPasswordScreen(navController) }
    }
}

@Composable
fun App(isDarkMode: Boolean, isFirstLaunch: Boolean) {
    val navController = rememberNavController()
    /*TODO : Change the start destination to toggle Welcome and Main Screens*/
    val startDestination = if (isFirstLaunch) ROUTE_WELCOME_SCREEN else ROUTE_LOGIN_GRAPH

    NavHost(navController = navController, startDestination = ROUTE_WELCOME_SCREEN) {
        composable(route = ROUTE_WELCOME_SCREEN) {
            WelcomeScreen(isDarkMode = isDarkMode, navController = navController)
        }
        loginGraph(navController)
    }
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
    }
}