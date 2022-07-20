package com.example.moviesapp.domain.usecases

import com.example.moviesapp.core.AuthStatus
import com.example.moviesapp.data.SignUpRepository
import com.example.moviesapp.domain.AuthInteractor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SignUpUser @Inject constructor(
    private val ioDispatcher: CoroutineDispatcher,
    private val signUpRepo: SignUpRepository
) : AuthInteractor<SignUpUser.Params, AuthStatus>() {

    data class Params(
        internal val firstname: String,
        internal val lastname: String,
        internal val email: String,
        internal val password: String,
    )

    override suspend fun invoke(params: Params): Flow<AuthStatus> =
        withContext(ioDispatcher) {
            signUpRepo.signUp(
                firstname = params.firstname,
                lastname = params.lastname,
                email = params.email,
                password = params.password
            )
        }

}