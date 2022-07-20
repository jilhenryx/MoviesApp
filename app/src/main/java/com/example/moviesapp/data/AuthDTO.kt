package com.example.moviesapp.data

import com.example.moviesapp.core.AppAuthStatus
import com.example.moviesapp.network.authentication.AppAuthResult

interface AuthDTO<AuthResult : AppAuthResult, Status : AppAuthStatus> {
    suspend fun authResultToAuthStatusMapper(authResult: AuthResult): Status
}