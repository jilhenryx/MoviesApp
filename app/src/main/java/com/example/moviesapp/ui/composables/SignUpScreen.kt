package com.example.moviesapp.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.moviesapp.R
import com.example.moviesapp.ui.composables.reusablecomposables.*
import com.example.moviesapp.ui.constants.ROUTE_CONFIRM_EMAIL_SCREEN
import com.example.moviesapp.ui.constants.ROUTE_LOGIN_SCREEN
import com.example.moviesapp.ui.theme.MoviesAppTheme

@Composable
fun SignUpTextFields(
    firstname: String,
    lastname: String,
    email: String,
    password: String,
    confirmPassword: String,
    onValueChange: (value: String, fieldType: TextFieldType) -> Unit
) {
    var showPassword by rememberSaveable { mutableStateOf(false) }

    Column(verticalArrangement = Arrangement.spacedBy(spacedBySmall)) {
        AppOutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = firstname,
            label = stringResource(R.string.text_field_first_name_label),
            onValueChange = { value -> onValueChange(value, TextFieldType.FIRSTNAME) }
        )
        AppOutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = lastname,
            label = stringResource(R.string.text_field_last_name_label),
            onValueChange = { value -> onValueChange(value, TextFieldType.LASTNAME) },
        )
        AppOutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = email,
            label = stringResource(R.string.text_field_email_label),
            onValueChange = { value -> onValueChange(value, TextFieldType.EMAIL) },
            keyboardType = KeyboardType.Email
        )
        AppOutlinedTextFieldPassword(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            label = stringResource(R.string.text_field_password_label),
            showCharacters = showPassword,
            visibilityOnIcon = R.drawable.ic_baseline_visibility_24,
            visibilityOffIcon = R.drawable.ic_baseline_visibility_off_24,
            onValueChange = { value -> onValueChange(value, TextFieldType.PASSWORD) },
            onPasswordToggle = { showPassword = !showPassword }
        )
        AppOutlinedTextFieldPassword(
            modifier = Modifier.fillMaxWidth(),
            value = confirmPassword,
            label = stringResource(R.string.text_field_confirm_password_label),
            showCharacters = showPassword,
            onValueChange = { value -> onValueChange(value, TextFieldType.CPASSWORD) },
        )

    }
}

@Composable
fun SignUpScreen(navController: NavController) {
    AppSignUpLoginScaffold(
        headerTitle = stringResource(R.string.signup_header_text),
        headerSubtitle = stringResource(R.string.signup_header_subtitle_text),
        onNavBackClick = {
               navController.popBackStack()
        },
        content = {
            SignUpTextFields(
                firstname = "",
                lastname = "",
                email = "",
                password = "",
                confirmPassword = "",
                onValueChange = { _, _ -> }
            )

            AppButton(
                type = AppButtonType.FILLED,
                title = stringResource(R.string.signup_button_text),
                onClick = {
                    /*TODO : Perform Signing Action*/
                    navController.navigate(route = "$ROUTE_CONFIRM_EMAIL_SCREEN/domain@hostname.com")
                }
            )

            AppDefaultFooter(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = stringResource(R.string.signup_footer_text),
                link = stringResource(R.string.signup_footer_link_text),
                onLinkCLick = {
                    navController.navigate(route = ROUTE_LOGIN_SCREEN) {
                        popUpTo(route = ROUTE_LOGIN_SCREEN) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    )
}

@Preview(
    showBackground = true,
    showSystemUi = true,
)
@Composable
fun SignUpScreenPreview() {
    MoviesAppTheme {
        SignUpScreen(
            navController = rememberNavController()
        )
    }
}