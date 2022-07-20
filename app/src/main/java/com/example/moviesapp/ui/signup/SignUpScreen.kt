package com.example.moviesapp.ui.signup

import com.example.moviesapp.ui.reusablecomposables.AuthAppBar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Scaffold
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
fun SignUpScreen(
    navigateToLogin: () -> Unit,
    navigateToCheckEmail: (email: String) -> Unit,
    navigateUp: () -> Unit
) {
    SignUpScreen(
        viewModel = hiltViewModel(),
        navigateToLogin = navigateToLogin,
        navigateToCheckEmail = navigateToCheckEmail,
        navigateUp = navigateUp
    )
}

@Composable
private fun SignUpScreen(
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel,
    navigateToLogin: () -> Unit,
    navigateToCheckEmail: (email: String) -> Unit,
    navigateUp: () -> Unit
) {
    SignUpScreen(
        modifier = modifier,
        state = viewModel.state,
        navigateToLogin = navigateToLogin,
        navigateUp = navigateUp,
        onSignUpClicked = { viewModel.signUp(navigateToCheckEmail) },
        onTextFieldValueChange = viewModel::onTextFieldValueChange
    )

}

@Composable
private fun SignUpScreen(
    modifier: Modifier = Modifier,
    state: SignUpStateHandler.SignUpStateHolder,
    navigateToLogin: () -> Unit,
    navigateUp: () -> Unit,
    onSignUpClicked: () -> Unit,
    onTextFieldValueChange: (value: String, fieldType: TextFieldType) -> Unit
) {
    val focusManager = LocalFocusManager.current

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
                AuthScreensHeader(
                    title = stringResource(R.string.signup_header_text),
                    subtitle = state.subtitle.text.ifBlank { stringResource(R.string.signup_header_subtitle_text) },
                    isSubtitleError = state.subtitle.isError,
                )

                Spacer(Modifier.height(MEDIUM_SPACING))

                SignUpTextFields(
                    firstname = state.firstname,
                    lastname = state.lastname,
                    email = state.email,
                    password = state.password,
                    confirmPassword = state.confirmPassword,
                    onValueChange = onTextFieldValueChange,
                    onDone = {
                        focusManager.clearFocus()
                        onSignUpClicked()
                    }
                )

                Spacer(modifier = Modifier.height(MEDIUM_SPACING))

                AppButton(
                    type = AppButtonType.FILLED,
                    title = stringResource(R.string.signup_button_text),
                    onClick = {
                        focusManager.clearFocus()
                        onSignUpClicked()
                    }
                )

                Spacer(modifier = Modifier.height(MEDIUM_SPACING))

                AppDefaultFooter(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = stringResource(R.string.signup_footer_text),
                    link = stringResource(R.string.signup_footer_link_text),
                    onLinkCLick = navigateToLogin
                )
            }

            LoadingScreen(modifier = Modifier, show = state.isLoading)
        }
    }
}

@Composable
private fun SignUpTextFields(
    firstname: String,
    lastname: String,
    email: String,
    password: String,
    confirmPassword: String,
    onDone: () -> Unit,
    onValueChange: (value: String, fieldType: TextFieldType) -> Unit
) {
    var showPassword by rememberSaveable { mutableStateOf(false) }
    var isFirstnameCorrect by rememberSaveable { mutableStateOf(true) }
    var isLastnameCorrect by rememberSaveable { mutableStateOf(true) }
    var isEmailCorrect by rememberSaveable { mutableStateOf(true) }
    var isPasswordCorrect by rememberSaveable { mutableStateOf(true) }
    var isConfirmPasswordCorrect by rememberSaveable { mutableStateOf(true) }

    AppOutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged {
                isFirstnameCorrect =
                    if (!it.hasFocus && firstname.isNotBlank())
                        firstname.isFieldCorrect(TextFieldType.FIRSTNAME)
                    else true

            },
        value = firstname,
        isError = !isFirstnameCorrect,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        label = stringResource(R.string.text_field_first_name_label),
        onValueChange = { value -> onValueChange(value, TextFieldType.FIRSTNAME) }
    )

    Spacer(modifier = Modifier.height(SMALL_SPACING))

    AppOutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged {
                isLastnameCorrect =
                    if (!it.hasFocus && lastname.isNotBlank())
                        lastname.isFieldCorrect(
                            TextFieldType.LASTNAME
                        )
                    else true
            },
        value = lastname,
        isError = !isLastnameCorrect,
        label = stringResource(R.string.text_field_last_name_label),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        onValueChange = { value -> onValueChange(value, TextFieldType.LASTNAME) },
    )

    Spacer(modifier = Modifier.height(SMALL_SPACING))

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
        isError = !isPasswordCorrect,
        label = stringResource(R.string.text_field_password_label),
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
                        confirmPassword == password
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
        onValueChange = { value -> onValueChange(value, TextFieldType.CONFIRM_PASSWORD) },
    )
}