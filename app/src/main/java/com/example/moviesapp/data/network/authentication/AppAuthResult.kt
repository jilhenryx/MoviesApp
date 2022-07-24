package com.example.moviesapp.data.network.authentication

interface AppAuthResult {
    val errorMessage: String
    val state: ResultState

    enum class ResultState {
        LOADING,
        SUCCESS,
        ERROR
    }
}

class AuthResult private constructor(
    override val state: AppAuthResult.ResultState,
    override val errorMessage: String = ""
) : AppAuthResult {
    companion object {
        fun onLoad() = AuthResult(state = AppAuthResult.ResultState.LOADING)

        fun onSuccess() = AuthResult(state = AppAuthResult.ResultState.SUCCESS)

        fun onError(message: String) =
            AuthResult(state = AppAuthResult.ResultState.ERROR, errorMessage = message)
    }
}

class AuthResultWithValue<T> private constructor(
    override val state: AppAuthResult.ResultState,
    override val errorMessage: String = "",
    val resultValue: T? = null
) : AppAuthResult {
    companion object {
        fun <T> onLoad(): AuthResultWithValue<T> =
            AuthResultWithValue(state = AppAuthResult.ResultState.LOADING)

        fun <T> onSuccess(resultValue: T) =
            AuthResultWithValue(
                state = AppAuthResult.ResultState.SUCCESS,
                resultValue = resultValue
            )

        fun <T> onError(message: String): AuthResultWithValue<T> =
            AuthResultWithValue(state = AppAuthResult.ResultState.ERROR, errorMessage = message)
    }
}
