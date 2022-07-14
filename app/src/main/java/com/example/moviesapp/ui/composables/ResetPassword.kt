package com.example.moviesapp.ui.composables

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.moviesapp.R
import com.example.moviesapp.ui.composables.reusablecomposables.AppButton
import com.example.moviesapp.ui.composables.reusablecomposables.AppButtonType
import com.example.moviesapp.ui.composables.reusablecomposables.AppLoginFlowScaffold
import com.example.moviesapp.ui.composables.reusablecomposables.AppOutlinedTextFieldPassword
import com.example.moviesapp.ui.constants.SMALL_SPACING
import com.example.moviesapp.ui.constants.TextFieldType
import com.example.moviesapp.ui.constants.isFieldCorrect
import com.example.moviesapp.ui.theme.MoviesAppTheme
import com.example.moviesapp.viewmodels.AuthViewModel

private const val TAG = "ResetPassword"

@Composable
fun ResetPasswordTextFields(
    password: String,
    confirmPassword: String,
    onValueChange: (value: String, fieldType: TextFieldType) -> Unit
) {
    var showPassword by rememberSaveable { mutableStateOf(false) }
    var isPasswordCorrect by rememberSaveable { mutableStateOf(false) }
    var isConfirmPasswordCorrect by rememberSaveable { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.spacedBy(SMALL_SPACING)
    ) {
        AppOutlinedTextFieldPassword(
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged {
                    isPasswordCorrect =
                        if (it.hasFocus) true else isFieldCorrect(password, TextFieldType.PASSWORD)
                },
            value = password,
            label = stringResource(R.string.text_field_password_label),
            isError = !isPasswordCorrect,
            showCharacters = showPassword,
            visibilityOnIcon = R.drawable.ic_baseline_visibility_24,
            visibilityOffIcon = R.drawable.ic_baseline_visibility_off_24,
            onValueChange = { value -> onValueChange(value, TextFieldType.PASSWORD) },
            onPasswordToggle = { showPassword = !showPassword }
        )
        AppOutlinedTextFieldPassword(
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged {
                    isConfirmPasswordCorrect =
                        if (it.hasFocus) true else password === confirmPassword
                },
            value = confirmPassword,
            isError = !isConfirmPasswordCorrect,
            label = stringResource(R.string.text_field_confirm_password_label),
            showCharacters = showPassword,
            onValueChange = { value ->
                onValueChange(value, TextFieldType.CONFIRM_PASSWORD)
            },
        )
    }
}

@Composable
fun ResetPasswordScreen(
    viewModel: AuthViewModel = viewModel(),
    navigateToLogin: () -> Unit
) {
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    var isLoading by rememberSaveable { mutableStateOf(false) }

    AppLoginFlowScaffold(
        headerIconRes = R.drawable.password_reset_icon,
        headerTitle = stringResource(R.string.reset_password_header_text),
        headerSubtitle = stringResource(R.string.rest_password_header_subtitle_text),
        content = {
            ResetPasswordTextFields(
                password = password,
                confirmPassword = confirmPassword,
                onValueChange = { value, fieldType ->
                    when (fieldType) {
                        TextFieldType.PASSWORD -> password = value
                        TextFieldType.CONFIRM_PASSWORD -> confirmPassword = value
                        else -> Log.d(
                            TAG,
                            "ResetPasswordScreen: OnValueChange Type is unrecognised"
                        )
                    }
                },
            )
            AppButton(
                type = AppButtonType.FILLED,
                title = stringResource(R.string.confirm_button_text).uppercase(),
                onClick = {
                    val shouldProceed =
                        isFieldCorrect(
                            password,
                            TextFieldType.PASSWORD
                        ) && password === confirmPassword
                    if (!shouldProceed) {
                        /*TODO*/
                    } else {
                        /*TODO: Implement Reset Logic*/
                        navigateToLogin()
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
fun ResetPasswordScreenPreview() {
    MoviesAppTheme {
        ResetPasswordScreen(
            navigateToLogin = {}
        )
    }
}