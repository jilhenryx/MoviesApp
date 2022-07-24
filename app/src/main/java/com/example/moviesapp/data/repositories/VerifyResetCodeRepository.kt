package com.example.moviesapp.data.repositories

import com.example.moviesapp.core.AuthStatusWithValue
import com.example.moviesapp.data.mappers.AuthResultWithValueMapper
import com.example.moviesapp.data.network.authentication.AuthHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class VerifyResetCodeRepository @Inject constructor(
    private val authHandler: AuthHandler,
    private val authResultMapper: AuthResultWithValueMapper<String>
) {

    internal suspend fun verifyCode(resetCode: String): Flow<AuthStatusWithValue<String>> =
        authHandler.verifyRestPasswordCode(resetCode)
            .map(authResultMapper::authResultToAuthStatusMapper)
}