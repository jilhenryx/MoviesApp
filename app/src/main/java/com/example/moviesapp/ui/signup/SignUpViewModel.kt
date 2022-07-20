package com.example.moviesapp.ui.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.domain.usecases.SignUpUser
import com.example.moviesapp.ui.composablehelpers.isFieldCorrect
import com.example.moviesapp.ui.reusablecomposables.TextFieldType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val stateHandler: SignUpStateHandler,
    private val signUpUser: SignUpUser
) : ViewModel() {

    val state = stateHandler.SignUpStateHolder()

    internal fun onTextFieldValueChange(value: String, fieldType: TextFieldType) =
        stateHandler.onTextFieldValueChange(value = value, fieldType = fieldType)

    private fun areAllFieldsCorrect(): Boolean =
        stateHandler.firstname.isFieldCorrect(TextFieldType.LASTNAME)
                && stateHandler.lastname.isFieldCorrect(TextFieldType.LASTNAME)
                && stateHandler.email.isFieldCorrect(TextFieldType.EMAIL)
                && stateHandler.password.isFieldCorrect(TextFieldType.PASSWORD)
                && stateHandler.confirmPassword == stateHandler.password


    internal fun signUp(navigateToCheckEmail: (email: String) -> Unit) {
        if (!areAllFieldsCorrect()) {
            stateHandler.handleInvalidSubmission()
        } else {
            viewModelScope.launch {
                signUpUser(
                    SignUpUser.Params(
                        firstname = stateHandler.firstname,
                        lastname = stateHandler.lastname,
                        password = stateHandler.password,
                        email = stateHandler.email,
                    )
                ).collect {
                    stateHandler.handleSignUpStatus(it, navigateToCheckEmail)
                }
            }
        }
    }
}