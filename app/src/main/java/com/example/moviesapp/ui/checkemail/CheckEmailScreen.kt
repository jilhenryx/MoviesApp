package com.example.moviesapp.ui.checkemail

import com.example.moviesapp.ui.reusablecomposables.AuthAppBar
import android.os.CountDownTimer
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.moviesapp.R
import com.example.moviesapp.core.AuthEmailType
import com.example.moviesapp.ui.composablehelpers.ComposeConstants.LARGE_SPACING
import com.example.moviesapp.ui.composablehelpers.ComposeConstants.MEDIUM_SPACING
import com.example.moviesapp.ui.reusablecomposables.*
import kotlinx.coroutines.cancel

@Composable
fun CheckEmailScreen(
    email: String,
    deepLink: String,
    emailType: AuthEmailType,
    callerId: String,
    navigateToLogin: () -> Unit,
    navigateToResetPassword: (resetCode: String) -> Unit,
    navigateUp: () -> Unit
) {
    CheckEmailScreen(
        viewModel = hiltViewModel(),
        email = email,
        deepLink = deepLink,
        emailType = emailType,
        callerId = callerId,
        navigateToLogin = navigateToLogin,
        navigateToResetPassword = navigateToResetPassword,
        navigateUp = navigateUp
    )
}

@Composable
private fun CheckEmailScreen(
    viewModel: CheckViewModel,
    modifier: Modifier = Modifier,
    email: String,
    deepLink: String,
    emailType: AuthEmailType,
    callerId: String,
    navigateToLogin: () -> Unit,
    navigateToResetPassword: (resetCode: String) -> Unit,
    navigateUp: () -> Unit
) {
    viewModel.setEmail(email)

    CheckEmailScreen(
        modifier = modifier,
        state = viewModel.state,
        deepLink = deepLink,
        timerFinished = { viewModel.onTimerFinish() },
        handleDeepLink = { link ->
            viewModel.handleDeepLink(
                link,
                navigateToLogin,
                navigateToResetPassword
            )
        },
        initializeHeader = { viewModel.initHeader(emailType, callerId) },
        sendAuthEmail = { viewModel.sendAuthEmail(emailType) },
        navigateUp = navigateUp
    )
}

@Composable
private fun CheckEmailScreen(
    modifier: Modifier = Modifier,
    state: CheckEmailStateHandler.StateHolder,
    deepLink: String,
    timerFinished: () -> Unit,
    handleDeepLink: (deepLink: String) -> Unit,
    initializeHeader: () -> Unit,
    sendAuthEmail: () -> Unit,
    navigateUp: () -> Unit
) {
    val timerText = if (!state.canRetry) {
        produceTimerText(timerFinished)
    } else null

    LaunchedEffect(deepLink) {
        initializeHeader()
        if (deepLink.isNotBlank()) {
            handleDeepLink(deepLink)
        } else {
            sendAuthEmail()
        }
    }

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
                AuthHeaderIcon(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    iconRes = R.drawable.new_mail_icon
                )

                Spacer(modifier = Modifier.height(MEDIUM_SPACING))

                AuthScreensHeader(
                    title = stringResource(state.title),
                    subtitle = state.subtitle.text.ifBlank {
                        stringResource(
                            R.string.check_email_header_subtitle_default_text,
                            state.email
                        )
                    },
                    isSubtitleError = state.subtitle.isError
                )

                Spacer(modifier = Modifier.height(LARGE_SPACING))

                RetryEmailText(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    timerText = timerText?.value ?: "",
                    linkColor = if (state.canRetry) null else Color.Gray,
                    onRetryClicked = sendAuthEmail
                )
            }
        }

        LoadingScreen(modifier = Modifier, show = state.isLoading)
    }
}

@Composable
fun produceTimerText(
    onTimerDone: () -> Unit
): State<String> {
    val initialValue = "4:00"
    return produceState(initialValue = initialValue) {

        val timer = object : CountDownTimer(240000, 1000) {
            override fun onTick(millisUntilFinish: Long) {
                val secondsLeft = millisUntilFinish / 1000
                value =
                    "${formatTimeDigits(secondsLeft / 60)}:${formatTimeDigits(secondsLeft % 60)}"
            }

            override fun onFinish() {
                value = ""
                onTimerDone()
                this@produceState.cancel()
            }
        }.start()

        awaitDispose { timer.cancel() }
    }
}

private fun formatTimeDigits(digits: Long): String =
    "0$digits".let { str -> str.substring(startIndex = str.length - 2) }