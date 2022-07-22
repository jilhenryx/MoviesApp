package com.example.moviesapp.main.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost

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

    @OptIn(ExperimentalAnimationApi::class)
    AnimatedNavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
        enterTransition = {
            slideIntoContainer(towards = AnimatedContentScope.SlideDirection.Left,
                animationSpec = tween(durationMillis = 500))
        },
        exitTransition = {
            scaleOut(targetScale = 0.8f, animationSpec = tween(durationMillis = 500)) +
            fadeOut(animationSpec = tween(durationMillis = 300, delayMillis = 100))
        },
        popEnterTransition = {
            scaleIn(initialScale = 0.8f, animationSpec = tween(durationMillis = 500)) +
            fadeIn(animationSpec = tween(durationMillis = 300, delayMillis = 100))
        },
        popExitTransition = {
            slideOutOfContainer(towards = AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(durationMillis = 1000))
        }
    ) {
        addAuthGraph(navController)
        addMainGraph(navController)
    }
}
