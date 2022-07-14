package com.example.moviesapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.network.AuthHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val authHandler: AuthHandler) : ViewModel() {
    internal suspend fun login(email: String, password: String) =
        authHandler.login(email, password).stateIn(viewModelScope)

    internal suspend fun createUser(
        email: String,
        password: String,
        firstname: String,
        lastname: String
    ) = authHandler.createUser(email, password, firstname, lastname).stateIn(viewModelScope)

    internal fun sendVerificationEmail() {
        authHandler.sendVerificationEmail()
    }

    internal suspend fun sendPasswordResetEmail(email: String) =
        authHandler.sendPasswordResetEmail(email).stateIn(viewModelScope)

    internal fun isUserEmailVerified() = authHandler.checkEmailVerification()
}