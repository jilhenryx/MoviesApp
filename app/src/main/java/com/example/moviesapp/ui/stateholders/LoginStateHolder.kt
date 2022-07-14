package com.example.moviesapp.ui.stateholders

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.example.moviesapp.ui.constants.REGEX_EMAIL_PATTERN
import com.example.moviesapp.ui.constants.TextFieldType
import com.example.moviesapp.ui.constants.isValueCorrect

private const val TAG = "LoginStateHolder"
private const val EMAIL_STATE_KEY = "$TAG.EMAIL_STATE_KEY"
private const val PASSWORD_STATE_KEY = "$TAG.PASSWORD_STATE_KEY"
private const val SUBTITLE_STATE_KEY = "$TAG.SUBTITLE_STATE_KEY"
private const val SUB_COLOR_STATE_KEY = "$TAG.SUB_COLOR_STATE_KEY"

class LoginStateHolder() {
    private val emailState = mutableStateOf("")
    private var passwordState = mutableStateOf("")
    internal val subtitleState = mutableStateOf("")
    internal val subtitleColorState = mutableStateOf<Color?>(null)

    internal val email: String
        get() = emailState.value
    internal val password: String
        get() = passwordState.value

    internal fun onValueChange(value: String, fieldType: TextFieldType) {
        when (fieldType) {
            TextFieldType.EMAIL -> emailState.value = value
            TextFieldType.PASSWORD -> passwordState.value = value
            else -> {
                Log.d(TAG, "LoginScreen: Type Not Recognized Here")
            }
        }
    }

    internal fun verifyEmail(): Boolean = email.isValueCorrect(REGEX_EMAIL_PATTERN)

    internal fun restoreState(restoreMap: Map<String, Any?>) {
        emailState.value = restoreMap[EMAIL_STATE_KEY] as String
        passwordState.value = restoreMap[PASSWORD_STATE_KEY] as String
        subtitleState.value = restoreMap[SUBTITLE_STATE_KEY] as String
        ((restoreMap[SUB_COLOR_STATE_KEY] as? Int)
            ?.let { colorStateInt -> Color(colorStateInt) })
            .also { stateValue -> subtitleColorState.value = stateValue }
    }

    internal fun areAllFieldsCorrect(): Boolean =
        email.isValueCorrect(REGEX_EMAIL_PATTERN) && password.length >= 8

}

val LoginStateSaver = mapSaver(
    save = {
        mapOf(
            EMAIL_STATE_KEY to it.email,
            PASSWORD_STATE_KEY to it.password,
            SUBTITLE_STATE_KEY to it.subtitleState.value,
            SUB_COLOR_STATE_KEY to it.subtitleColorState.value?.toArgb()
        )
    },
    restore = { map ->
        LoginStateHolder().apply {
            restoreState(map)
        }
    }
)


@Composable
fun rememberLoginStateHolder() =
    rememberSaveable(saver = LoginStateSaver) {
        LoginStateHolder()
    }