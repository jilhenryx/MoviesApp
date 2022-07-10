package com.example.moviesapp.ui.composables

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.moviesapp.R
import com.example.moviesapp.ui.composables.reusablecomposables.AppButton
import com.example.moviesapp.ui.composables.reusablecomposables.AppButtonType
import com.example.moviesapp.ui.composables.reusablecomposables.AppContentColumn
import com.example.moviesapp.ui.composables.reusablecomposables.AppDefaultHeader
import com.example.moviesapp.ui.constants.ROUTE_LOGIN_GRAPH
import com.example.moviesapp.ui.constants.ROUTE_LOGIN_SCREEN
import com.example.moviesapp.ui.constants.ROUTE_WELCOME_SCREEN
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
        verticalArrangement = Arrangement.spacedBy(spacerHeightMedium)
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
        Spacer(modifier = Modifier.height(spacerHeightSmall))
    }
}

@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    isDarkMode: Boolean
) {
    Box(modifier = modifier.fillMaxSize()) {
        WelcomeScreenBackground(isDarkMode = isDarkMode)
        WelcomeScreenContent(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(horizontal = 12.dp)
        ) {
            navController.navigate(route = ROUTE_LOGIN_GRAPH) {
                popUpTo(route = ROUTE_WELCOME_SCREEN){
                    inclusive = true
                }
            }
        }
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
            navController = rememberNavController()
        )
    }
}