package com.example.moviesapp.ui.login

import android.content.IntentSender
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

object GoogleOneTapHandler {
    private const val TAG = "GoogleOneTapHandler"

    // Only Tracking Failure Since Successful launching of the Google One-Tap result
    // will be handled by ActivityResultLauncher. Failure is tracked to disable Loading Indication
    internal const val FAILED = 400

    internal fun loginWithGoogle(
        launcher: ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult>,
        onTapClient: SignInClient,
    ): Flow<Int> {

        val signInRequest = BeginSignInRequest.builder()
            .setPasswordRequestOptions(
                BeginSignInRequest.PasswordRequestOptions.builder()
                    .setSupported(true)
                    .build()
            )
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId("")// TODO: add One-Tap Server ID
                    .setFilterByAuthorizedAccounts(true)
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
        return launchSignInRequest(
            oneTapClient = onTapClient,
            signInRequest = signInRequest,
            launcher = launcher
        )
    }

    private fun launchSignInRequest(
        oneTapClient: SignInClient,
        signInRequest: BeginSignInRequest,
        launcher: ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult>
    ) = callbackFlow<Int> {
        oneTapClient.beginSignIn(signInRequest)
            .addOnSuccessListener { result ->
                try {
                    if (result == null) throw NullPointerException()
                    IntentSenderRequest.Builder(result.pendingIntent.intentSender).build().also {
                        launcher.launch(it)
                    }
                    close()

                } catch (intentError: IntentSender.SendIntentException) {
                    Log.d(TAG, "launchSignInRequest: Intent Error", intentError)
                    trySend(FAILED)
                    close()
                } catch (resultError: NullPointerException) {
                    Log.d(TAG, "launchSignInRequest: Null Pointer Exception")
                    trySend(FAILED)
                    close()
                }
            }
            .addOnFailureListener {
                Log.d(TAG, "launchSignInRequest: No Sign Exit. Launching SignUp Request")
                /* TODO: Launch SignUp Flow*/

            }
        awaitClose {
            channel.close()
        }
    }
}