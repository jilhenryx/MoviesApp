package com.example.moviesapp.data.mappers

import com.example.moviesapp.core.AppAuthStatus
import com.example.moviesapp.data.network.authentication.AppAuthResult

interface AuthDTO<AuthResult : AppAuthResult, Status : AppAuthStatus> {
    suspend fun authResultToAuthStatusMapper(authResult: AuthResult): Status
}