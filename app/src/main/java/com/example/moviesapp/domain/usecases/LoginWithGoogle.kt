package com.example.moviesapp.domain.usecases

import com.example.moviesapp.core.AuthStatus
import com.example.moviesapp.data.repositories.GoogleAuthRepository
import com.example.moviesapp.domain.AuthInteractor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginWithGoogle @Inject constructor(
    private val ioDispatcher: CoroutineDispatcher,
    private val googleAuthRepo: GoogleAuthRepository
) : AuthInteractor<LoginWithGoogle.Params, AuthStatus>() {

    data class Params(val idToken: String)

    override suspend fun invoke(params: Params): Flow<AuthStatus> =
        withContext(ioDispatcher) {
            googleAuthRepo.loginWithGoogle(params.idToken)
        }
}