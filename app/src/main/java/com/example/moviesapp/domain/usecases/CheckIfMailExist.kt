package com.example.moviesapp.domain.usecases

import com.example.moviesapp.core.AuthStatusWithValue
import com.example.moviesapp.data.EmailVerificationRepository
import com.example.moviesapp.domain.AuthInteractor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CheckIfMailExist @Inject constructor(
    private val ioDispatcher: CoroutineDispatcher,
    private val emailRepo: EmailVerificationRepository
) : AuthInteractor<CheckIfMailExist.Params, AuthStatusWithValue<Boolean>>() {

    data class Params(val email: String)

    override suspend fun invoke(params: Params): Flow<AuthStatusWithValue<Boolean>> =
        withContext(ioDispatcher) {
            emailRepo.checkIfMailExist(params.email)
        }
}