package com.example.moviesapp.core

object Messages {
    internal const val DEFAULT_ERROR_MESSAGE = "Oops! Something went wrong. Please try again later"
    internal const val EMPTY_FIELDS_MESSAGE =
        "Please confirm that no field is empty and you entered the correct details"
    internal const val DEFAULT_LOGIN_ERROR_MESSAGE =
        "Unable to Login. The email or password entered is not correct"
    internal const val LOGIN_ERROR_NO_USER_FOUND_MESSAGE =
        "Unable to Login. The email entered is not registered. Create an account to proceed"
    internal const val DEFAULT_SIGN_UP_ERROR_MESSAGE =
        "Unable to create a new account. The email already exists"
    internal const val INVALID_EMAIL = "Email is Invalid"
    internal const val EMAIL_NOT_VERIFIED_MESSAGE =
        "Your email is yet to be verified. Click on retry to get a verification link sent to your mailbox"
    internal const val EMAIL_DOES_NOT_EXIST_MESSAGE =
        "This email does not have a registered account"
    internal const val GOOGLE_SIGN_IN_ERROR_MESSAGE =
        "Unable to login with Google at this time. Please login with email and password or create an account"
}