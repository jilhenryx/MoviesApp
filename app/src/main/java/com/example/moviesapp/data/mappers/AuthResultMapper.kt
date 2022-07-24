package com.example.moviesapp.data.mappers

import com.example.moviesapp.core.AuthStatus
import com.example.moviesapp.data.network.authentication.AppAuthResult
import com.example.moviesapp.data.network.authentication.AuthResult
import javax.inject.Inject

class AuthResultMapper @Inject constructor() : AuthDTO<AuthResult, AuthStatus> {
    override suspend fun authResultToAuthStatusMapper(authResult: AuthResult): AuthStatus =
        when (authResult.state) {
            AppAuthResult.ResultState.LOADING -> AuthStatus.InProgress
            AppAuthResult.ResultState.SUCCESS -> AuthStatus.Success
            AppAuthResult.ResultState.ERROR -> AuthStatus.Failed(authResult.errorMessage)
        }
}