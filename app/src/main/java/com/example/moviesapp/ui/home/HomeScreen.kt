package com.example.moviesapp.ui.home

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.moviesapp.R
import com.example.moviesapp.ui.composablehelpers.ComposeConstants
import com.example.moviesapp.ui.reusablecomposables.AuthAppBar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(navigateToLogin: () -> Unit) {
    HomeScreen(
        viewModel = hiltViewModel(),
        navigateToLogin = navigateToLogin
    )
}

@Composable
private fun HomeScreen(
    viewModel: HomeViewModel,
    navigateToLogin: () -> Unit
) {
    HomeScreen(
        state = viewModel.state,
        navigateToLogin = navigateToLogin
    )
}

@Composable
private fun HomeScreen(
    modifier: Modifier = Modifier,
    state: HomeStateHandler.StateHolder,
    navigateToLogin: () -> Unit
) {
    var startAnim by remember { mutableStateOf(true) }
    var textInitialAlpha by remember { mutableStateOf(0f) }
    var steamInitialAlpha by remember { mutableStateOf(1f) }

    val framesAnimatable = remember {
        Animatable(
            initialValue = 0,
            typeConverter = Int.VectorConverter
        )
    }

    val textAnimatedAlpha by animateFloatAsState(
        targetValue = textInitialAlpha,
        animationSpec = tween(durationMillis = 1600),
    )

    val steamAnimatedAlpha by animateFloatAsState(
        targetValue = steamInitialAlpha,
        animationSpec = tween(durationMillis = 1000, easing = LinearEasing),
    )

    LaunchedEffect(startAnim) {
        delay(500)
        if (startAnim) {
            framesAnimatable.animateTo(
                targetValue = state.frameLength,
                animationSpec = tween(1300, easing = LinearEasing)
            )
        }
        if (!framesAnimatable.isRunning) {
            textInitialAlpha = 1f
            steamInitialAlpha = 0f

            delay(1000)
            framesAnimatable.snapTo(0)
            startAnim = false
        }
    }


    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            AuthAppBar(modifier = Modifier, showUpIndicator = false)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Box(
                modifier = Modifier.clickable {
                    if (!framesAnimatable.isRunning) {
                        steamInitialAlpha = 1f
                        startAnim = true
                    }
                }
            ) {
                Image(
                    painter = painterResource(R.drawable.cauldron),
                    contentDescription = null
                )

                Image(
                    modifier = Modifier.alpha(steamAnimatedAlpha),
                    painter = painterResource(state.frames[framesAnimatable.value]),
                    contentDescription = null
                )
            }

            Spacer(modifier = Modifier.height(ComposeConstants.MEDIUM_SPACING))

            //Intentional call of data layer here
            Text(
                modifier = Modifier
                    .alpha(textAnimatedAlpha)
                    .clickable {
                        Firebase.auth.signOut()
                        navigateToLogin()
                    },
                text = "Something's cooking......."
            )
        }
    }
}
