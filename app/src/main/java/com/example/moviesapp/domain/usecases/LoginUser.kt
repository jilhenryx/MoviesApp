package com.example.moviesapp.domain.usecases

import com.example.moviesapp.core.AuthStatusWithValue
import com.example.moviesapp.data.LoginRepository
import com.example.moviesapp.domain.AuthInteractor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginUser @Inject constructor(
    private val loginRepo: LoginRepository,
    private val ioDispatcher: CoroutineDispatcher
) : AuthInteractor<LoginUser.Params, AuthStatusWithValue<Boolean>>() {

    data class Params(
        val email: String,
        val password: String
    )

    override suspend fun invoke(params: Params): Flow<AuthStatusWithValue<Boolean>> {
        return withContext(ioDispatcher) {
            loginRepo.login(email = params.email, password = params.password)
        }
    }
}
