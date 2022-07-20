package com.example.moviesapp.ui.forgotpassword

import androidx.compose.runtime.mutableStateOf
import com.example.moviesapp.core.AuthStatusWithValue
import com.example.moviesapp.core.Messages.EMAIL_DOES_NOT_EXIST_MESSAGE
import com.example.moviesapp.core.Messages.EMPTY_FIELDS_MESSAGE
import com.example.moviesapp.ui.composablehelpers.SubtitleHeaderState
import javax.inject.Inject

class ForgotPasswordStateHandler @Inject constructor() {

    private val emailState = mutableStateOf("")
    private val subtitleState = mutableStateOf(SubtitleHeaderState.default())
    private val isLoadingState = mutableStateOf(false)

    internal val email
        get() = emailState.value

    internal fun onTextFieldValueChanged(email: String) {
        emailState.value = email
    }

    internal fun handleInvalidSubmission() {
        subtitleState.value = SubtitleHeaderState(EMPTY_FIELDS_MESSAGE, true)
    }

    internal fun handleAuthStatus(
        authStatus: AuthStatusWithValue<Boolean>,
        navigateToCheckEmail: (email: String) -> Unit
    ) {
        when (authStatus) {
            is AuthStatusWithValue.InProgress -> {
                subtitleState.value = SubtitleHeaderState.default()
                isLoadingState.value = true
            }
            is AuthStatusWithValue.Success -> {
                isLoadingState.value = false
                //Status Value = EmailExist
                if (authStatus.statusValue) {
                    navigateToCheckEmail(email)
                } else {
                    subtitleState.value =
                        SubtitleHeaderState(EMAIL_DOES_NOT_EXIST_MESSAGE, true)
                }
            }
            is AuthStatusWithValue.Failed -> {
                subtitleState.value = SubtitleHeaderState(authStatus.errorMessage, true)
                isLoadingState.value = false
            }
        }
    }

    inner class StateHolder {
        internal val email
            get() = emailState.value
        internal val subtitle
            get() = subtitleState.value
        internal val isLoading
            get() = isLoadingState.value
    }
}