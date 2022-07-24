package com.example.moviesapp.data.repositories

import com.example.moviesapp.core.AuthStatusWithValue
import com.example.moviesapp.data.mappers.AuthDTO
import com.example.moviesapp.data.network.authentication.AppAuthResult
import com.example.moviesapp.data.network.authentication.AuthHandler
import com.example.moviesapp.data.network.authentication.AuthResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val authHandler: AuthHandler
) : AuthDTO<AuthResult, AuthStatusWithValue<Boolean>> {

    internal fun login(email: String, password: String): Flow<AuthStatusWithValue<Boolean>> =
        authHandler
            .login(email = email, password = password)
            .map(::authResultToAuthStatusMapper)


    override suspend fun authResultToAuthStatusMapper(authResult: AuthResult): AuthStatusWithValue<Boolean> =
        when (authResult.state) {
            AppAuthResult.ResultState.LOADING -> AuthStatusWithValue.InProgress()
            AppAuthResult.ResultState.SUCCESS -> verifyEmailConfirmation()
            AppAuthResult.ResultState.ERROR -> AuthStatusWithValue.Failed(authResult.errorMessage)

        }

    private suspend fun verifyEmailConfirmation(): AuthStatusWithValue<Boolean> =
        authHandler.confirmEmailVerification()
            .filter {
                it.state == AppAuthResult.ResultState.SUCCESS || it.state == AppAuthResult.ResultState.ERROR
            }.map {
                it.resultValue?.let { isVerified ->
                    if (isVerified) AuthStatusWithValue.Success(isVerified)
                    else null
                }
                    ?: AuthStatusWithValue.Success(false)
            }.first()
}