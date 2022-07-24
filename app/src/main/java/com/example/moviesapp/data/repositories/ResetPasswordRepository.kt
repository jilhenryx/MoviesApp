package com.example.moviesapp.data.repositories

import com.example.moviesapp.core.AuthStatus
import com.example.moviesapp.data.mappers.AuthResultMapper
import com.example.moviesapp.data.network.authentication.AuthHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ResetPasswordRepository @Inject constructor(
    private val authHandler: AuthHandler,
    private  val authResultMapper: AuthResultMapper
) {

    internal suspend fun resetPassword(resetCode: String, password: String): Flow<AuthStatus> =
        authHandler.resetPassword(resetCode = resetCode, password = password)
            .map(authResultMapper::authResultToAuthStatusMapper)
}