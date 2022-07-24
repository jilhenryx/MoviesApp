package com.example.moviesapp.data.mappers

import com.example.moviesapp.core.AuthStatusWithValue
import com.example.moviesapp.data.network.authentication.AppAuthResult
import com.example.moviesapp.data.network.authentication.AuthResultWithValue
import javax.inject.Inject

class AuthResultWithValueMapper<T> @Inject constructor() :
    AuthDTO<AuthResultWithValue<T>, AuthStatusWithValue<T>> {
    override suspend fun authResultToAuthStatusMapper(authResult: AuthResultWithValue<T>): AuthStatusWithValue<T> =
        when (authResult.state) {
            AppAuthResult.ResultState.LOADING -> AuthStatusWithValue.InProgress()
            AppAuthResult.ResultState.SUCCESS -> AuthStatusWithValue.Success(authResult.resultValue!!)
            AppAuthResult.ResultState.ERROR -> AuthStatusWithValue.Failed(authResult.errorMessage)
        }
}