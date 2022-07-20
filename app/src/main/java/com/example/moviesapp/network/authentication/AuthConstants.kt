package com.example.moviesapp.network.authentication


object AuthConstants {
    private const val AUTH_DEEP_LINK_PATH = "https://moviesappng.page.link"
    internal const val VERIFY_EMAIL_URL_PATH = "$AUTH_DEEP_LINK_PATH/usermgmt/verifyemail"
    internal const val RESET_PASSWORD_URL_PATH = "$AUTH_DEEP_LINK_PATH/usermgmt/resetpassword"
    internal const val VERIFY_EMAIL_DEEP_LINK_URI = "$AUTH_DEEP_LINK_PATH/?link={link}"
    internal const val RESET_PASSWORD_DEEP_LINK_URI = "$AUTH_DEEP_LINK_PATH?link={link}"

    internal const val EMAIL_QUERY_KEY = "email"

    internal const val DEEP_LINK_MODE_QUERY_KEY = "mode"
    internal const val DEEP_LINK_MODE_VERIFY_EMAIL = "verifyEmail"
    internal const val DEEP_LINK_MODE_RESET_PASSWORD = "resetPassword"
}
