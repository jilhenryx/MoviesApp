package com.example.moviesapp.ui.signup

import androidx.compose.runtime.mutableStateOf
import com.example.moviesapp.core.AuthStatus
import com.example.moviesapp.core.Messages.EMPTY_FIELDS_MESSAGE
import com.example.moviesapp.ui.composablehelpers.SubtitleHeaderState
import com.example.moviesapp.ui.reusablecomposables.TextFieldType
import javax.inject.Inject

class SignUpStateHandler @Inject constructor() {
    private val firstnameState = mutableStateOf("")
    private val lastnameState = mutableStateOf("")
    private val emailState = mutableStateOf("")
    private val passwordState = mutableStateOf("")
    private val confirmPasswordState = mutableStateOf("")
    private val subtitleState = mutableStateOf(SubtitleHeaderState.default())
    private val isLoadingState = mutableStateOf(false)

    internal val firstname
        get() = firstnameState.value

    internal val lastname
        get() = lastnameState.value

    internal val email
        get() = emailState.value

    internal val password
        get() = passwordState.value

    internal val confirmPassword
        get() = confirmPasswordState.value

    internal fun handleInvalidSubmission() {
        subtitleState.value = SubtitleHeaderState(EMPTY_FIELDS_MESSAGE, true)
    }

    internal fun onTextFieldValueChange(value: String, fieldType: TextFieldType) {
        when (fieldType) {
            TextFieldType.FIRSTNAME -> firstnameState.value = value
            TextFieldType.LASTNAME -> lastnameState.value = value
            TextFieldType.EMAIL -> emailState.value = value
            TextFieldType.PASSWORD -> passwordState.value = value
            TextFieldType.CONFIRM_PASSWORD -> confirmPasswordState.value = value
        }
    }

    internal fun handleSignUpStatus(
        status: AuthStatus,
        navigateToCheckEmail: (email: String) -> Unit
    ) {
        when (status) {
            is AuthStatus.InProgress -> {
                subtitleState.value = SubtitleHeaderState.default()
                isLoadingState.value = true
            }
            is AuthStatus.Success -> {
                isLoadingState.value = false
                navigateToCheckEmail(email)
            }
            is AuthStatus.Failed -> {
                subtitleState.value = SubtitleHeaderState(status.errorMessage, true)
                isLoadingState.value = false
            }
        }

    }

    inner class SignUpStateHolder {
        internal val firstname
            get() = firstnameState.value

        internal val lastname
            get() = lastnameState.value

        internal val email
            get() = emailState.value

        internal val password
            get() = passwordState.value

        internal val confirmPassword
            get() = confirmPasswordState.value

        internal val subtitle
            get() = subtitleState.value

        internal val isLoading
            get() = isLoadingState.value
    }
}