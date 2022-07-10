package com.example.moviesapp.ui.composables

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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
import com.example.moviesapp.ui.constants.ROUTE_FORGOT_PASSWORD_SCREEN
import com.example.moviesapp.ui.constants.ROUTE_SIGN_UP_SCREEN
import com.example.moviesapp.ui.theme.MoviesAppTheme

@Composable
fun LoginTextFields(
    email: String,
    password: String,
    onValueChange: (value: String, fieldType: TextFieldType) -> Unit,
    onForgotPasswordClick: () -> Unit,
) {
    var showPassword by rememberSaveable { mutableStateOf(false) }

    Column(verticalArrangement = Arrangement.spacedBy(spacedBySmall)) {
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
        Text(
            modifier = Modifier
                .align(Alignment.End)
                .clickable(onClick = onForgotPasswordClick),
            text = stringResource(R.string.login_forgot_password_text),
            color = MaterialTheme.colors.primary,
        )
    }

}

@Composable
fun LoginButtons(onLoginCLicked: () -> Unit, onGoogleLoginCLicked: () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(spacedBySmall)) {
        AppButton(
            type = AppButtonType.FILLED,
            title = stringResource(id = R.string.login_button_text).uppercase(),
            onClick = onLoginCLicked
        )
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = stringResource(R.string.login_alt_sign_in_text),
        )
        AppButton(
            type = AppButtonType.OUTLINED,
            title = stringResource(id = R.string.login_google_button_text),
            leadingIcon = R.drawable.google_icon,
            onClick = onGoogleLoginCLicked
        )
    }
}

@Composable
fun LoginScreen(
    onForgotPasswordClick: (email:String) -> Unit,
    onSignUpClick: () -> Unit,
) {
    AppLoginFlowScaffold(
        headerTitle = stringResource(id = R.string.login_header_text),
        headerSubtitle = stringResource(id = R.string.login_subtitle_text),
        content = {
            LoginTextFields(
                email = "",
                password = "",
                onValueChange = { _, _ -> },
                onForgotPasswordClick = { onForgotPasswordClick("domain@hostname.com") })

            LoginButtons(onLoginCLicked = {}, onGoogleLoginCLicked = {})

            AppDefaultFooter(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = stringResource(R.string.login_footer_text),
                link = stringResource(R.string.login_footer_link_text),
                onLinkCLick = onSignUpClick)
        }
    )
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun LoginScreenPreview() {
    MoviesAppTheme {
        LoginScreen(
            onForgotPasswordClick = {},
            onSignUpClick = {}
        )
    }
}