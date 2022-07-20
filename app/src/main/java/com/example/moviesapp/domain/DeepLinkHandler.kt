package com.example.moviesapp.domain

import android.net.Uri
import com.example.moviesapp.core.AuthEmailType
import com.example.moviesapp.domain.usecases.AuthDeepLinkData
import com.example.moviesapp.network.authentication.AuthConstants.DEEP_LINK_MODE_QUERY_KEY
import com.example.moviesapp.network.authentication.AuthConstants.DEEP_LINK_MODE_RESET_PASSWORD
import com.example.moviesapp.network.authentication.AuthConstants.DEEP_LINK_MODE_VERIFY_EMAIL
import com.example.moviesapp.network.authentication.AuthConstants.EMAIL_QUERY_KEY
import javax.inject.Inject

class DeepLinkHandler @Inject constructor() {

    internal fun handleDeepLink(deepLink: String): AuthDeepLinkData? =
        Uri.parse(deepLink).let { uri ->
            extractDeepLinkMode(uri).let { mode ->
                when (mode) {
                    DEEP_LINK_MODE_VERIFY_EMAIL ->
                        AuthDeepLinkData(AuthEmailType.VERIFY_EMAIL, getEmail(uri))
                    DEEP_LINK_MODE_RESET_PASSWORD ->
                        AuthDeepLinkData(AuthEmailType.RESET_PASSWORD, getPasswordResetCode(uri))
                    else -> null
                }
            }
        }

    private fun extractDeepLinkMode(uri: Uri?): String =
        uri?.getQueryParameter(DEEP_LINK_MODE_QUERY_KEY) ?: ""

    private fun getPasswordResetCode(uri: Uri?) =
        uri?.getQueryParameter("oobCode") ?: ""

    private fun getEmail(uri: Uri?): String =
        uri?.getQueryParameter(EMAIL_QUERY_KEY) ?: ""

}