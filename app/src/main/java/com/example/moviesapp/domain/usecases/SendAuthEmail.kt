package com.example.moviesapp.domain.usecases

import com.example.moviesapp.core.AuthEmailType
import com.example.moviesapp.core.AuthStatus
import com.example.moviesapp.data.SendAuthEmailRepository
import com.example.moviesapp.domain.AuthInteractor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SendAuthEmail @Inject constructor(
    private val repo: SendAuthEmailRepository,
    private val ioDispatcher: CoroutineDispatcher
) : AuthInteractor<SendAuthEmail.Params, AuthStatus>() {

    data class Params(
        val email: String,
        val type: AuthEmailType
    )

    override suspend fun invoke(params: Params): Flow<AuthStatus> =
        withContext(ioDispatcher) {
            repo.sendEmail(
                email = params.email,
                type = params.type
            )
        }
}