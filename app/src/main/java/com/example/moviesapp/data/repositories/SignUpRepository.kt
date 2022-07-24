package com.example.moviesapp.data.repositories

import com.example.moviesapp.core.AuthStatus
import com.example.moviesapp.data.mappers.AuthResultMapper
import com.example.moviesapp.data.network.authentication.AuthHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SignUpRepository @Inject constructor(
    private val authHandler: AuthHandler,
    private val authResultMapper: AuthResultMapper
) {

    internal suspend fun signUp(
        firstname: String,
        lastname: String,
        email: String,
        password: String
    ): Flow<AuthStatus> =
        authHandler.createUser(
            firstname = firstname,
            lastname = lastname,
            email = email,
            password = password
        ).map(authResultMapper::authResultToAuthStatusMapper)
}