package com.example.moviesapp.core

interface AppAuthStatus {
    val errorMessage: String
}

sealed class AuthStatusWithValue<T : Any?> : AppAuthStatus {
    open val statusValue: T? = null
    override val errorMessage: String = ""

    class InProgress<T> : AuthStatusWithValue<T>()
    class Success<T>(override val statusValue: T) : AuthStatusWithValue<T>()
    class Failed<T>(override val errorMessage: String) : AuthStatusWithValue<T>()
}

sealed class AuthStatus : AppAuthStatus {
    override val errorMessage: String = ""

    object InProgress : AuthStatus()
    object Success : AuthStatus()
    class Failed(override val errorMessage: String) : AuthStatus()
}