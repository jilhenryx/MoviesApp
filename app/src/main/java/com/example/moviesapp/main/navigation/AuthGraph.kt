@file:OptIn(ExperimentalAnimationApi::class)

package com.example.moviesapp.main.navigation

import android.content.Intent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.*
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import com.example.moviesapp.core.AuthEmailType
import com.example.moviesapp.main.navigation.AuthGraphCallerIds.LOGIN_SCREEN_ID
import com.example.moviesapp.main.navigation.AuthGraphCallerIds.SIGN_UP_SCREEN_ID
import com.example.moviesapp.data.network.authentication.AuthConstants
import com.example.moviesapp.ui.checkemail.CheckEmailScreen
import com.example.moviesapp.ui.forgotpassword.ForgotPasswordScreen
import com.example.moviesapp.ui.login.LoginScreen
import com.example.moviesapp.ui.resetpassword.ResetPasswordScreen
import com.example.moviesapp.ui.signup.SignUpScreen

object AuthGraphCallerIds {
    val LOGIN_SCREEN_ID = AuthScreen.Login.route
    val SIGN_UP_SCREEN_ID = AuthScreen.SignUp.route
}

private sealed class AuthScreen(route: String) : Screen {
    final override val graph = NavGraph.AuthGraph
    override val route: String = "${graph.route}.$route"

    object Login : AuthScreen(route = "login")

    object SignUp : AuthScreen(route = "signup")

    object ForgotPassword : AuthScreen(route = "forgot_password") {
        override val route: String = "${super.route}?email={email}"
        fun createRouteId(email: String): String = "${super.route}?email=$email"
    }

    object CheckEmail : AuthScreen(route = "check_mail") {
        override val route: String =
            "${super.route}?email={email}&link={link}&emailType={emailType}&caller={callerId}"

        fun createRouteId(
            email: String,
            emailType: AuthEmailType,
            callerId: String
        ) = "${super.route}?email=$email&emailType=$emailType&caller=$callerId"

    }

    object ResetPassword : AuthScreen(route = "reset_password") {
        override val route: String = "${super.route}/{resetCode}"
        fun createId(resetCode: String) = "${super.route}/$resetCode"
    }
}

internal fun NavGraphBuilder.addAuthGraph(
    navController: NavHostController,
) {
    navigation(startDestination = AuthScreen.Login.route, route = NavGraph.AuthGraph.route) {
        addLoginScreen(navController)
        addSignUpScreen(navController)
        addForgotPasswordScreen(navController)
        addCheckEmailScreen(navController)
        addResetPasswordScreen(navController)
    }
}

private fun NavGraphBuilder.addLoginScreen(
    navController: NavHostController,
) {
    composable(route = AuthScreen.Login.route) {
        LoginScreen(
            navigateToSignUp = {
                navController.navigate(AuthScreen.SignUp.route)
            },
            navigateToMain = {
                navController.navigate(NavGraph.MainGraph.route){
                    popUpTo(NavGraph.AuthGraph.route){
                        inclusive =  true
                    }
                }
            },
            navigateToForgotPassword = {
                navController.navigate(AuthScreen.ForgotPassword.createRouteId(it))
            },
            navigateToCheckEmail = {
                navController.navigate(
                    AuthScreen.CheckEmail.createRouteId(
                        email = it,
                        emailType = AuthEmailType.VERIFY_EMAIL,
                        callerId = LOGIN_SCREEN_ID
                    )
                )
            }
        )
    }
}

private fun NavGraphBuilder.addSignUpScreen(navController: NavHostController) {
    composable(route = AuthScreen.SignUp.route) {
        SignUpScreen(
            navigateToCheckEmail = {
                navController.navigate(
                    route = AuthScreen.CheckEmail.createRouteId(
                        email = it,
                        emailType = AuthEmailType.VERIFY_EMAIL,
                        callerId = SIGN_UP_SCREEN_ID
                    ),
                    navOptions = navOptions {
                        popUpTo(route = AuthScreen.Login.route)
                    }
                )
            },
            navigateUp = {
                navController.navigateUp()
            }
        )
    }
}

private fun NavGraphBuilder.addForgotPasswordScreen(navController: NavHostController) {
    composable(
        route = AuthScreen.ForgotPassword.route,
        arguments = listOf(
            navArgument("email") {
                defaultValue = ""
            }
        )) { backStackEntry ->
        val email = backStackEntry.arguments?.getString("email") ?: ""
        ForgotPasswordScreen(
            email = email,
            navigateToCheckEmail = {
                navController.navigate(
                    AuthScreen.CheckEmail.createRouteId(
                        email = it,
                        emailType = AuthEmailType.RESET_PASSWORD,
                        callerId = ""
                    )
                )
            },
            navigateUp = { navController.navigateUp() }
        )
    }
}

private fun NavGraphBuilder.addCheckEmailScreen(navController: NavHostController) {
    composable(
        route = AuthScreen.CheckEmail.route,
        arguments = listOf(
            navArgument(name = "email") {
                defaultValue = ""
            },
            navArgument(name = "link") {
                defaultValue = ""
            },
            navArgument(name = "emailType") {
                type = NavType.EnumType(AuthEmailType::class.java)
                defaultValue = AuthEmailType.VERIFY_EMAIL
            },
            navArgument(name = "callerId") {
                defaultValue = ""
            },
        ),
        deepLinks = listOf(
            navDeepLink {
                uriPattern = AuthConstants.VERIFY_EMAIL_DEEP_LINK_URI
                action = Intent.ACTION_VIEW
            },
            navDeepLink {
                uriPattern = AuthConstants.RESET_PASSWORD_DEEP_LINK_URI
                action = Intent.ACTION_VIEW
            }
        )
    ) { backStackEntry ->
        val email = backStackEntry.arguments?.getString("email") ?: ""
        val link = backStackEntry.arguments?.getString("link") ?: ""
        val emailType =
            backStackEntry.arguments?.getSerializable("emailType") as? AuthEmailType
                ?: AuthEmailType.VERIFY_EMAIL
        val callerId = backStackEntry.arguments?.getString("callerId") ?: ""

        CheckEmailScreen(
            email = email,
            deepLink = link,
            emailType = emailType,
            callerId = callerId,
            navigateToLogin = {
                navController.navigate(route = AuthScreen.Login.route) {
                    popUpTo(route = AuthScreen.Login.route) {
                        inclusive = true
                    }
                }
            },
            navigateToResetPassword = {
                navController.navigate(route = AuthScreen.ResetPassword.createId(it)) {
                    popUpTo(AuthScreen.Login.route) {
                        inclusive = true
                    }
                }
            },
            navigateUp = { navController.navigateUp() }
        )
    }
}

private fun NavGraphBuilder.addResetPasswordScreen(navController: NavHostController) {
    composable(route = AuthScreen.ResetPassword.route) { backStackEntry ->
        val resetCode = backStackEntry.arguments?.getString("resetCode") ?: ""
        ResetPasswordScreen(
            resetCode = resetCode,
            navigateToLogin = {
                navController.navigate(route = AuthScreen.Login.route) {
                    popUpTo(route = AuthScreen.ResetPassword.route) {
                        inclusive = true
                    }
                }
            }
        )
    }
}