package com.example.moviesapp.ui.forgotpassword

import com.example.moviesapp.ui.reusablecomposables.AuthAppBar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.moviesapp.R
import com.example.moviesapp.ui.composablehelpers.ComposeConstants.LARGE_SPACING
import com.example.moviesapp.ui.reusablecomposables.*

@Composable
fun ForgotPasswordScreen(
    email: String,
    navigateToCheckEmail: (email: String) -> Unit,
    navigateUp: () -> Unit
) {
    ForgotPasswordScreen(
        modifier = Modifier,
        viewModel = hiltViewModel(),
        email = email,
        navigateToCheckEmail = navigateToCheckEmail,
        navigateUp = navigateUp
    )
}

@Composable
private fun ForgotPasswordScreen(
    modifier: Modifier,
    viewModel: ForgotPasswordViewModel,
    email: String,
    navigateToCheckEmail: (email: String) -> Unit,
    navigateUp: () -> Unit,
) {
    ForgotPasswordScreen(
        modifier = modifier,
        state = viewModel.state,
        email = email,
        submit = { viewModel.submit(navigateToCheckEmail) },
        navigateUp = navigateUp,
        onTextFieldValueChanged = viewModel::onTextFieldValueChanged
    )

}

@Composable
private fun ForgotPasswordScreen(
    modifier: Modifier = Modifier,
    state: ForgotPasswordStateHandler.StateHolder,
    email: String,
    submit: () -> Unit,
    navigateUp: () -> Unit,
    onTextFieldValueChanged: (value: String) -> Unit
) {
    val focusManager = LocalFocusManager.current

    LaunchedEffect(email) {
        onTextFieldValueChanged(email)
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            AuthAppBar(
                modifier = Modifier,
                showUpIndicator = true,
                onUpButtonClick = navigateUp
            )
        }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            AppContentColumn(modifier = Modifier) {
                AuthHeaderIcon(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    iconRes = R.drawable.question_icon
                )

                Spacer(modifier = Modifier.height(LARGE_SPACING))

                AuthScreensHeader(
                    title = stringResource(R.string.forgot_password_header_text),
                    subtitle = state.subtitle.text.ifBlank { stringResource(R.string.forgot_password_header_subtitle_text) },
                    isSubtitleError = state.subtitle.isError
                )

                Spacer(modifier = Modifier.height(LARGE_SPACING))

                AppOutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = state.email,
                    label = stringResource(R.string.text_field_email_label),
                    onValueChange = onTextFieldValueChanged,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(onDone = {
                        focusManager.clearFocus()
                        submit()
                    })
                )

                Spacer(modifier = Modifier.height(LARGE_SPACING))

                AppButton(
                    modifier = Modifier,
                    type = AppButtonType.FILLED,
                    title = stringResource(R.string.continue_button_text).uppercase(),
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