package com.example.moviesapp.ui.composables

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.moviesapp.R
import com.example.moviesapp.ui.composables.reusablecomposables.AppLoginFlowScaffold
import com.example.moviesapp.ui.composables.reusablecomposables.RetryEmailText
import com.example.moviesapp.ui.theme.MoviesAppTheme

@Composable
fun CheckEmailScreen(
    email: String,
    @StringRes titleStringId: Int?,
    @StringRes subtitleStringId: Int?
) {
    AppLoginFlowScaffold(
        headerIconRes = R.drawable.new_mail_icon,
        headerTitle = stringResource(titleStringId ?: R.string.check_email_header_text),
        headerSubtitle = stringResource(
            subtitleStringId ?: R.string.check_email_header_subtitle_default_text,
            email
        ),
        content = {
            RetryEmailText(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                timerText = "4:00",
                onRetryClicked = {
                    /*TODO Remove Navigation to Reset Password Screen and
                       Implement right logic */

                }
            )
        }
    )
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun CheckEmailScreenPreview() {
    MoviesAppTheme {
        CheckEmailScreen(
            email = "domain@hostname.com",
            R.string.check_email_header_text,
            R.string.check_email_header_subtitle_default_text
        )
    }
}