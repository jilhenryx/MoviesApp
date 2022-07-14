package com.example.moviesapp.ui.composables

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.moviesapp.R
import com.example.moviesapp.ui.composables.reusablecomposables.AppButton
import com.example.moviesapp.ui.composables.reusablecomposables.AppButtonType
import com.example.moviesapp.ui.composables.reusablecomposables.AppDefaultHeader
import com.example.moviesapp.ui.constants.MEDIUM_SPACING
import com.example.moviesapp.ui.constants.SMALL_SPACING
import com.example.moviesapp.ui.theme.MoviesAppTheme

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
        verticalArrangement = Arrangement.spacedBy(MEDIUM_SPACING)
    ) {
        AppDefaultHeader(
            title = stringResource(R.string.welcome_header),
            subtitle = stringResource(R.string.welcome_message)
        )
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

@Preview(
    name = "WelcomeScreen",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showSystemUi = true,
    showBackground = true
)
@Composable
fun PreviewWelcomeScreen() {
    MoviesAppTheme {
        WelcomeScreen(
            isDarkMode = isSystemInDarkTheme(),
        ){}
    }
}