package com.example.moviesapp.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.moviesapp.R
import com.example.moviesapp.ui.composablehelpers.ComposeConstants.MEDIUM_SPACING
import com.example.moviesapp.ui.composablehelpers.ComposeConstants.SMALL_SPACING
import com.example.moviesapp.ui.reusablecomposables.AppButton
import com.example.moviesapp.ui.reusablecomposables.AppButtonType
import com.example.moviesapp.ui.reusablecomposables.AppDefaultHeader

@Composable
fun WelcomeScreenBackground(
    modifier: Modifier = Modifier,
    isDarkMode: Boolean
) {
    val imageBackground =
        if (isDarkMode) R.drawable.welcome_screen_dark_ else R.drawable.welcome_screen_light_
    Image(
        painter = painterResource(id = imageBackground),
        contentDescription = null,
        modifier = modifier.fillMaxSize(),
        contentScale = ContentScale.FillBounds
    )
}

@Composable
fun WelcomeScreenContent(
    modifier: Modifier = Modifier,
    onButtonClick: () -> Unit
) {
    Column(
        modifier = modifier,
    ) {
        AppDefaultHeader(
            title = stringResource(R.string.welcome_header),
            subtitle = stringResource(R.string.welcome_message)
        )
        Spacer(modifier = Modifier.height(MEDIUM_SPACING))
        AppButton(
            type = AppButtonType.FILLED,
            title = stringResource(R.string.welcome_button_text).uppercase(),
            trailingIcon = R.drawable.arrow_forward_ios,
            iconTint = MaterialTheme.colors.background,
            onClick = onButtonClick
        )
        Spacer(modifier = Modifier.height(SMALL_SPACING))
    }
}

@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier,
    isDarkMode: Boolean,
    onButtonClick: () -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        WelcomeScreenBackground(isDarkMode = isDarkMode)
        WelcomeScreenContent(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(horizontal = SMALL_SPACING),
            onButtonClick = onButtonClick
        )
    }

}