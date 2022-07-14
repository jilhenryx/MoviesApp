package com.example.moviesapp.ui.composables

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.moviesapp.R
import com.example.moviesapp.network.AppAuthResult
import com.example.moviesapp.ui.composables.reusablecomposables.*
import com.example.moviesapp.ui.constants.*
import com.example.moviesapp.ui.stateholders.rememberLoginStateHolder
import com.example.moviesapp.ui.theme.MoviesAppTheme
import com.example.moviesapp.viewmodels.AuthViewModel
import kotlinx.coroutines.launch

private const val TAG = "LoginScreen"

@Composable
private fun LoginTextFields(
    email: String,
    password: String,
    onValueChange: (value: String, fieldType: TextFieldType) -> Unit,
    onForgotPasswordClick: () -> Unit,
) {
    var showPassword by rememberSaveable { mutableStateOf(false) }
    var isEmailCorrect by rememberSaveable { mutableStateOf(true) }
    var isPasswordCorrect by rememberSaveable { mutableStateOf(true) }

    Column(verticalArrangement = Arrangement.spacedBy(SMALL_SPACING)) {
        AppOutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged {
                    isEmailCorrect =
                        if (!it.hasFocus && email.isNotBlank())
                            isFieldCorrect(email, TextFieldType.EMAIL)
                        else true
                },
            value = email,
            label = stringResource(R.string.text_field_email_label),
            isError = !isEmailCorrect,
            onValueChange = { value -> onValueChange(value, TextFieldType.EMAIL) },
            keyboardType = KeyboardType.Email
        )
        AppOutlinedTextFieldPassword(
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged {
                    isPasswordCorrect =
                        if (!it.hasFocus && password.isNotBlank())
                            isFieldCorrect(password, TextFieldType.PASSWORD)
                        else true
                },
            value = password,
            label = stringResource(R.string.text_field_password_label),
            showCharacters = showPassword,
            isError = !isPasswordCorrect,
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
private fun LoginButtons(onLoginCLicked: () -> Unit, onGoogleLoginCLicked: () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(SMALL_SPACING)) {
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
    viewModel: AuthViewModel = viewModel(),
    onForgotPasswordClick: (email: String) -> Unit,
    onSignUpClick: () -> Unit,
    navigateToMain: () -> Unit,
    navigateToCheckEmail: (email: String) -> Unit
) {
    val stateHolder = rememberLoginStateHolder()
    val coroutineScope = rememberCoroutineScope()
    val lifecycleOwner = LocalLifecycleOwner.current
    var isLoading by rememberSaveable { mutableStateOf(false) }

    Box {
        AppLoginFlowScaffold(
            headerTitle = stringResource(R.string.login_header_text),
            headerSubtitle = stateHolder.subtitleState.value.ifBlank { stringResource(R.string.login_subtitle_text) },
            isSubtitleError = stateHolder.subtitleColorState.value
        ) {
            LoginTextFields(
                email = stateHolder.email,
                password = stateHolder.password,
                onValueChange = stateHolder::onValueChange,
                onForgotPasswordClick = {
                    if (stateHolder.email.isBlank()
                        || !isFieldCorrect(stateHolder.email, TextFieldType.EMAIL)
                    ) {
                        stateHolder.subtitleState.value = "Please enter a valid email address"
                        stateHolder.subtitleColorState.value = Color.Red
                    } else {
                        stateHolder.subtitleState.value = ""
                        stateHolder.subtitleColorState.value = null
                        onForgotPasswordClick(stateHolder.email)
                    }
                },
            )

            LoginButtons(onLoginCLicked = {
                if (!stateHolder.areAllFieldsCorrect()) {
                    stateHolder.subtitleState.value =
                        DEFAULT_EMPTY_FIELDS_MESSAGE
                    stateHolder.subtitleColorState.value = Color.Red
                } else {
                    stateHolder.subtitleState.value = ""
                    stateHolder.subtitleColorState.value = null
                    coroutineScope.launch {
                        stateHolder.subtitleState.value = ""
                        viewModel.login(
                            stateHolder.email,
                            stateHolder.password
                        ).flowWithLifecycle(lifecycleOwner.lifecycle)
                            .collect { authResult ->
                                Log.d(TAG, "LoginScreen: Collecting Flow ${authResult.state}")
                                when (authResult.state) {
                                    AppAuthResult.ResultState.LOADING -> {
                                        isLoading = true
                                    }
                                    AppAuthResult.ResultState.SUCCESS -> {
                                        isLoading = false
                                        if (viewModel.isUserEmailVerified()) {
                                            navigateToMain()
                                        } else {
                                            viewModel.sendVerificationEmail()
                                            navigateToCheckEmail(stateHolder.email)
                                        }
                                    }
                                    AppAuthResult.ResultState.ERROR -> {
                                        Log.d(TAG, "LoginScreen: Login Failed")
                                        stateHolder.subtitleState.value =
                                            authResult.errorMessage ?: DEFAULT_ERROR_MESSAGE
                                        stateHolder.subtitleColorState.value = Color.Red
                                        isLoading = false
                                    }
                                }
                            }
                    }
                }
            }, onGoogleLoginCLicked = {})

            AppDefaultFooter(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = stringResource(R.string.login_footer_text),
                link = stringResource(R.string.login_footer_link_text),
                onLinkCLick = onSignUpClick
            )
        }
        if (isLoading) LoadingScreen()
    }

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
            onSignUpClick = {},
            navigateToMain = {},
            navigateToCheckEmail = {}
        )
    }
}