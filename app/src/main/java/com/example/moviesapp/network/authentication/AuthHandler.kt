package com.example.moviesapp.network.authentication

import android.provider.ContactsContract.Directory.PACKAGE_NAME
import android.util.Log
import com.example.moviesapp.core.Messages.DEFAULT_ERROR_MESSAGE
import com.example.moviesapp.core.Messages.DEFAULT_LOGIN_ERROR_MESSAGE
import com.example.moviesapp.core.Messages.DEFAULT_SIGN_UP_ERROR_MESSAGE
import com.example.moviesapp.network.authentication.AuthConstants.DEEP_LINK_MODE_QUERY_KEY
import com.example.moviesapp.network.authentication.AuthConstants.DEEP_LINK_MODE_VERIFY_EMAIL
import com.example.moviesapp.network.authentication.AuthConstants.EMAIL_QUERY_KEY
import com.example.moviesapp.network.authentication.AuthConstants.RESET_PASSWORD_URL_PATH
import com.example.moviesapp.network.authentication.AuthConstants.VERIFY_EMAIL_URL_PATH
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.ChannelResult
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class AuthHandler @Inject constructor() {
    private val firebaseAuth: FirebaseAuth
        get() = Firebase.auth

    companion object {
        private const val TAG = "AuthHandler"

        internal fun buildDeepLinkUri(
            fullPath: String,
            queries: Map<String, String>
        ): String {
            val stringBuilder = StringBuilder("$fullPath?")
            queries.forEach {
                stringBuilder.append("${it.key}=${it.value}&")
            }
            return stringBuilder.deleteCharAt(stringBuilder.length - 1).toString()
        }
    }

    private fun signOut() =
        firebaseAuth.currentUser?.let { firebaseAuth.signOut() }


    internal fun login(email: String, password: String) = callbackFlow<AuthResult>
    {
        send(AuthResult.onLoad())
        //If User exists, sign them out before login
        signOut()
        Log.d(TAG, "login: Initiated")
        try {
            firebaseAuth
                .signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    Log.d(TAG, "login: User Logged In")
                    trySend(AuthResult.onSuccess()).also {
                        authHandlerLogger("login", it)
                    }
                    close()
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "login: Login Failed: ", exception)
                    val errorMessage =
                        if (exception is FirebaseAuthInvalidCredentialsException) {
                            exception.message ?: DEFAULT_LOGIN_ERROR_MESSAGE
                        } else {
                            exception.message ?: DEFAULT_ERROR_MESSAGE
                        }
                    trySend(AuthResult.onError(errorMessage)).also {
                        authHandlerLogger("login", it)
                    }
                    close()
                }
        } catch (exception: Exception) {
            Log.d(TAG, "login: Exception Caught: ", exception)
            send(AuthResult.onError(exception.message ?: DEFAULT_ERROR_MESSAGE))
            close()
        }
        awaitClose {
            channel.close()
        }
    }

    internal fun loginWithGoogle(idToken: String) {
        val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
        // TODO: Verify if user exist and what happens on collision before you implement
    }


    internal fun createUser(
        email: String,
        password: String,
        firstname: String,
        lastname: String
    ) = callbackFlow {
        send(AuthResult.onLoad())

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
                        trySend(AuthResult.onSuccess()).also {
                            authHandlerLogger("createUser", it)
                        }
                        close()
                    }
                }.addOnFailureListener { exception ->
                    Log.d(TAG, "createUser: Create User Failed", exception)
                    val errorMessage = when (exception) {
                        is FirebaseAuthUserCollisionException -> {
                            exception.message ?: DEFAULT_SIGN_UP_ERROR_MESSAGE
                        }
                        is FirebaseAuthInvalidCredentialsException -> {
                            exception.message ?: "Email is Invalid"
                        }
                        else -> {
                            exception.message ?: DEFAULT_ERROR_MESSAGE
                        }
                    }
                    trySend(AuthResult.onError(errorMessage)).also {
                        authHandlerLogger("createUser", it)
                    }
                    close()
                }
        } catch (exception: Exception) {
            Log.d(TAG, "createUser: Exception Caught: ", exception)
            send(AuthResult.onError(exception.message ?: DEFAULT_ERROR_MESSAGE))
            close()
        }
        awaitClose {
            channel.close()
        }
    }

    internal fun sendVerificationEmail(email: String) =
        callbackFlow {
            try {
                send(AuthResult.onLoad())

                val url = buildDeepLinkUri(
                    fullPath = VERIFY_EMAIL_URL_PATH,
                    queries = mapOf(
                        DEEP_LINK_MODE_QUERY_KEY to DEEP_LINK_MODE_VERIFY_EMAIL,
                        EMAIL_QUERY_KEY to email
                    )
                )

                val actionCodeSettings = ActionCodeSettings.newBuilder()
                    .setUrl(url)
                    .setHandleCodeInApp(false)
                    .setAndroidPackageName(PACKAGE_NAME, true, null)
                    .build()
                firebaseAuth.useAppLanguage()

                val user = firebaseAuth.currentUser
                if (user != null) {
                    user.sendEmailVerification(actionCodeSettings)
                        .addOnSuccessListener {
                            Log.d(TAG, "sendVerificationEmail: Successful")
                            trySend(AuthResult.onSuccess()).also {
                                authHandlerLogger("sendVerificationEmail", it)
                            }
                            close()
                        }
                        .addOnFailureListener { exception ->
                            Log.d(TAG, "sendVerificationEmail: Failed", exception)
                            trySend(
                                AuthResult.onError(
                                    exception.message ?: DEFAULT_ERROR_MESSAGE
                                )
                            ).also {
                                authHandlerLogger("sendVerificationEmail", it)
                            }
                            close()
                        }
                } else {
                    Log.d(TAG, "sendVerificationEmail: User Is Null")
                    trySend(
                        AuthResult.onError(
                            DEFAULT_ERROR_MESSAGE
                        )
                    ).also {
                        authHandlerLogger("sendVerificationEmail", it)
                    }
                    close()
                }
            } catch (exception: Exception) {
                Log.d(TAG, "sendVerificationEmail: Exception Caught: ", exception)
                send(AuthResult.onError(DEFAULT_ERROR_MESSAGE))
                close()
            }
            awaitClose {
                channel.close()
            }
        }

    internal fun sendPasswordResetEmail(email: String) =
        callbackFlow {
            try {
                send(AuthResult.onLoad())

                val actionCodeSettings = ActionCodeSettings.newBuilder()
                    .setUrl(RESET_PASSWORD_URL_PATH)
                    .setHandleCodeInApp(true)
                    .setAndroidPackageName(PACKAGE_NAME, true, null)
                    .build()

                firebaseAuth.useAppLanguage()
                firebaseAuth.sendPasswordResetEmail(email, actionCodeSettings)
                    .addOnSuccessListener {
                        Log.d(TAG, "sendPasswordResetEmail: Successful")
                        trySend(AuthResult.onSuccess()).also {
                            authHandlerLogger("sendPasswordResetEmail", it)
                        }
                        close()
                    }.addOnFailureListener { exception ->
                        Log.d(TAG, "sendPasswordReset: Failed", exception)
                        trySend(AuthResult.onError(DEFAULT_ERROR_MESSAGE)).also {
                            authHandlerLogger("sendPasswordResetEmail", it)
                        }
                        close()
                    }
            } catch (exception: Exception) {
                Log.d(TAG, "sendPasswordResetEmail: Exception Caught: ", exception)
                send(AuthResult.onError(DEFAULT_ERROR_MESSAGE))
                close()
            }
            awaitClose {
                channel.close()
            }
        }

    internal fun verifyRestPasswordCode(resetCode: String) =
        callbackFlow<AuthResultWithValue<String>> {
            send(AuthResultWithValue.onLoad())
            try {
                firebaseAuth.verifyPasswordResetCode(resetCode)
                    .addOnSuccessListener { email ->
                        if (email != null) {
                            Log.d(TAG, "verifyRestPasswordCode: Successful")
                            trySend(AuthResultWithValue.onSuccess(email)).also {
                                authHandlerLogger("verifyRestPasswordCode", it)
                            }
                            close()

                        } else {
                            Log.d(TAG, "verifyRestPasswordCode: Success with null result")
                            trySend(AuthResultWithValue.onError(DEFAULT_ERROR_MESSAGE)).also {
                                authHandlerLogger("verifyRestPasswordCode", it)
                            }
                            close()
                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.d(TAG, "verifyRestPasswordCode: Failed", exception)
                        trySend(
                            AuthResultWithValue.onError(exception.message ?: DEFAULT_ERROR_MESSAGE)
                        ).also { authHandlerLogger("resetPassword", it) }
                        close()
                    }

            } catch (exception: Exception) {
                Log.d(TAG, "verifyAuthCode: Exception Caught: ", exception)
                send(AuthResultWithValue.onError(DEFAULT_ERROR_MESSAGE))
                close()
            }
            awaitClose {
                channel.close()
            }
        }

    internal fun resetPassword(resetCode: String, password: String) =
        callbackFlow {
            send(AuthResult.onLoad())
            try {
                firebaseAuth
                    .confirmPasswordReset(resetCode, password)
                    .addOnSuccessListener {
                        firebaseAuth.applyActionCode(resetCode)
                        Log.d(TAG, "resetPassword: Reset Password Successful")
                        trySend(AuthResult.onSuccess()).also {
                            authHandlerLogger("resetPassword", it)
                        }
                        close()
                    }
                    .addOnFailureListener { exception ->
                        Log.d(TAG, "resetPassword: Reset Password Failed", exception)
                        trySend(AuthResult.onError(exception.message ?: DEFAULT_ERROR_MESSAGE))
                            .also { authHandlerLogger("resetPassword", it) }
                        close()
                    }

            } catch (exception: Exception) {
                Log.d(TAG, "resetPassword: Exception Caught: ", exception)
                send(AuthResult.onError(DEFAULT_ERROR_MESSAGE))
                close()
            }
            awaitClose {
                channel.close()
            }
        }

    internal fun checkIfEmailExists(email: String) = callbackFlow {
        try {
            send(AuthResultWithValue.onLoad())

            firebaseAuth.fetchSignInMethodsForEmail(email)
                .addOnSuccessListener { queryResult ->
                    val doesEmailExist = queryResult.signInMethods?.let { signInMethods ->
                        (signInMethods.size > 0)
                    }
                    if (doesEmailExist != null) {
                        Log.d(TAG, "checkIfEmailExists: Email Found")
                        trySend(AuthResultWithValue.onSuccess(resultValue = doesEmailExist)).also {
                            authHandlerLogger("checkIfEmailExists", it)
                        }
                    } else {
                        Log.d(TAG, "checkIfEmailExists: SignInMethods is Null")
                        trySend(AuthResultWithValue.onError<Boolean>(message = DEFAULT_ERROR_MESSAGE))
                            .also { authHandlerLogger("checkIfEmailExists", it) }
                    }
                    close()
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "checkIfEmailExists: Failed", exception)
                    trySend(AuthResultWithValue.onError<Boolean>(DEFAULT_ERROR_MESSAGE)).also {
                        authHandlerLogger("checkIfEmailExists", it)
                    }
                    close()
                }
        } catch (exception: Exception) {
            Log.d(TAG, "checkIfEmailExists: Exception Caught: ", exception)
            send(AuthResultWithValue.onError(exception.localizedMessage ?: DEFAULT_ERROR_MESSAGE))
            close()
        }

        awaitClose {
            channel.close()
        }
    }

    internal fun confirmEmailVerification() = callbackFlow {
        try {
            send(AuthResultWithValue.onLoad())
            firebaseAuth.currentUser?.let { user ->
                user.reload()
                    .addOnSuccessListener {
                        user.isEmailVerified.let { isVerified ->
                            trySend(AuthResultWithValue.onSuccess(isVerified)).also { channelResult ->
                                authHandlerLogger("confirmEmailVerification", channelResult)
                            }
                            close()
                        }
                    }
                    .addOnFailureListener { exception ->
                        trySend(
                            AuthResultWithValue.onError<Boolean>(
                                exception.message ?: DEFAULT_ERROR_MESSAGE
                            )
                        ).also {
                            authHandlerLogger("confirmEmailVerification", it)
                        }
                        close()
                    }
            }
        } catch (exception: Exception) {
            Log.d(TAG, "confirmEmailVerification: Exception Caught: ", exception)
            send(AuthResultWithValue.onError(exception.localizedMessage ?: DEFAULT_ERROR_MESSAGE))
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
}
