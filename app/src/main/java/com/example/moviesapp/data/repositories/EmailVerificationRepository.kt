package com.example.moviesapp.data.repositories

import com.example.moviesapp.core.AuthStatusWithValue
import com.example.moviesapp.data.mappers.AuthResultWithValueMapper
import com.example.moviesapp.data.network.authentication.AuthHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class EmailVerificationRepository @Inject constructor(
    private val authHandler: AuthHandler,
    private val authResultMapper: AuthResultWithValueMapper<Boolean>
) {

    internal suspend fun confirmEmailVerification(): Flow<AuthStatusWithValue<Boolean>> =
        authHandler.confirmEmailVerification().map(authResultMapper::authResultToAuthStatusMapper)

    internal suspend fun checkIfMailExist(email: String): Flow<AuthStatusWithValue<Boolean>> =
        authHandler.checkIfEmailExists(email).map(authResultMapper::authResultToAuthStatusMapper)
}