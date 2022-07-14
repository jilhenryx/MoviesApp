package com.example.moviesapp.ui.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.moviesapp.R
import com.example.moviesapp.ui.composables.reusablecomposables.AppButton
import com.example.moviesapp.ui.composables.reusablecomposables.AppButtonType
import com.example.moviesapp.ui.composables.reusablecomposables.AppLoginFlowScaffold
import com.example.moviesapp.ui.composables.reusablecomposables.AppOutlinedTextField
import com.example.moviesapp.ui.constants.LARGE_SPACING
import com.example.moviesapp.ui.theme.MoviesAppTheme
import com.example.moviesapp.viewmodels.ForgotPasswordViewModel


@Composable
fun ForgotPasswordScreen(
    viewModel: ForgotPasswordViewModel = viewModel(),
    email: String,
    navigate: (email: String) -> Unit
) {
    viewModel.onEmailReceived(email)
    AppLoginFlowScaffold(
        headerIconRes = R.drawable.question_icon,
        headerTitle = stringResource(R.string.forgot_password_header_text),
        headerSubtitle = viewModel.headerSubtitle.ifBlank { stringResource(R.string.forgot_password_header_subtitle_text) },
        isSubtitleError = viewModel.isSubtitleError,
        isContentLoading = viewModel.isLoading,
        contentSpacing = LARGE_SPACING
    ) {
        AppOutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged(viewModel::onTextFieldFocusChanged),
            value = viewModel.email,
            label = stringResource(R.string.text_field_email_label),
            isError = !viewModel.isEmailCorrect,
            onValueChange = viewModel::onTextFieldValueChanged,
            keyboardType = KeyboardType.Email
        )
        AppButton(
            type = AppButtonType.FILLED,
            title = stringResource(R.string.continue_button_text).uppercase(),
            onClick = {
                viewModel.submit() {
                    navigate(viewModel.email)
                }
            }
        )
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