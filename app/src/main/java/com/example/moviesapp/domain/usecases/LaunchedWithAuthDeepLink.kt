package com.example.moviesapp.domain.usecases

import com.example.moviesapp.core.AuthEmailType
import com.example.moviesapp.domain.AuthInteractor
import com.example.moviesapp.domain.DeepLinkHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

data class AuthDeepLinkData(
    val mode: AuthEmailType,
    val data: String
)

class LaunchedWithAuthDeepLink @Inject constructor(
    private val deepLinkHandler: DeepLinkHandler
) : AuthInteractor<LaunchedWithAuthDeepLink.Param, AuthDeepLinkData?>() {
    data class Param(val deepLink: String)

    override suspend fun invoke(params: Param): Flow<AuthDeepLinkData?> =
        flowOf(deepLinkHandler.handleDeepLink(params.deepLink))
}