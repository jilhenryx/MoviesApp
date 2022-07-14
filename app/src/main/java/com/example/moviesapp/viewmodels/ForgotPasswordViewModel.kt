package com.example.moviesapp.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.focus.FocusState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.network.AppAuthResult
import com.example.moviesapp.network.AuthHandler
import com.example.moviesapp.ui.constants.DEFAULT_EMPTY_FIELDS_MESSAGE
import com.example.moviesapp.ui.constants.DEFAULT_ERROR_MESSAGE
import com.example.moviesapp.ui.constants.TextFieldType
import com.example.moviesapp.ui.constants.isFieldCorrect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val authHandler: AuthHandler
) : ViewModel() {
    private var isNewViewModel = true
    private val emailState = mutableStateOf("")
    private val isEmailCorrectState = mutableStateOf(true)
    private val headerSubtitleState = mutableStateOf("")
    private val isSubtitleErrorState = mutableStateOf(false)
    private val isLoadingState = mutableStateOf(false)

    internal val email
        get() = emailState.value
    internal val isEmailCorrect
        get() = isEmailCorrectState.value
    internal val headerSubtitle
        get() = headerSubtitleState.value
    internal val isSubtitleError
        get() = isSubtitleErrorState.value
    internal val isLoading
        get() = isLoadingState.value

    internal fun onEmailReceived(email: String) {
        emailState.value = email
    }

    internal fun onTextFieldValueChanged(value: String) {
        emailState.value = value
    }

    internal fun onTextFieldFocusChanged(focusState: FocusState) {
        if (isNewViewModel) {
            isNewViewModel = false
            return
        }
        isEmailCorrectState.value =
            if (focusState.hasFocus) true else isFieldCorrect(email, TextFieldType.EMAIL)
    }

    internal fun submit(
        onComplete: () -> Unit
    ) {
        if (!isFieldCorrect(email, TextFieldType.EMAIL)) {
            headerSubtitleState.value = DEFAULT_EMPTY_FIELDS_MESSAGE
            isSubtitleErrorState.value = true
        } else {
            headerSubtitleState.value = ""
            isSubtitleErrorState.value = false
            viewModelScope.launch {
                authHandler.sendPasswordResetEmail(email)
                    .collect { authResult ->
                        when (authResult.state) {
                            AppAuthResult.ResultState.LOADING -> {
                                isLoadingState.value = true
                            }
                            AppAuthResult.ResultState.SUCCESS -> {
                                isLoadingState.value = false
                                onComplete()
                            }
                            AppAuthResult.ResultState.ERROR -> {
                                headerSubtitleState.value =
                                    authResult.errorMessage ?: DEFAULT_ERROR_MESSAGE
                                isSubtitleErrorState.value = true
                                isLoadingState.value = false
                            }
                        }
                    }
            }
        }
    }

}