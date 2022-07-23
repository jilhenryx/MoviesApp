package com.example.moviesapp.ui.login

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.example.moviesapp.core.AuthStatusWithValue
import com.example.moviesapp.core.Messages.DEFAULT_ERROR_MESSAGE
import com.example.moviesapp.core.Messages.EMPTY_FIELDS_MESSAGE
import com.example.moviesapp.ui.composablehelpers.SubtitleHeaderState
import com.example.moviesapp.ui.reusablecomposables.TextFieldType
import javax.inject.Inject

class LoginStateHandler  @Inject constructor() {
    private val emailState = mutableStateOf("")
    private val passwordState = mutableStateOf("")
    private val subtitleState = mutableStateOf(SubtitleHeaderState("", false))
    private val isLoadingState = mutableStateOf(false)


    internal val email
        get() = emailState.value
    internal val password
        get() = passwordState.value


    companion object {
        private const val TAG = "LoginStateHandler"
    }

    internal fun handleInvalidSubmission(errorMessage:String) {
        subtitleState.value = SubtitleHeaderState(errorMessage  , true)
    }

    internal fun onTextFieldValueChange(value: String, fieldType: TextFieldType) {
        when (fieldType) {
            TextFieldType.EMAIL -> emailState.value = value
            TextFieldType.PASSWORD -> passwordState.value = value
            else -> {
                Log.d(TAG, "LoginScreen: Type Not Recognized Here")
            }
        }
    }

    internal fun handleLoginStatus(
        loginStatus: AuthStatusWithValue<Boolean>,
        navigateToMain: () -> Unit,
        navigateToCheckEmail: (email: String) -> Unit
    ) {
        when (loginStatus) {
            is AuthStatusWithValue.InProgress -> {
                subtitleState.value = SubtitleHeaderState.default()
                isLoadingState.value = true
            }
            is AuthStatusWithValue.Success -> {
                isLoadingState.value = false
                if (loginStatus.statusValue) navigateToMain()
                else navigateToCheckEmail(email)
            }
            is AuthStatusWithValue.Failed -> {
                subtitleState.value = SubtitleHeaderState(
                    loginStatus.errorMessage.ifBlank { DEFAULT_ERROR_MESSAGE },
                    true
                )
                isLoadingState.value = false
            }
        }
    }

    inner class LoginStateHolder() {
        internal val email
            get() = emailState.value
        internal val password
            get() = passwordState.value
        internal val subtitle
            get() = subtitleState.value
        internal val isLoading
            get() = isLoadingState.value
    }
}