package com.example.moviesapp.data

import com.example.moviesapp.core.AuthStatusWithValue
import com.example.moviesapp.network.authentication.AppAuthResult
import com.example.moviesapp.network.authentication.AuthHandler
import com.example.moviesapp.network.authentication.AuthResultWithValue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class VerifyResetCodeRepository @Inject constructor(
    private val authHandler: AuthHandler
) : AuthDTO<AuthResultWithValue<String>, AuthStatusWithValue<String>> {

    internal suspend fun verifyCode(resetCode: String): Flow<AuthStatusWithValue<String>> =
        authHandler.verifyRestPasswordCode(resetCode).map(::authResultToAuthStatusMapper)

    override suspend fun authResultToAuthStatusMapper(authResult: AuthResultWithValue<String>): AuthStatusWithValue<String> =
        when (authResult.state) {
            AppAuthResult.ResultState.LOADING -> AuthStatusWithValue.InProgress()
            AppAuthResult.ResultState.SUCCESS -> AuthStatusWithValue.Success(authResult.resultValue!!)
            AppAuthResult.ResultState.ERROR -> AuthStatusWithValue.Failed(authResult.errorMessage)
        }
}