package com.example.moviesapp.ui.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
fun LoginScreen(
    navigateToSignUp: () -> Unit,
    navigateToMain: () -> Unit,
    navigateToForgotPassword: (email: String) -> Unit,
    navigateToCheckEmail: (email: String) -> Unit
) {
    LoginScreen(
        viewModel = hiltViewModel(),
        navigateToSignUp = navigateToSignUp,
        navigateToMain = navigateToMain,
        navigateToForgotPassword = navigateToForgotPassword,
        navigateToCheckEmail = navigateToCheckEmail,
    )
}

@Composable
private fun LoginScreen(
    viewModel: LoginViewModel,
    navigateToSignUp: () -> Unit,
    navigateToMain: () -> Unit,
    navigateToForgotPassword: (email: String) -> Unit,
    navigateToCheckEmail: (email: String) -> Unit,
) {
    LoginScreen(
        state = viewModel.state,
        navigateToSignUp = navigateToSignUp,
        navigateToForgotPassword = navigateToForgotPassword,
        onTextFieldValueChanged = { value, fieldType ->
            viewModel.onTextFieldValueChange(value, fieldType)
        },
        onLoginButtonClicked = { viewModel.login(navigateToMain, navigateToCheckEmail) },
        onGoogleSignIn = {}
    )

}

@Composable
private fun LoginScreen(
    modifier: Modifier = Modifier,
    state: LoginStateHandler.LoginStateHolder,
    navigateToSignUp: () -> Unit,
    navigateToForgotPassword: (email: String) -> Unit,
    onTextFieldValueChanged: (value: String, fieldType: TextFieldType) -> Unit,
    onLoginButtonClicked: () -> Unit,
    onGoogleSignIn: () -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    val focusManager = LocalFocusManager.current

    Scaffold(
        modifier = modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        topBar = {
            AuthAppBar(modifier = Modifier)
        }) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            AppContentColumn(modifier = Modifier) {

                AuthScreensHeader(
                    title = stringResource(R.string.login_header_text),
                    subtitle = state.subtitle.text.ifBlank { stringResource(id = R.string.login_subtitle_text) },
                    isSubtitleError = state.subtitle.isError
                )

                Spacer(modifier = Modifier.height(MEDIUM_SPACING))

                LoginTextFields(
                    email = state.email,
                    password = state.password,
                    onValueChange = onTextFieldValueChanged,
                    onDone = {
                        focusManager.clearFocus()
                        onLoginButtonClicked()
                    },
                    onForgotPasswordClick = {
                        navigateToForgotPassword(state.email)
                    }
                )

                Spacer(modifier = Modifier.height(MEDIUM_SPACING))

                LoginButtons(
                    onLoginCLicked = {
                        focusManager.clearFocus()
                        onLoginButtonClicked()
                    },
                    onGoogleLoginCLicked = {
                        focusManager.clearFocus()
                        onGoogleSignIn()
                    }
                )

                Spacer(modifier = Modifier.height(MEDIUM_SPACING))

                AppDefaultFooter(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = stringResource(R.string.login_footer_text),
                    link = stringResource(R.string.login_footer_link_text),
                    onLinkCLick = navigateToSignUp
                )
            }

            LoadingScreen(modifier = Modifier, show = state.isLoading)
        }
    }
}

@Composable
private fun ColumnScope.LoginTextFields(
    email: String,
    password: String,
    onValueChange: (value: String, fieldType: TextFieldType) -> Unit,
    onDone: () -> Unit,
    onForgotPasswordClick: () -> Unit,
) {
    var showPassword by rememberSaveable { mutableStateOf(false) }
    var isEmailCorrect by rememberSaveable { mutableStateOf(true) }
    var isPasswordCorrect by rememberSaveable { mutableStateOf(true) }

    AppOutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged {
                isEmailCorrect =
                    if (!it.hasFocus && email.isNotBlank())
                        email.isFieldCorrect(TextFieldType.EMAIL)
                    else true
            },
        value = email,
        label = stringResource(R.string.text_field_email_label),
        isError = !isEmailCorrect,
        onValueChange = { value -> onValueChange(value, TextFieldType.EMAIL) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        )
    )

    Spacer(modifier = Modifier.height(SMALL_SPACING))

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
        showCharacters = showPassword,
        isError = !isPasswordCorrect,
        visibilityOnIcon = R.drawable.ic_baseline_visibility_24,
        visibilityOffIcon = R.drawable.ic_baseline_visibility_off_24,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = { onDone() }),
        onValueChange = { value -> onValueChange(value, TextFieldType.PASSWORD) },
        onPasswordToggle = { showPassword = !showPassword }
    )

    Spacer(modifier = Modifier.height(SMALL_SPACING))

    Text(
        modifier = Modifier
            .align(Alignment.End)
            .clickable(onClick = onForgotPasswordClick),
        text = stringResource(R.string.login_forgot_password_text),
        color = MaterialTheme.colors.primary,
    )
}

@Composable
private fun ColumnScope.LoginButtons(
    onLoginCLicked: () -> Unit,
    onGoogleLoginCLicked: () -> Unit
) {
    AppButton(
        type = AppButtonType.FILLED,
        title = stringResource(id = R.string.login_button_text).uppercase(),
        onClick = onLoginCLicked
    )

    Spacer(modifier = Modifier.height(SMALL_SPACING))

    Text(
        modifier = Modifier.align(Alignment.CenterHorizontally),
        text = stringResource(R.string.login_alt_sign_in_text),
    )

    Spacer(modifier = Modifier.height(SMALL_SPACING))

    AppButton(
        type = AppButtonType.OUTLINED,
        title = stringResource(id = R.string.login_google_button_text),
        leadingIcon = R.drawable.google_icon,
        onClick = onGoogleLoginCLicked
    )

}