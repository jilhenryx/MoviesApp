package com.example.moviesapp.ui.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.example.moviesapp.R
import com.example.moviesapp.ui.composables.reusablecomposables.*
import com.example.moviesapp.ui.theme.MoviesAppTheme


@Composable
fun ForgotPasswordScreen() {
    AppSignUpLoginScaffold(
        headerIconRes = R.drawable.question_icon,
        spacerHeight = spacerHeightLarge,
        headerTitle = stringResource(R.string.forgot_password_header_text),
        headerSubtitle = stringResource(R.string.forgot_password_header_subtitle_text),
        content = {
            AppOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = "",
                label = stringResource(R.string.text_field_email_label),
                onValueChange = {},
                keyboardType = KeyboardType.Email
            )
            AppButton(
                type = AppButtonType.FILLED,
                title = stringResource(R.string.continue_button_text).uppercase(),
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
fun ForgotPasswordScreenPreview() {
    MoviesAppTheme {
        ForgotPasswordScreen()
    }
}