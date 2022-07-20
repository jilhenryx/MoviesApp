package com.example.moviesapp.domain.usecases

import com.example.moviesapp.core.AuthStatusWithValue
import com.example.moviesapp.data.EmailVerificationRepository
import com.example.moviesapp.domain.AuthInteractor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ConfirmEmailVerification @Inject constructor(
    private val repo: EmailVerificationRepository,
    private val ioDispatcher: CoroutineDispatcher
) : AuthInteractor<Nothing?, AuthStatusWithValue<Boolean>>() {

    override suspend fun invoke(params: Nothing?): Flow<AuthStatusWithValue<Boolean>> =
        withContext(ioDispatcher) {
            repo.confirmEmailVerification()
        }
}