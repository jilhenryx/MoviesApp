package com.example.moviesapp.ui.forgotpassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.domain.usecases.CheckIfMailExist
import com.example.moviesapp.ui.composablehelpers.isFieldCorrect
import com.example.moviesapp.ui.reusablecomposables.TextFieldType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val stateHandler: ForgotPasswordStateHandler,
    private val checkIfMailExist: CheckIfMailExist,
) : ViewModel() {

    val state = stateHandler.StateHolder()

    internal fun onTextFieldValueChanged(email: String) =
        stateHandler.onTextFieldValueChanged(email = email)

    internal fun submit(
        onComplete: (email: String) -> Unit
    ) {
        if (!stateHandler.email.isFieldCorrect(TextFieldType.EMAIL)) {
            stateHandler.handleInvalidSubmission()
        } else {
            viewModelScope.launch {
                checkIfMailExist(CheckIfMailExist.Params(stateHandler.email)).collect {
                    stateHandler.handleAuthStatus(it, onComplete)
                }
            }
        }
    }

}