package com.example.moviesapp.ui.stateholders

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.example.moviesapp.ui.constants.REGEX_EMAIL_PATTERN
import com.example.moviesapp.ui.constants.TextFieldType
import com.example.moviesapp.ui.constants.isValueCorrect

private const val TAG = "SignUpStateHolder"
private const val FIRSTNAME_STATE_KEY = "$TAG.FIRSTNAME_STATE_KEY"
private const val LASTNAME_STATE_KEY = "$TAG.LASTNAME_STATE_KEY"
private const val EMAIL_STATE_KEY = "$TAG.EMAIL_STATE_KEY"
private const val PASSWORD_STATE_KEY = "$TAG.PASSWORD_STATE_KEY"
private const val CONFIRM_PASS_STATE_KEY = "$TAG.CONFIRM_PASS_STATE_KEY"
private const val SUBTITLE_STATE_KEY = "$TAG.SUBTITLE_STATE_KEY"
private const val SUB_COLOR_STATE_KEY = "$TAG.SUB_COLOR_STATE_KEY"

class SignUpStateHolder {
    private val firstnameState = mutableStateOf("")
    private val lastnameState = mutableStateOf("")
    private val emailState = mutableStateOf("")
    private val passwordState = mutableStateOf("")
    private val confirmPasswordState = mutableStateOf("")
    internal val subtitleState = mutableStateOf("")
    internal val subtitleColorState = mutableStateOf<Color?>(null)

    internal val firstname: String
        get() = firstnameState.value
    internal val lastname: String
        get() = lastnameState.value
    internal val email: String
        get() = emailState.value
    internal val password: String
        get() = passwordState.value
    internal val confirmPassword: String
        get() = confirmPasswordState.value


    internal fun onValueChange(value: String, fieldType: TextFieldType) {
        when (fieldType) {
            TextFieldType.FIRSTNAME -> firstnameState.value = value
            TextFieldType.LASTNAME -> lastnameState.value = value
            TextFieldType.EMAIL -> emailState.value = value
            TextFieldType.PASSWORD -> passwordState.value = value
            TextFieldType.CONFIRM_PASSWORD -> confirmPasswordState.value = value
        }
    }

    internal fun areAllFieldsCorrect(): Boolean =
        firstname.isNotBlank()
                && lastname.isNotBlank()
                && email.isValueCorrect(REGEX_EMAIL_PATTERN)
                && password.length >= 8
                && confirmPassword == password


    internal fun restoreState(restoreMap: Map<String, Any?>) {
        firstnameState.value = restoreMap[FIRSTNAME_STATE_KEY] as String
        lastnameState.value = restoreMap[LASTNAME_STATE_KEY] as String
        emailState.value = restoreMap[EMAIL_STATE_KEY] as String
        passwordState.value = restoreMap[PASSWORD_STATE_KEY] as String
        confirmPasswordState.value = restoreMap[CONFIRM_PASS_STATE_KEY] as String
        subtitleState.value = restoreMap[SUBTITLE_STATE_KEY] as String
        ((restoreMap[SUB_COLOR_STATE_KEY] as? Int)
            ?.let { colorStateInt -> Color(colorStateInt) })
            .also { stateValue -> subtitleColorState.value = stateValue }
    }

}

val SignUpStateSaver = mapSaver(
    save = {
        mapOf(
            FIRSTNAME_STATE_KEY to it.firstname,
            LASTNAME_STATE_KEY to it.lastname,
            EMAIL_STATE_KEY to it.email,
            PASSWORD_STATE_KEY to it.password,
            CONFIRM_PASS_STATE_KEY to it.confirmPassword,
            SUBTITLE_STATE_KEY to it.subtitleState.value,
            SUB_COLOR_STATE_KEY to it.subtitleColorState.value?.toArgb()
        )
    },
    restore = { map ->
        SignUpStateHolder().apply {
            restoreState(map)
        }
    }
)

@Composable
fun rememberSignUpStateHolder() = rememberSaveable(saver = SignUpStateSaver) {
    SignUpStateHolder()
}