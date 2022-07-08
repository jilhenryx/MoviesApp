package com.example.moviesapp.ui.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.moviesapp.R
import com.example.moviesapp.ui.composables.reusablecomposables.*
import com.example.moviesapp.ui.theme.MoviesAppTheme

@Composable
fun ConfirmEmailContent(
    modifier: Modifier = Modifier,
    timerText: String,
    onButtonClick: () -> Unit,
    onRetryClick: () -> Unit,
) {
    AppButton(
        type = AppButtonType.FILLED,
        title = stringResource(R.string.continue_button_text).uppercase(),
        onClick = onButtonClick
    )
    RetryEmailText(
        modifier = modifier,
        timerText = timerText,
        onRetryClicked = onRetryClick
    )
}

@Composable
fun ConfirmEmailScreen() {
    val email = "domain@hostname.com"
    AppSignUpLoginScaffold(
        headerIconRes = R.drawable.new_mail_icon,
        spacerHeight = spacerHeightLarge,

        headerTitle = stringResource(R.string.confirm_email_header_text),
        headerSubtitle = stringResource(
            R.string.confirm_email_header_subtitle_text,
            email
        ),
        content = {

            ConfirmEmailContent(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                timerText = "4:00",
                onButtonClick = {},
                onRetryClick = {}
            )
        }
    )

}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun ConfirmEmailScreenPreview() {
    MoviesAppTheme {
        ConfirmEmailScreen()
    }
}