package com.example.moviesapp.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.moviesapp.R
import com.example.moviesapp.ui.composables.reusablecomposables.*
import com.example.moviesapp.ui.theme.MoviesAppTheme

@Composable
fun ResetPasswordTextFields(
    password: String,
    confirmPassword: String,
    onValueChange: (value: String, fieldType: TextFieldType) -> Unit
) {
    var showPassword by rememberSaveable { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.spacedBy(spacedBySmall)
    ) {
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
fun ResetPasswordScreen() {
    AppSignUpLoginScaffold(
        headerIconRes = R.drawable.password_reset_icon,
        spacerHeight = spacerHeightLarge,
        headerTitle = stringResource(R.string.reset_password_header_text),
        headerSubtitle = stringResource(R.string.rest_password_header_subtitle_text),
        content = {
            ResetPasswordTextFields(
                password = "",
                confirmPassword = "",
                onValueChange = { _, _ -> },
            )
            AppButton(
                type = AppButtonType.FILLED,
                title = stringResource(R.string.confirm_button_text).uppercase(),
                onClick = { }
            )
        }
    )
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun ResetPasswordScreenPreview() {
    MoviesAppTheme {
        ResetPasswordScreen()
    }
}