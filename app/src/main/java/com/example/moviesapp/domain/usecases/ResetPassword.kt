package com.example.moviesapp.domain.usecases

import com.example.moviesapp.core.AuthStatus
import com.example.moviesapp.data.ResetPasswordRepository
import com.example.moviesapp.domain.AuthInteractor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ResetPassword @Inject constructor(
    private val ioDispatcher: CoroutineDispatcher,
    private val resetPasswordRepo: ResetPasswordRepository
) : AuthInteractor<ResetPassword.Params, AuthStatus>() {

    data class Params(val resetCode: String, val password: String)

    override suspend fun invoke(params: Params): Flow<AuthStatus> =
        withContext(ioDispatcher) {
            resetPasswordRepo.resetPassword(
                resetCode = params.resetCode,
                password = params.password
            )
        }
}