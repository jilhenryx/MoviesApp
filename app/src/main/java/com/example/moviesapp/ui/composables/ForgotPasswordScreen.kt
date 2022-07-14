package com.example.moviesapp.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.runtime.internal.composableLambda
import androidx.compose.runtime.internal.composableLambdaInstance
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.moviesapp.R
import com.example.moviesapp.network.AppAuthResult
import com.example.moviesapp.ui.composables.reusablecomposables.AppButton
import com.example.moviesapp.ui.composables.reusablecomposables.AppButtonType
import com.example.moviesapp.ui.composables.reusablecomposables.AppLoginFlowScaffold
import com.example.moviesapp.ui.composables.reusablecomposables.AppOutlinedTextField
import com.example.moviesapp.ui.constants.*
import com.example.moviesapp.ui.theme.MoviesAppTheme
import com.example.moviesapp.viewmodels.AuthViewModel
import kotlinx.coroutines.launch


@Composable
fun ForgotPasswordScreen(
    viewModel: AuthViewModel = viewModel(),
    email: String,
    navigate: (email: String) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var typedEmail: String by rememberSaveable { mutableStateOf(email) }
    var isEmailCorrect by rememberSaveable { mutableStateOf(true) }
    var headerSubtitle by rememberSaveable { mutableStateOf("") }
    var headerSubTitleColor by rememberSaveable { mutableStateOf<Color?>(null) }
    var isLoading by rememberSaveable { mutableStateOf(false) }

    Box {
        AppLoginFlowScaffold(
            headerIconRes = R.drawable.question_icon,
            headerTitle = stringResource(R.string.forgot_password_header_text),
            headerSubtitle = headerSubtitle.ifBlank { stringResource(R.string.forgot_password_header_subtitle_text) },
            headerSubtitleColor = headerSubTitleColor,
            contentSpacing = LARGE_SPACING
        ) {
            AppOutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged {
                        isEmailCorrect =
                            if (!it.hasFocus && typedEmail.isNotBlank()) isFieldCorrect(
                                typedEmail,
                                TextFieldType.EMAIL
                            ) else true
                    },
                value = typedEmail,
                label = stringResource(R.string.text_field_email_label),
                isError = !isEmailCorrect,
                onValueChange = { value -> typedEmail = value },
                keyboardType = KeyboardType.Email
            )
            AppButton(
                type = AppButtonType.FILLED,
                title = stringResource(R.string.continue_button_text).uppercase()
            ) {
                if (!isFieldCorrect(typedEmail, TextFieldType.EMAIL)) {
                    headerSubtitle =
                        DEFAULT_EMPTY_FIELDS_MESSAGE
                    headerSubTitleColor = Color.Red
                } else {
                    headerSubtitle = ""
                    headerSubTitleColor = null
                    coroutineScope.launch {
                        viewModel.sendPasswordResetEmail(typedEmail).collect { authResult ->
                            when (authResult.state) {
                                AppAuthResult.ResultState.LOADING -> {
                                    isLoading = true
                                }
                                AppAuthResult.ResultState.SUCCESS -> {
                                    isLoading = false
                                    navigate(email)
                                }
                                AppAuthResult.ResultState.ERROR -> {
                                    headerSubtitle =
                                        authResult.errorMessage ?: DEFAULT_ERROR_MESSAGE
                                    headerSubTitleColor = Color.Red
                                    isLoading = false
                                }
                            }
                        }
                    }
                }
            }
        }
        if (isLoading) LoadingScreen()
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun ForgotPasswordScreenPreview() {
    MoviesAppTheme {
        ForgotPasswordScreen(
            email = "domain@hostname.com",
            navigate = {}
        )
    }
}