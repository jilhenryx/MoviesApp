package com.example.moviesapp.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.moviesapp.R
import com.example.moviesapp.network.AppAuthResult
import com.example.moviesapp.ui.composables.reusablecomposables.*
import com.example.moviesapp.ui.constants.*
import com.example.moviesapp.ui.stateholders.rememberSignUpStateHolder
import com.example.moviesapp.ui.theme.MoviesAppTheme
import com.example.moviesapp.viewmodels.AuthViewModel
import kotlinx.coroutines.launch

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
    var isFirstnameCorrect by rememberSaveable { mutableStateOf(true) }
    var isLastnameCorrect by rememberSaveable { mutableStateOf(true) }
    var isEmailCorrect by rememberSaveable { mutableStateOf(true) }
    var isPasswordCorrect by rememberSaveable { mutableStateOf(true) }
    var isConfirmPasswordCorrect by rememberSaveable { mutableStateOf(true) }

    Column(verticalArrangement = Arrangement.spacedBy(SMALL_SPACING)) {
        AppOutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged {
                    isFirstnameCorrect =
                        if (!it.hasFocus && firstname.isNotBlank())
                            isFieldCorrect(firstname, TextFieldType.FIRSTNAME)
                        else true

                },
            value = firstname,
            isError = !isFirstnameCorrect,
            label = stringResource(R.string.text_field_first_name_label),
            onValueChange = { value -> onValueChange(value, TextFieldType.FIRSTNAME) }
        )
        AppOutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged {
                    isLastnameCorrect =
                        if (!it.hasFocus && lastname.isNotBlank()) isFieldCorrect(
                            lastname,
                            TextFieldType.LASTNAME
                        )
                        else true
                },
            value = lastname,
            isError = !isLastnameCorrect,
            label = stringResource(R.string.text_field_last_name_label),
            onValueChange = { value -> onValueChange(value, TextFieldType.LASTNAME) },
        )
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
            isError = !isPasswordCorrect,
            label = stringResource(R.string.text_field_password_label),
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
                        if (!it.hasFocus && confirmPassword.isNotBlank())
                            confirmPassword == password
                        else true

                },
            value = confirmPassword,
            isError = !isConfirmPasswordCorrect,
            label = stringResource(R.string.text_field_confirm_password_label),
            showCharacters = showPassword,
            onValueChange = { value -> onValueChange(value, TextFieldType.CONFIRM_PASSWORD) },
        )

    }
}

@Composable
fun SignUpScreen(
    viewModel: AuthViewModel = viewModel(),
    onLoginClick: () -> Unit,
    onSignUp: (email: String) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val stateHolder = rememberSignUpStateHolder()
    var isLoading by rememberSaveable { mutableStateOf(false) }

    Box {
        AppLoginFlowScaffold(
            headerTitle = stringResource(R.string.signup_header_text),
            headerSubtitle = stateHolder.subtitleState.value.ifBlank { stringResource(R.string.signup_header_subtitle_text) },
            isSubtitleError = stateHolder.subtitleColorState.value,
            content = {
                SignUpTextFields(
                    firstname = stateHolder.firstname,
                    lastname = stateHolder.lastname,
                    email = stateHolder.email,
                    password = stateHolder.password,
                    confirmPassword = stateHolder.confirmPassword,
                    onValueChange = stateHolder::onValueChange
                )

                AppButton(
                    type = AppButtonType.FILLED,
                    title = stringResource(R.string.signup_button_text),
                    onClick = {
                        if (!stateHolder.areAllFieldsCorrect()) {
                            stateHolder.subtitleState.value =
                                DEFAULT_EMPTY_FIELDS_MESSAGE
                            stateHolder.subtitleColorState.value = Color.Red
                        } else {
                            stateHolder.subtitleState.value = ""
                            stateHolder.subtitleColorState.value = null
                            coroutineScope.launch {
                                viewModel.createUser(
                                    firstname = stateHolder.firstname,
                                    lastname = stateHolder.lastname,
                                    email = stateHolder.email,
                                    password = stateHolder.password
                                ).collect { result ->
                                    when (result.state) {
                                        AppAuthResult.ResultState.LOADING -> {
                                            isLoading = true
                                        }
                                        AppAuthResult.ResultState.SUCCESS -> {
                                            viewModel.sendVerificationEmail()
                                            isLoading = false
                                            onSignUp(stateHolder.email)
                                        }
                                        AppAuthResult.ResultState.ERROR -> {
                                            stateHolder.subtitleState.value =
                                                result.errorMessage
                                                    ?: DEFAULT_ERROR_MESSAGE
                                            stateHolder.subtitleColorState.value = Color.Red
                                            isLoading = false
                                        }
                                    }
                                }
                            }
                        }
                    }
                )

                AppDefaultFooter(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = stringResource(R.string.signup_footer_text),
                    link = stringResource(R.string.signup_footer_link_text),
                    onLinkCLick = onLoginClick
                )
            }
        )
        if (isLoading) LoadingScreen()
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
)
@Composable
fun SignUpScreenPreview() {
    MoviesAppTheme {
        SignUpScreen(
            onLoginClick = {},
            onSignUp = {}
        )
    }
}