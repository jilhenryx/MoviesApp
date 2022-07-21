package com.example.moviesapp.ui.resetpassword

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.moviesapp.R
import com.example.moviesapp.ui.composablehelpers.ComposeConstants.MEDIUM_SPACING
import com.example.moviesapp.ui.composablehelpers.ComposeConstants.SMALL_SPACING
import com.example.moviesapp.ui.composablehelpers.isFieldCorrect
import com.example.moviesapp.ui.reusablecomposables.*

@Composable
fun ResetPasswordScreen(
    resetCode: String,
    navigateToLogin: () -> Unit
) {

    ResetPasswordScreen(
        viewModel = hiltViewModel(),
        resetCode = resetCode,
        navigateToLogin = navigateToLogin
    )

}

@Composable
private fun ResetPasswordScreen(
    viewModel: ResetPasswordViewModel,
    resetCode: String,
    navigateToLogin: () -> Unit
) {

    ResetPasswordScreen(
        state = viewModel.state(),
        resetCode = resetCode,
        verifyResetCode = { viewModel.verifyResetCode(resetCode, navigateToLogin) },
        onTextFieldValueChanged = { value, fieldType ->
            viewModel.onTextFieldValueChange(value, fieldType)
        },
        submit = { viewModel.changePassword(resetCode, navigateToLogin) }
    )
}

@Composable
private fun ResetPasswordScreen(
    state: ResetPasswordStateHandler.StateHolder,
    resetCode: String,
    verifyResetCode: () -> Unit,
    onTextFieldValueChanged: (value: String, fieldType: TextFieldType) -> Unit,
    submit: () -> Unit,
) {
    val focusManager = LocalFocusManager.current

    LaunchedEffect(resetCode) {
        verifyResetCode()
    }

    Scaffold {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {

            AppContentColumn(modifier = Modifier) {

                AuthHeaderIcon(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    iconRes = R.drawable.password_reset_icon
                )

                Spacer(modifier = Modifier.height(MEDIUM_SPACING))

                AuthScreensHeader(
                    title = stringResource(R.string.reset_password_header_text),
                    subtitle = state.subtitle.text.ifBlank {
                        stringResource(
                            R.string.rest_password_header_subtitle_text,
                            state.email
                        )
                    },
                    isSubtitleError = state.subtitle.isError
                )

                Spacer(modifier = Modifier.height(MEDIUM_SPACING))

                ResetPasswordTextFields(
                    password = state.password,
                    confirmPassword = state.confirmPassword,
                    onDone = {
                        focusManager.clearFocus()
                        submit()
                    },
                    onValueChange = onTextFieldValueChanged
                )

                Spacer(modifier = Modifier.height(MEDIUM_SPACING))

                AppButton(
                    modifier = Modifier,
                    type = AppButtonType.FILLED,
                    title = stringResource(R.string.confirm_button_text).uppercase(),
                    onClick = {
                        focusManager.clearFocus()
                        submit()
                    }
                )
            }

            LoadingScreen(modifier = Modifier, show = state.isLoading)
        }
    }
}

@Composable
private fun ResetPasswordTextFields(
    password: String,
    confirmPassword: String,
    onDone: () -> Unit,
    onValueChange: (value: String, fieldType: TextFieldType) -> Unit
) {
    var showPassword by rememberSaveable { mutableStateOf(false) }
    var isPasswordCorrect by rememberSaveable { mutableStateOf(false) }
    var isConfirmPasswordCorrect by rememberSaveable { mutableStateOf(false) }

    AppOutlinedTextFieldPassword(
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged {
                isPasswordCorrect =
                    if (!it.hasFocus && password.isNotBlank())
                        password.isFieldCorrect(TextFieldType.PASSWORD)
                    else true
            },
        value = password,
        label = stringResource(R.string.text_field_password_label),
        isError = !isPasswordCorrect,
        showCharacters = showPassword,
        visibilityOnIcon = R.drawable.ic_baseline_visibility_24,
        visibilityOffIcon = R.drawable.ic_baseline_visibility_off_24,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Next
        ),
        onValueChange = { value -> onValueChange(value, TextFieldType.PASSWORD) },
        onPasswordToggle = { showPassword = !showPassword }
    )

    Spacer(modifier = Modifier.height(SMALL_SPACING))

    AppOutlinedTextFieldPassword(
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged {
                isConfirmPasswordCorrect =
                    if (!it.hasFocus && confirmPassword.isNotBlank())
                        password == confirmPassword
                    else true
            },
        value = confirmPassword,
        isError = !isConfirmPasswordCorrect,
        label = stringResource(R.string.text_field_confirm_password_label),
        showCharacters = showPassword,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = { onDone() }),
        onValueChange = { value ->
            onValueChange(value, TextFieldType.CONFIRM_PASSWORD)
        },
    )
}
