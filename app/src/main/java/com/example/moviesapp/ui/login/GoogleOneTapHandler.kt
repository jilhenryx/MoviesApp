package com.example.moviesapp.ui.login

import android.content.IntentSender
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import com.example.moviesapp.BuildConfig
import com.example.moviesapp.core.AuthStatus
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.SignInClient
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

object GoogleOneTapHandler {
    private const val TAG = "GoogleOneTapHandler"

    // Only Tracking Failure Since Successful launching of the Google One-Tap result
    // will be handled by ActivityResultLauncher. Failure is tracked to disable Loading Indication
    private const val FAILED = 400

    internal suspend fun loginWithGoogle(
        launcher: ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult>,
        onTapClient: SignInClient,
    ): Flow<Int> {

        return launchOneTapRequest(
            oneTapClient = onTapClient,
            launcher = launcher
        )
    }

    private suspend fun launchOneTapRequest(
        oneTapClient: SignInClient,
        launcher: ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult>
    ) = callbackFlow {
        val signInRequest = BeginSignInRequest.builder()
            .setPasswordRequestOptions(
                BeginSignInRequest.PasswordRequestOptions.builder()
                    .setSupported(true)
                    .build()
            )
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(BuildConfig.ONE_TAP_CLIENT_ID)
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .setAutoSelectEnabled(false)
            .build()

        oneTapClient.beginSignIn(signInRequest)
            .addOnSuccessListener { result ->
                onSuccess(result, launcher)
            }
            .addOnFailureListener {
                onFailure(it)
            }
        awaitClose {
            channel.close()
        }
    }

    private fun ProducerScope<Int>.onSuccess(
        result: BeginSignInResult?,
        launcher: ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult>
    ) {
        try {
            if (result == null) throw NullPointerException()
            Log.d(TAG, "launchOneTapRequest: Google Request Successful")
            IntentSenderRequest.Builder(result.pendingIntent.intentSender).build().also {
                launcher.launch(it)
            }
            close()

        } catch (intentError: IntentSender.SendIntentException) {
            onFailure(intentError)
        } catch (resultError: NullPointerException) {
            onFailure(resultError)
        }
    }

    private fun ProducerScope<Int>.onFailure(exception: Exception) {
        Log.d(TAG, "launchOneTapRequest: ${exception.localizedMessage}")
        trySend(FAILED)
        close()
    }

}
