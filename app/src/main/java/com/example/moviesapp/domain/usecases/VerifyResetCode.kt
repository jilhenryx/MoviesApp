package com.example.moviesapp.domain.usecases

import com.example.moviesapp.core.AuthStatusWithValue
import com.example.moviesapp.data.repositories.VerifyResetCodeRepository
import com.example.moviesapp.domain.AuthInteractor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class VerifyResetCode @Inject constructor(
    private val ioDispatcher: CoroutineDispatcher,
    private val verifyCodeRepo: VerifyResetCodeRepository
) : AuthInteractor<VerifyResetCode.Params, AuthStatusWithValue<String>>() {

    data class Params(val resetCode: String)

    override suspend fun invoke(params: Params): Flow<AuthStatusWithValue<String>> =
        withContext(ioDispatcher) {
            verifyCodeRepo.verifyCode(params.resetCode)
        }
}