package com.example.moviesapp.data

import com.example.moviesapp.core.AuthStatus
import com.example.moviesapp.network.authentication.AppAuthResult
import com.example.moviesapp.network.authentication.AuthHandler
import com.example.moviesapp.network.authentication.AuthResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ResetPasswordRepository @Inject constructor(
    private val authHandler: AuthHandler
) : AuthDTO<AuthResult, AuthStatus> {

    internal suspend fun resetPassword(resetCode: String, password: String): Flow<AuthStatus> =
        authHandler.resetPassword(resetCode = resetCode, password = password)
            .map(::authResultToAuthStatusMapper)

    override suspend fun authResultToAuthStatusMapper(authResult: AuthResult): AuthStatus =
        when (authResult.state) {
            AppAuthResult.ResultState.LOADING -> AuthStatus.InProgress
            AppAuthResult.ResultState.SUCCESS -> AuthStatus.Success
            AppAuthResult.ResultState.ERROR -> AuthStatus.Failed(authResult.errorMessage)
        }
}