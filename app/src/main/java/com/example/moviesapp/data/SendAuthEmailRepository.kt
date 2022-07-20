package com.example.moviesapp.data

import com.example.moviesapp.core.AuthEmailType
import com.example.moviesapp.core.AuthStatus
import com.example.moviesapp.network.authentication.AppAuthResult
import com.example.moviesapp.network.authentication.AuthHandler
import com.example.moviesapp.network.authentication.AuthResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SendAuthEmailRepository @Inject constructor(
    private val authHandler: AuthHandler,
) : AuthDTO<AuthResult, AuthStatus> {

    internal suspend fun sendEmail(email: String, type: AuthEmailType): Flow<AuthStatus> =
        when (type) {
            AuthEmailType.VERIFY_EMAIL -> sendVerificationEmail(email)
            AuthEmailType.RESET_PASSWORD -> sendResetVerificationEmail(email)
        }


    private suspend fun sendVerificationEmail(email: String) =
        authHandler.sendVerificationEmail(email).map(::authResultToAuthStatusMapper)

    private suspend fun sendResetVerificationEmail(email: String) =
        authHandler.sendPasswordResetEmail(email).map(::authResultToAuthStatusMapper)


    override suspend fun authResultToAuthStatusMapper(authResult: AuthResult): AuthStatus =
        when (authResult.state) {
            AppAuthResult.ResultState.LOADING -> AuthStatus.InProgress
            AppAuthResult.ResultState.SUCCESS -> AuthStatus.Success
            AppAuthResult.ResultState.ERROR -> AuthStatus.Failed(authResult.errorMessage)
        }
}