package com.example.moviesapp.data.repositories

import com.example.moviesapp.core.AuthEmailType
import com.example.moviesapp.core.AuthStatus
import com.example.moviesapp.data.mappers.AuthResultMapper
import com.example.moviesapp.data.network.authentication.AuthHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SendAuthEmailRepository @Inject constructor(
    private val authHandler: AuthHandler,
    private val authResultMapper: AuthResultMapper
) {

    internal suspend fun sendEmail(email: String, type: AuthEmailType): Flow<AuthStatus> =
        when (type) {
            AuthEmailType.VERIFY_EMAIL -> sendVerificationEmail(email)
            AuthEmailType.RESET_PASSWORD -> sendResetVerificationEmail(email)
        }


    private suspend fun sendVerificationEmail(email: String) =
        authHandler.sendVerificationEmail(email).map(authResultMapper::authResultToAuthStatusMapper)

    private suspend fun sendResetVerificationEmail(email: String) =
        authHandler.sendPasswordResetEmail(email).map(authResultMapper::authResultToAuthStatusMapper)

}