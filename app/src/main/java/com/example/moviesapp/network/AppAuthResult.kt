package com.example.moviesapp.network

class AppAuthResult private constructor(
    val state: ResultState,
    val errorMessage: String? = null
) {
    enum class ResultState {
        LOADING,
        SUCCESS,
        ERROR
    }

    companion object {
        fun onLoad(): AppAuthResult = AppAuthResult(state = ResultState.LOADING)

        fun onSuccess(): AppAuthResult = AppAuthResult(state = ResultState.SUCCESS)

        fun onError(message: String) =
            AppAuthResult(state = ResultState.ERROR, errorMessage = message)

    }
}
