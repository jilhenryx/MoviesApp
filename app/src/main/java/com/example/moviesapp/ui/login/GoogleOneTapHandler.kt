package com.example.moviesapp.ui.login

import android.content.IntentSender
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import com.example.moviesapp.BuildConfig
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.SignInClient
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map

object GoogleOneTapHandler {
    private const val TAG = "GoogleOneTapHandler"

    // Only Tracking Failure Since Successful launching of the Google One-Tap result
    // will be handled by ActivityResultLauncher. Failure is tracked to disable Loading Indication
    private const val FAILED = 400

    internal suspend fun loginWithGoogle(
        launcher: ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult>,
        onTapClient: SignInClient,
    ): Flow<Int> {

        return launchSignInRequest(
            oneTapClient = onTapClient,
            launcher = launcher
        )
    }

    private suspend fun launchSignInRequest(
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
                    .setFilterByAuthorizedAccounts(true)
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()

        oneTapClient.beginSignIn(signInRequest)
            .addOnSuccessListener { result ->
                onSuccess(result, launcher)
            }
            .addOnFailureListener {
                Log.d(TAG, "launchSignInRequest: No Sign Exit. Launching SignUp Request")
                launchSignUpRequest(oneTapClient, launcher).map {
                    trySend(it)
                    this.close()
                }
            }
        awaitClose {
            channel.close()
        }
    }

    private fun launchSignUpRequest(
        oneTapClient: SignInClient,
        launcher: ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult>
    ) = callbackFlow {
        val signUpRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(BuildConfig.ONE_TAP_CLIENT_ID)
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            ).build()

        oneTapClient.beginSignIn(signUpRequest)
            .addOnSuccessListener { result ->
                onSuccess(result, launcher)
            }
            .addOnFailureListener {
                Log.d(TAG, "launchInSignUpRequest: Sign Up Failed", it)
                trySend(FAILED)
                close()
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
            IntentSenderRequest.Builder(result.pendingIntent.intentSender).build().also {
                launcher.launch(it)
            }
            close()

        } catch (intentError: IntentSender.SendIntentException) {
            Log.d(TAG, "launchGoogleRequest: Intent Error", intentError)
            trySend(FAILED)
            close()
        } catch (resultError: NullPointerException) {
            Log.d(TAG, "launchGoogleRequest: Null Pointer Exception")
            trySend(FAILED)
            close()
        }
    }

}
