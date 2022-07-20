package com.example.moviesapp.ui.resetpassword

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.example.moviesapp.core.AuthStatus
import com.example.moviesapp.core.AuthStatusWithValue
import com.example.moviesapp.core.Messages.EMPTY_FIELDS_MESSAGE
import com.example.moviesapp.ui.composablehelpers.SubtitleHeaderState
import com.example.moviesapp.ui.reusablecomposables.TextFieldType
import javax.inject.Inject

class ResetPasswordStateHandler @Inject constructor() {

    private val emailState = mutableStateOf("")
    private val passwordState = mutableStateOf("")
    private val confirmPasswordState = mutableStateOf("")
    private val subtitleState = mutableStateOf(SubtitleHeaderState.default())
    private val isLoadingState = mutableStateOf(false)

    internal val password
        get() = passwordState.value

    internal val confirmPassword
        get() = confirmPasswordState.value

    companion object {
        private const val TAG = "ResetPasswordStateHandler"
    }

    internal fun onTextFieldValueChange(value: String, fieldType: TextFieldType) {
        when (fieldType) {
            TextFieldType.PASSWORD -> passwordState.value = value
            TextFieldType.CONFIRM_PASSWORD -> confirmPasswordState.value = value
            else -> Log.d(TAG, "ResetPasswordScreen: OnValueChange Type is unrecognised")
        }
    }

    internal fun handlePasswordReset(authStatus: AuthStatus, navigateToLogin: () -> Unit) {
        when (authStatus) {
            is AuthStatus.InProgress -> {
                subtitleState.value = SubtitleHeaderState.default()
                isLoadingState.value = true
            }
            is AuthStatus.Success -> {
                isLoadingState.value = false
                navigateToLogin()
            }
            is AuthStatus.Failed -> {
                subtitleState.value = SubtitleHeaderState(authStatus.errorMessage, true)
                isLoadingState.value = false
            }
        }
    }

    internal fun handleResetCodeVerification(
        authStatus: AuthStatusWithValue<String>,
        navigateToLogin: () -> Unit
    ) {
        when (authStatus) {
            is AuthStatusWithValue.InProgress -> {
                subtitleState.value = SubtitleHeaderState.default()
                isLoadingState.value = true
            }
            is AuthStatusWithValue.Success -> {
                //statusValue = email from server
                emailState.value = authStatus.statusValue
                isLoadingState.value = false
            }
            is AuthStatusWithValue.Failed -> {
                isLoadingState.value = false
                navigateToLogin()
            }
        }
    }

    internal fun invalidSubmissionState() {
        subtitleState.value = SubtitleHeaderState(EMPTY_FIELDS_MESSAGE, true)
    }

    inner class StateHolder() {
        internal val email
            get() = emailState.value
        internal val subtitle
            get() = subtitleState.value
        internal val isLoading
            get() = isLoadingState.value
        internal val password
            get() = passwordState.value
        internal val confirmPassword
            get() = confirmPasswordState.value
    }
}