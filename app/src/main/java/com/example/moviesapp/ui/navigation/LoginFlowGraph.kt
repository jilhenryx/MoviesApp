package com.example.moviesapp.ui.navigation

import android.content.Intent
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.moviesapp.R
import com.example.moviesapp.ui.composables.*
import com.example.moviesapp.ui.composables.reusablecomposables.AppScaffold
import com.example.moviesapp.ui.constants.*
import com.example.moviesapp.viewmodels.AuthViewModel

internal fun NavGraphBuilder.loginGraph(
    navController: NavController,
    setIsNavHomeIndicator: (isHomeDest: Boolean) -> Unit
) {
    navigation(
        startDestination = ROUTE_LOGIN_SCREEN,
        route = ROUTE_LOGIN_GRAPH,
    ) {
        /*
        * LOGIN
        */
        composable(route = ROUTE_LOGIN_SCREEN) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(ROUTE_LOGIN_GRAPH)
            }
            val parentViewModel = hiltViewModel<AuthViewModel>(parentEntry)
            LoginScreen(
                parentViewModel,
                onSignUpClick = {
                    navController.navigate(route = ROUTE_SIGN_UP_SCREEN)
                },
                onForgotPasswordClick = { email ->
                    navController.navigate(route = "$ROUTE_FORGOT_PASSWORD_SCREEN/$email")
                },
                navigateToMain = {
                    //navController.navigate(route = ROUTE_MAIN_SCREEN)
                },
                navigateToCheckEmail = { email ->
                    val titleId = R.string.yet_to_confirm_email_header_text
                    val subtitleId = R.string.yet_to_confirm_email_header_subtitle_text
                    navController.navigate(
                        route =
                        "$ROUTE_CHECK_EMAIL_SCREEN/$email?checkType=${CheckEmailType.VERIFY_EMAIL}&titleStringId=$titleId&subtitleStringId=$subtitleId"
                    )
                }
            )
            setIsNavHomeIndicator(true)
        }

        /*
        * SIGNUP
        */
        composable(route = ROUTE_SIGN_UP_SCREEN) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(ROUTE_LOGIN_GRAPH)
            }
            val parentViewModel = hiltViewModel<AuthViewModel>(parentEntry)
            SignUpScreen(
                viewModel = parentViewModel,
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
                        route =
                        "$ROUTE_CHECK_EMAIL_SCREEN/$email?checkType=${CheckEmailType.VERIFY_EMAIL}&titleStringId=$titleId&subtitleStringId=$subtitleId",
                        navOptions = navOptions {
                            popUpTo(route = ROUTE_LOGIN_SCREEN)
                        }
                    )
                }
            )
            setIsNavHomeIndicator(false)
        }

        /*
        * FORGOT PASSWORD
         */
        composable(route = "$ROUTE_FORGOT_PASSWORD_SCREEN/{email}") { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(ROUTE_LOGIN_GRAPH)
            }
            val parentViewModel = hiltViewModel<AuthViewModel>(parentEntry)
            val email = backStackEntry.arguments?.getString("email") ?: ""
            ForgotPasswordScreen(
                parentViewModel,
                email,
                navigate = { emailAddress ->
                    navController.navigate(
                        route =
                        "$ROUTE_CHECK_EMAIL_SCREEN/$emailAddress?checkType=${CheckEmailType.RESET_PASSWORD}"
                    ) {
                        popUpTo(route = ROUTE_LOGIN_SCREEN)
                    }
                }
            )
            setIsNavHomeIndicator(false)
        }

        /*
        * CHECK EMAIL
         */
        composable(
            route =
            "$ROUTE_CHECK_EMAIL_SCREEN/{email}?checkType={checkType}&titleStringId={titleStringId}&subtitleStringId={subtitleStringId}",
            arguments = listOf(
                navArgument(name = "checkType") {
                    type = NavType.EnumType(CheckEmailType::class.java)
                    defaultValue = CheckEmailType.VERIFY_EMAIL
                },
                navArgument(name = "titleStringId") {
                    type = NavType.ReferenceType
                    defaultValue = R.string.check_email_header_text
                },
                navArgument(name = "subtitleStringId") {
                    type = NavType.ReferenceType
                    defaultValue = R.string.check_email_header_subtitle_default_text
                }),
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "https://moviesappng.page.link/verifyemail"
                    action = Intent.ACTION_VIEW
                }
            )
        ) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(ROUTE_LOGIN_GRAPH)
            }
            val parentViewModel = hiltViewModel<AuthViewModel>(parentEntry)
            val checkEmailType =
                backStackEntry.arguments?.getSerializable("checkType") as? CheckEmailType
                    ?: CheckEmailType.NONE
            val titleId = backStackEntry.arguments?.getInt("titleStringId")
            val subtitleId = backStackEntry.arguments?.getInt("subtitleStringId")
            val email = backStackEntry.arguments?.getString("email") ?: ""

            CheckEmailScreen(
                parentViewModel,
                checkEmailType = checkEmailType,
                email = email,
                titleStringId = titleId,
                subtitleStringId = subtitleId
            )
            setIsNavHomeIndicator(false)
        }

        /*
        * REST PASSWORD
         */
        composable(route = ROUTE_RESET_PASSWORD_SCREEN) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(ROUTE_LOGIN_GRAPH)
            }
            val parentViewModel = hiltViewModel<AuthViewModel>(parentEntry)
            ResetPasswordScreen(
                parentViewModel,
                navigateToLogin = {
                    navController.navigate(route = ROUTE_LOGIN_SCREEN) {
                        popUpTo(route = ROUTE_LOGIN_SCREEN) {
                            inclusive = true
                        }
                    }
                })
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