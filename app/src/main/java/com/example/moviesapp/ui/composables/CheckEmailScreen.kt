package com.example.moviesapp.ui.composables

import android.os.CountDownTimer
import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.moviesapp.R
import com.example.moviesapp.network.AuthHandler
import com.example.moviesapp.ui.composables.reusablecomposables.AppLoginFlowScaffold
import com.example.moviesapp.ui.composables.reusablecomposables.RetryEmailText
import com.example.moviesapp.ui.constants.LARGE_SPACING
import com.example.moviesapp.ui.theme.MoviesAppTheme
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

private const val TAG = "CheckEmailScreen"

enum class CheckEmailType {
    NONE,
    VERIFY_EMAIL,
    RESET_PASSWORD
}

@Composable
fun CheckEmailScreen(
    email: String,
    authHandler: AuthHandler,
    checkEmailType: CheckEmailType,
    @StringRes titleStringId: Int?,
    @StringRes subtitleStringId: Int?
) {
    val coroutineScope = rememberCoroutineScope()
    var startTimer by rememberSaveable { mutableStateOf(true) }
    val timerText = if (startTimer) {
        produceTimerText()
    } else null
    if (timerText != null && timerText.value.isBlank()) {
        startTimer = false
    }
    val canRetry by remember(startTimer) {
        derivedStateOf { timerText == null || timerText.value.isBlank() }
    }

    AppLoginFlowScaffold(
        headerIconRes = R.drawable.new_mail_icon,
        headerTitle = stringResource(titleStringId ?: R.string.check_email_header_text),
        headerSubtitle = stringResource(
            subtitleStringId ?: R.string.check_email_header_subtitle_default_text,
            email
        ),
        contentSpacing = LARGE_SPACING,
        content = {
            RetryEmailText(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                timerText = timerText?.value ?: "",
                linkColor = if (canRetry) null else Color.Gray,
                onRetryClicked = {
                    if (canRetry) {
                        when (checkEmailType) {
                            CheckEmailType.VERIFY_EMAIL -> {
                                authHandler.sendVerificationEmail()
                            }
                            CheckEmailType.RESET_PASSWORD -> {
                                coroutineScope.launch {
                                    authHandler.sendPasswordResetEmail(email)
                                }
                            }
                            CheckEmailType.NONE -> {
                                Log.d(TAG, "CheckEmailScreen: No Check Type Received")
                            }
                        }
                        startTimer = true
                    }
                }
            )
        }
    )
}

@Composable
private fun produceTimerText(): State<String> {
    val initialValue = "4:00"
    return produceState(initialValue = initialValue) {

        val timer = object : CountDownTimer(240000, 1000) {
            override fun onTick(millisUntilFinish: Long) {
                val secondsLeft = millisUntilFinish / 1000
                value =
                    "${formatTimeDigits(secondsLeft / 60)}:${formatTimeDigits(secondsLeft % 60)}"
            }

            override fun onFinish() {
                Log.d(TAG, "onFinish: ")
                value = ""
                this@produceState.cancel()
            }
        }.start()

        awaitDispose { timer.cancel() }
    }
}

private fun formatTimeDigits(digits: Long): String =
    "0$digits".let { str -> str.substring(startIndex = str.length - 2) }


@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun CheckEmailScreenPreview() {
    MoviesAppTheme {
//        CheckEmailScreen(
//            email = "domain@hostname.com",
//          titleId =  R.string.check_email_header_text,
//            R.string.check_email_header_subtitle_default_text
//        )
    }
}