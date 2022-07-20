package com.example.moviesapp.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.domain.usecases.LoginUser
import com.example.moviesapp.ui.composablehelpers.isFieldCorrect
import com.example.moviesapp.ui.reusablecomposables.TextFieldType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val stateHandler: LoginStateHandler,
    private val loginUser: LoginUser,
) : ViewModel() {

    internal val state = stateHandler.LoginStateHolder()

    internal fun onTextFieldValueChange(value: String, fieldType: TextFieldType) {
        stateHandler.onTextFieldValueChange(value, fieldType)

    }

    private fun areAllFieldsCorrect() =
        stateHandler.email.isFieldCorrect(TextFieldType.EMAIL)
                && stateHandler.password.isFieldCorrect(TextFieldType.PASSWORD)

    internal fun login(
        navigateToMain: () -> Unit,
        navigateToCheckEmail: (email: String) -> Unit
    ) {
        if (!areAllFieldsCorrect()) {
            stateHandler.handleInvalidSubmission()
        } else {
            viewModelScope.launch {
                loginUser(
                    LoginUser.Params(
                        email = stateHandler.email,
                        password = stateHandler.password
                    )
                ).collect { loginStatus ->
                    stateHandler.handleLoginStatus(
                        loginStatus, navigateToMain, navigateToCheckEmail
                    )
                }
            }
        }
    }
}