package com.example.moviesapp.data

import com.example.moviesapp.core.AuthStatusWithValue
import com.example.moviesapp.network.authentication.AppAuthResult
import com.example.moviesapp.network.authentication.AuthHandler
import com.example.moviesapp.network.authentication.AuthResultWithValue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class EmailVerificationRepository @Inject constructor(
    private val authHandler: AuthHandler
) : AuthDTO<AuthResultWithValue<Boolean>, AuthStatusWithValue<Boolean>> {

    internal suspend fun confirmEmailVerification(): Flow<AuthStatusWithValue<Boolean>> =
        authHandler.confirmEmailVerification().map(::authResultToAuthStatusMapper)

    internal suspend fun checkIfMailExist(email: String): Flow<AuthStatusWithValue<Boolean>> =
        authHandler.checkIfEmailExists(email).map(::authResultToAuthStatusMapper)


    override suspend fun authResultToAuthStatusMapper(authResult: AuthResultWithValue<Boolean>): AuthStatusWithValue<Boolean> =
        when (authResult.state) {
            AppAuthResult.ResultState.LOADING -> AuthStatusWithValue.InProgress<Boolean>()
            AppAuthResult.ResultState.SUCCESS -> AuthStatusWithValue.Success(
                authResult.resultValue ?: false
            )
            AppAuthResult.ResultState.ERROR -> AuthStatusWithValue.Failed<Boolean>(authResult.errorMessage)
        }

}