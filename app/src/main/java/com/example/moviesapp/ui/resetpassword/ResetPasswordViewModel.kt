package com.example.moviesapp.ui.resetpassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.domain.usecases.ResetPassword
import com.example.moviesapp.domain.usecases.VerifyResetCode
import com.example.moviesapp.ui.composablehelpers.isFieldCorrect
import com.example.moviesapp.ui.reusablecomposables.TextFieldType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val stateHandler: ResetPasswordStateHandler,
    private val verifyResetCode: VerifyResetCode,
    private val resetPassword: ResetPassword
) : ViewModel() {

    internal fun state() = stateHandler.StateHolder()

    internal fun onTextFieldValueChange(value: String, fieldType: TextFieldType) =
        stateHandler.onTextFieldValueChange(value = value, fieldType = fieldType)

    internal fun verifyResetCode(resetCode: String, navigateToLogin: () -> Unit) {
        viewModelScope.launch {
            verifyResetCode(VerifyResetCode.Params(resetCode = resetCode)).collect {
                stateHandler.handleResetCodeVerification(authStatus = it, navigateToLogin)
            }
        }
    }

    internal fun changePassword(resetCode: String, navigateToLogin: () -> Unit) {
        if (!areAllFieldsCorrect()) {
            stateHandler.invalidSubmissionState()
        } else {
            viewModelScope.launch {
                resetPassword(
                    ResetPassword.Params(resetCode = resetCode, password = stateHandler.password)
                ).collect {
                    stateHandler.handlePasswordReset(it, navigateToLogin)
                }
            }
        }
    }

    private fun areAllFieldsCorrect() =
        stateHandler.password.isFieldCorrect(TextFieldType.PASSWORD)
                && stateHandler.confirmPassword == stateHandler.password


}