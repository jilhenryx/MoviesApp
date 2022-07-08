package com.example.moviesapp.ui.composables

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.moviesapp.R
import com.example.moviesapp.ui.composables.reusablecomposables.*
import com.example.moviesapp.ui.theme.MoviesAppTheme

@Composable
fun WelcomeScreenBackground(modifier: Modifier = Modifier, isDarkMode: Boolean) {
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
fun WelcomeScreenContent(modifier: Modifier = Modifier) {
    AppContentColumn(
        modifier = modifier,
    ) {
        AppDefaultHeader(
            title = stringResource(R.string.welcome_header),
            subtitle = stringResource(R.string.welcome_message)
        )
        Spacer(modifier = Modifier.height(spacerHeightSmall))
        AppButton(
            type = AppButtonType.FILLED,
            title = stringResource(R.string.welcome_button_text).uppercase(),
            trailingIcon = R.drawable.arrow_forward,
            iconTint = Color.White
        ) {
            TODO("Yet to Implement Button OnCLick")
        }
        Spacer(modifier = Modifier.height(spacerHeightSmall))
    }
}

@Composable
fun WelcomeScreen(modifier: Modifier = Modifier, isDarkMode: Boolean) {
    Box(modifier = modifier.fillMaxSize()) {
        WelcomeScreenBackground(isDarkMode = isDarkMode)
        WelcomeScreenContent(
            modifier = Modifier
                .align(Alignment.BottomCenter)
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
        WelcomeScreen(isDarkMode = isSystemInDarkTheme())
    }
}