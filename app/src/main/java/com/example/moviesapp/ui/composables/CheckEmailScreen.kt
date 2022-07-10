package com.example.moviesapp.ui.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.moviesapp.R
import com.example.moviesapp.ui.composables.reusablecomposables.AppSignUpLoginScaffold
import com.example.moviesapp.ui.composables.reusablecomposables.RetryEmailText
import com.example.moviesapp.ui.constants.ROUTE_RESET_PASSWORD_SCREEN
import com.example.moviesapp.ui.theme.MoviesAppTheme

@Composable
fun CheckEmailScreen(navController: NavController,
                     email:String
) {
    AppSignUpLoginScaffold(
        headerIconRes = R.drawable.new_mail_icon,
        onNavBackClick = {
            navController.popBackStack()
        },
        headerTitle = stringResource(R.string.check_email_header_text),
        headerSubtitle = stringResource(
            R.string.check_email_header_subtitle_text,
            email
        ),
        content = {
            RetryEmailText(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                timerText = "4:00",
                onRetryClicked = {
                    /*TODO Remove Navigation to Reset Password Screen and
                       Implement right logic */
                    navController.navigate(route = ROUTE_RESET_PASSWORD_SCREEN) {
                        popUpTo(route = ROUTE_RESET_PASSWORD_SCREEN)
                    }
                }
            )
        }
    )
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun CheckEmailScreenPreview() {
    MoviesAppTheme {
        CheckEmailScreen(
            email = "domain@hostname.com",
            navController = rememberNavController()
        )
    }
}