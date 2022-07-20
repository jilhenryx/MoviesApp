package com.example.moviesapp.ui.checkemail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.core.AuthEmailType
import com.example.moviesapp.domain.usecases.ConfirmEmailVerification
import com.example.moviesapp.domain.usecases.LaunchedWithAuthDeepLink
import com.example.moviesapp.domain.usecases.SendAuthEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckViewModel @Inject constructor(
    private val stateHandler: CheckEmailStateHandler,
    private val confirmEmail: ConfirmEmailVerification,
    private val handleDeepLink: LaunchedWithAuthDeepLink,
    private val sendAuthEmail: SendAuthEmail,
) : ViewModel() {
    internal val state = stateHandler.StateHolder()


    //Not Ideal - Should be removed in the future
    internal fun setEmail(email: String) {
        stateHandler.emailState.value = email
    }

    private fun confirmEmailVerification(navigateToLogin: () -> Unit) {
        viewModelScope.launch {
            confirmEmail(null).collect { authStatus ->
                stateHandler.handleVerificationStatus(authStatus, navigateToLogin)
            }
        }
    }

    internal fun sendAuthEmail(emailType: AuthEmailType) {
        if (stateHandler.canRetry) {
            viewModelScope.launch { sendEmail(emailType) }
        }
    }

    private suspend fun sendEmail(emailType: AuthEmailType) {
        sendAuthEmail(
            SendAuthEmail.Params(
                email = stateHandler.emailState.value,
                type = emailType
            )
        ).collect {
            stateHandler.handleSendEmailStatus(it)
        }
    }

    internal fun initHeader(emailType: AuthEmailType, callerId: String) =
        stateHandler.handleHeaderDetails(emailType = emailType, callerId = callerId)

    internal fun handleDeepLink(
        deepLink: String,
        navigateToLogin: () -> Unit,
        navigateToResetPassword: (resetCode: String) -> Unit
    ) {
        viewModelScope.launch {
            handleDeepLink(LaunchedWithAuthDeepLink.Param(deepLink)).collect { deepLinkData ->
                deepLinkData?.let { data ->
                    when (data.mode) {
                        AuthEmailType.VERIFY_EMAIL -> {
                            setEmail(data.data)
                            confirmEmailVerification(navigateToLogin)
                        }
                        AuthEmailType.RESET_PASSWORD -> {
                            navigateToResetPassword(data.data)
                        }
                    }
                } ?: run {
                    stateHandler.handleInvalidDeepLink()
                }

            }
        }
    }

    internal fun onTimerFinish() {
        stateHandler.handleTimerFinished()
    }

}

