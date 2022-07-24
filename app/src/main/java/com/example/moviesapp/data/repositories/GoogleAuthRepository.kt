package com.example.moviesapp.data.repositories

import com.example.moviesapp.core.AuthStatus
import com.example.moviesapp.data.mappers.AuthResultMapper
import com.example.moviesapp.data.network.authentication.AuthHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GoogleAuthRepository @Inject constructor(
    private val authHandler: AuthHandler,
    private val authResultMapper: AuthResultMapper
){

    internal suspend fun loginWithGoogle(idToken:String): Flow<AuthStatus> =
        authHandler.loginWithGoogle(idToken).map(authResultMapper::authResultToAuthStatusMapper)
}