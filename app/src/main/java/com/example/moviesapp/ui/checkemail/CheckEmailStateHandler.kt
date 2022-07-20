package com.example.moviesapp.ui.checkemail

import androidx.compose.runtime.mutableStateOf
import com.example.moviesapp.R
import com.example.moviesapp.core.AuthEmailType
import com.example.moviesapp.core.AuthStatus
import com.example.moviesapp.core.AuthStatusWithValue
import com.example.moviesapp.core.Messages
import com.example.moviesapp.main.navigation.AuthGraphCallerIds.LOGIN_SCREEN_ID
import com.example.moviesapp.main.navigation.AuthGraphCallerIds.SIGN_UP_SCREEN_ID
import com.example.moviesapp.ui.composablehelpers.SubtitleHeaderState
import javax.inject.Inject

class CheckEmailStateHandler @Inject constructor() {
    private val titleState = mutableStateOf(R.string.check_email_header_text)
    private val subtitleState = mutableStateOf(SubtitleHeaderState.default())
    private val isLoadingState = mutableStateOf(true)
    private val canRetryState = mutableStateOf(true)
    internal val emailState = mutableStateOf("")


    internal val canRetry
        get() = canRetryState.value

    internal fun handleInvalidDeepLink() {
        subtitleState.value = SubtitleHeaderState(Messages.DEFAULT_ERROR_MESSAGE, true)
        isLoadingState.value = false
    }

    internal fun handleVerificationStatus(
        authStatus: AuthStatusWithValue<Boolean>,
        navigateToLogin: () -> Unit
    ) {
        when (authStatus) {
            is AuthStatusWithValue.InProgress -> {
                subtitleState.value = SubtitleHeaderState.default()
                isLoadingState.value = true
            }
            is AuthStatusWithValue.Success -> {
                isLoadingState.value = false
                //Status Value == isEmailVerified
                if (authStatus.statusValue) navigateToLogin()
                else {
                    subtitleState.value = SubtitleHeaderState(
                        Messages.EMAIL_NOT_VERIFIED_MESSAGE,
                        true
                    )
                }
            }
            is AuthStatusWithValue.Failed -> {
                subtitleState.value = SubtitleHeaderState(Messages.DEFAULT_ERROR_MESSAGE, true)
                isLoadingState.value = false
            }
        }

    }

    internal fun handleSendEmailStatus(authStatus: AuthStatus) {
        when (authStatus) {
            is AuthStatus.InProgress -> {
                subtitleState.value = SubtitleHeaderState.default()
                isLoadingState.value = true
            }
            is AuthStatus.Success -> {
                subtitleState.value = SubtitleHeaderState.default()
                isLoadingState.value = false
                canRetryState.value = false
            }
            is AuthStatus.Failed -> {
                subtitleState.value = SubtitleHeaderState(authStatus.errorMessage, true)
                isLoadingState.value = false
                canRetryState.value = true
            }
        }
    }

    internal fun handleHeaderDetails(emailType: AuthEmailType, callerId: String) {
        when (emailType) {
            AuthEmailType.VERIFY_EMAIL -> {
                if (callerId == LOGIN_SCREEN_ID) {
                    titleState.value = R.string.yet_to_confirm_email_header_text
                } else if (callerId == SIGN_UP_SCREEN_ID) {
                    titleState.value = R.string.confirm_email_header_text
                }
            }
            AuthEmailType.RESET_PASSWORD -> {}
        }
    }

    internal fun handleTimerFinished() {
        canRetryState.value = true
    }

    inner class StateHolder {
        internal val title
            get() = titleState.value
        internal val isLoading
            get() = isLoadingState.value
        internal val canRetry
            get() = canRetryState.value
        internal val email
            get() = emailState.value
        internal val subtitle
            get() = subtitleState.value
    }


}

