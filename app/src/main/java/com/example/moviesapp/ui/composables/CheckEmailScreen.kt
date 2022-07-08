package com.example.moviesapp.ui.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.moviesapp.R
import com.example.moviesapp.ui.composables.reusablecomposables.AppSignUpLoginScaffold
import com.example.moviesapp.ui.composables.reusablecomposables.RetryEmailText
import com.example.moviesapp.ui.theme.MoviesAppTheme

@Composable
fun CheckEmailScreen() {
    val email = "domain@hostname.com"
    AppSignUpLoginScaffold(
        headerIconRes = R.drawable.new_mail_icon,
        spacerHeight = spacerHeightLarge,
        headerTitle = stringResource(R.string.check_email_header_text),
        headerSubtitle = stringResource(
            R.string.check_email_header_subtitle_text,
            email
        ),
        content = {
            RetryEmailText(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                timerText = "4:00",
                onRetryClicked = {}
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
        CheckEmailScreen()
    }
}