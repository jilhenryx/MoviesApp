package com.example.moviesapp.network

import android.util.Log
import com.example.moviesapp.ui.constants.PACKAGE_NAME
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.ChannelResult
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class AuthHandler @Inject constructor() {
    private val defaultErrorMessage = "Oops! Something went wrong. Please try again later"
    private val defaultLoginErrorMessage =
        "Unable to Login. The email or password entered is not correct"
    private val defaultSignUpErrorMessage =
        "Unable to create a new account. The email already exists"
    private val firebaseAuth: FirebaseAuth
        get() = Firebase.auth

    companion object {
        private const val TAG = "AuthHandler"
    }

    internal fun signOut() =
        firebaseAuth.currentUser?.let { firebaseAuth.signOut() }


    internal fun login(email: String, password: String): Flow<AppAuthResult> =
        callbackFlow<AppAuthResult> {
            send(AppAuthResult.onLoad())
            //If User exists, sign them out before login
            signOut()
            Log.d(TAG, "login: Initiated")
            try {
                firebaseAuth
                    .signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        Log.d(TAG, "login: User Logged In")
                        val channelResult = trySend(AppAuthResult.onSuccess())
                        authHandlerLogger("login", channelResult)
                        close()
                    }
                    .addOnFailureListener { exception ->
                        Log.d(TAG, "login: Login Failed: ", exception)
                        val errorMessage =
                            if (exception is FirebaseAuthInvalidCredentialsException) {
                                exception.message ?: defaultLoginErrorMessage
                            } else {
                                defaultErrorMessage
                            }
                        val channelResult = trySend(AppAuthResult.onError(errorMessage))
                        authHandlerLogger("login", channelResult)
                        close()
                    }
            } catch (exception: Exception) {
                Log.d(TAG, "login: Exception Caught: ", exception)
                send(AppAuthResult.onError(defaultErrorMessage))
                close()
            }
            awaitClose {
                channel.close()
            }
        }

    private fun authHandlerLogger(funName: String, result: ChannelResult<Unit>) {
        if (result.isFailure) {
            Log.d(TAG, "$funName: Updating Channel Failed", result.exceptionOrNull())
        }
    }

    internal fun createUser(
        email: String,
        password: String,
        firstname: String,
        lastname: String
    ) = callbackFlow {
        send(AppAuthResult.onLoad())

        //If User exists, sign them out before signup
        signOut()
        Log.d(TAG, "createUser: Initiated")
        try {
            firebaseAuth
                .createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener { result ->
                    Log.d(TAG, "createUser: Created Successfully")
                    result.user?.let { user ->
                        user.updateProfile(
                            userProfileChangeRequest {
                                displayName = "$firstname $lastname"
                            }
                        ).addOnSuccessListener {
                            Log.d(TAG, "createUser: User Profile Updated")
                        }.addOnFailureListener {
                            Log.d(TAG, "createUser: Unable to Update User", it)
                        }
                        val channelResult = trySend(AppAuthResult.onSuccess())
                        authHandlerLogger("createUser", channelResult)
                        close()
                    }
                }.addOnFailureListener { exception ->
                    Log.d(TAG, "createUser: Create User Failed", exception)
                    val errorMessage = when (exception) {
                        is FirebaseAuthUserCollisionException -> {
                            exception.message ?: defaultErrorMessage
                        }
                        is FirebaseAuthInvalidCredentialsException -> {
                            exception.message ?: "Email is Invalid"
                        }
                        else -> {
                            defaultErrorMessage
                        }
                    }
                    val channelResult = trySend(AppAuthResult.onError(errorMessage))
                    authHandlerLogger("createUser", channelResult)
                    close()
                }
        } catch (exception: Exception) {
            Log.d(TAG, "createUser: Exception Caught: ", exception)
            send(AppAuthResult.onError(defaultErrorMessage))
        }
        awaitClose {
            channel.close()
        }
    }

    internal fun sendVerificationEmail(email: String = "domain@example.com") {
        try {
            /*TODO: Fix Domain Issue In Firebase Console so that actionCode can be used*/
            val actionCodeSettings = ActionCodeSettings.newBuilder()
                .setUrl("https://moviesappng.page.link/usermgmt/verifyemail?email=$email")
                .setAndroidPackageName(PACKAGE_NAME, true, null)
                .build()
            firebaseAuth.useAppLanguage()
            val user = firebaseAuth.currentUser
            if (user != null) {
                user.sendEmailVerification()
                    .addOnSuccessListener { Log.d(TAG, "sendVerificationEmail: Successful") }
                    .addOnFailureListener { Log.d(TAG, "sendVerificationEmail: Failed", it) }
            } else {
                Log.d(TAG, "sendVerificationEmail: User Is Null")
            }
        } catch (exception: Exception) {
            Log.d(TAG, "sendVerificationEmail: Exception Caught: ", exception)
        }
    }

    internal fun sendPasswordResetEmail(email: String) =
        callbackFlow<AppAuthResult> {
            try {
                /*TODO: Fix Domain Issue In Firebase Console so that actionCode can be used*/
                val actionCodeSettings = ActionCodeSettings.newBuilder()
                    .setUrl("https://moviesappng.page.link/usermgmt/resetpassword?email=$email")
                    .setHandleCodeInApp(true)
                    .setDynamicLinkDomain("https://moviesappng.page.link")
                    .setAndroidPackageName(PACKAGE_NAME, true, null)
                    .build()

                send(AppAuthResult.onLoad())
                Log.d(TAG, "sendPasswordResetEmail: Initiated")
                firebaseAuth.useAppLanguage()
                firebaseAuth.sendPasswordResetEmail(email)
                    .addOnSuccessListener {
                        Log.d(TAG, "sendPasswordResetEmail: Successful")
                        val channelResult = trySend(AppAuthResult.onSuccess())
                        authHandlerLogger("sendPasswordResetEmail", channelResult)
                        close()
                    }.addOnFailureListener { exception ->
                        Log.d(TAG, "sendPasswordReset: Failed", exception)
                        val channelResult = trySend(AppAuthResult.onError(defaultErrorMessage))
                        authHandlerLogger("sendPasswordResetEmail", channelResult)
                        close()
                    }
            } catch (exception: Exception) {
                Log.d(TAG, "sendPasswordResetEmail: Exception Caught: ", exception)
                send(AppAuthResult.onError(defaultErrorMessage))
            }
            awaitClose {
                channel.close()
            }
        }

    internal fun resetPassword(email: String, password: String) =
        callbackFlow<AppAuthResult> {
            send(AppAuthResult.onLoad())
            /*TODO : Reset Logic. You need a way to log user in*/
        }

    internal fun checkEmailVerification() =
        firebaseAuth.currentUser?.isEmailVerified ?: false
}
