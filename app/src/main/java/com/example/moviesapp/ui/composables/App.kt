package com.example.moviesapp.ui.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.moviesapp.R
import com.example.moviesapp.ui.composables.reusablecomposables.AppScaffold
import com.example.moviesapp.ui.constants.*

fun NavGraphBuilder.loginGraph(
    navController: NavController,
    setIsNavHomeIndicator: (isHomeDest: Boolean) -> Unit
) {
    navigation(
        startDestination = ROUTE_LOGIN_SCREEN,
        route = ROUTE_LOGIN_GRAPH,
    ) {
        composable(route = ROUTE_LOGIN_SCREEN) {
            LoginScreen(
                onSignUpClick = {
                    navController.navigate(route = ROUTE_SIGN_UP_SCREEN)
                },
                onForgotPasswordClick = { email ->
                    navController.navigate(route = "$ROUTE_FORGOT_PASSWORD_SCREEN/$email")
                }
            )
            setIsNavHomeIndicator(true)
        }
        composable(route = ROUTE_SIGN_UP_SCREEN) {
            SignUpScreen(
                onLoginClick = {
                    navController.navigate(route = ROUTE_LOGIN_SCREEN) {
                        popUpTo(route = ROUTE_LOGIN_SCREEN) {
                            inclusive = true
                        }
                    }
                },
                onSignUp = { email ->
                    val titleId = R.string.confirm_email_header_text
                    val subtitleId = R.string.confirm_email_header_subtitle_text
                    navController.navigate(
                        route = "$ROUTE_CHECK_EMAIL_SCREEN/$email?titleStringId=$titleId&subtitleStringId=$subtitleId"
                    )
                }
            )
            setIsNavHomeIndicator(false)
        }
        composable(route = "$ROUTE_FORGOT_PASSWORD_SCREEN/{email}") { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            ForgotPasswordScreen(
                email,
                onContinueClick = { emailAddress ->
                    navController.navigate(route = "$ROUTE_CHECK_EMAIL_SCREEN/$emailAddress") {
                        popUpTo(route = ROUTE_LOGIN_SCREEN)
                    }
                }
            )
            setIsNavHomeIndicator(false)
        }
        composable(
            route = "$ROUTE_CHECK_EMAIL_SCREEN/{email}?titleStringId={titleStringId}&subtitleStringId={subtitleStringId}",
            arguments = listOf(
                navArgument(name = "titleStringId") {
                    type = NavType.ReferenceType
                    defaultValue = R.string.check_email_header_text
                },
                navArgument(name = "subtitleStringId") {
                    type = NavType.ReferenceType
                    defaultValue = R.string.check_email_header_subtitle_default_text
                })
        ) { backStackEntry ->
            val titleId = backStackEntry.arguments?.getInt("titleStringId")
            val subtitleId = backStackEntry.arguments?.getInt("subtitleStringId")
            val email = backStackEntry.arguments?.getString("email") ?: ""

            CheckEmailScreen(email = email, titleStringId = titleId, subtitleStringId = subtitleId)
            setIsNavHomeIndicator(false)
        }
        composable(route = ROUTE_RESET_PASSWORD_SCREEN) {
            ResetPasswordScreen {
                navController.navigate(route = ROUTE_LOGIN_SCREEN) {
                    popUpTo(route = ROUTE_LOGIN_SCREEN) {
                        inclusive = true
                    }
                }
            }
            setIsNavHomeIndicator(false)
        }
    }
}

@Composable
fun LoginFlow(navController: NavHostController) {
    var isNavDestinationHome by rememberSaveable { mutableStateOf(true) }

    AppScaffold(
        isNavHome = isNavDestinationHome,
        onUpButtonClick = { navController.popBackStack() }
    ) {
        NavHost(
            navController = navController,
            startDestination = ROUTE_LOGIN_GRAPH
        ) {
            loginGraph(navController,
                setIsNavHomeIndicator = {
                    isNavDestinationHome = it
                })
        }
    }
}


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