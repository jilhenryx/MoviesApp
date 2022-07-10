package com.example.moviesapp.ui.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.moviesapp.R
import com.example.moviesapp.ui.composables.reusablecomposables.AppButton
import com.example.moviesapp.ui.composables.reusablecomposables.AppButtonType
import com.example.moviesapp.ui.composables.reusablecomposables.AppOutlinedTextField
import com.example.moviesapp.ui.composables.reusablecomposables.AppSignUpLoginScaffold
import com.example.moviesapp.ui.constants.ROUTE_CHECK_EMAIL_SCREEN
import com.example.moviesapp.ui.constants.ROUTE_LOGIN_SCREEN
import com.example.moviesapp.ui.theme.MoviesAppTheme


@Composable
fun ForgotPasswordScreen(
    navController: NavController,
    email: String
) {
    AppSignUpLoginScaffold(
        headerIconRes = R.drawable.question_icon,
        onNavBackClick = {
            navController.popBackStack()
        },
        headerTitle = stringResource(R.string.forgot_password_header_text),
        headerSubtitle = stringResource(R.string.forgot_password_header_subtitle_text),
        content = {
            var typedEmail: String by remember { mutableStateOf(email) }

            AppOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = typedEmail,
                label = stringResource(R.string.text_field_email_label),
                onValueChange = { value ->
                    typedEmail = value
                },
                keyboardType = KeyboardType.Email
            )
            AppButton(
                type = AppButtonType.FILLED,
                title = stringResource(R.string.continue_button_text).uppercase(),
                onClick = {
                    /*TODO : Implement Reset Password Logic*/
                    navController.navigate(route = "$ROUTE_CHECK_EMAIL_SCREEN/email") {
                        popUpTo(route = ROUTE_LOGIN_SCREEN)
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
fun ForgotPasswordScreenPreview() {
    MoviesAppTheme {
        ForgotPasswordScreen(
            navController = rememberNavController(),
            email = "domain@hostname.com"
        )
    }
}