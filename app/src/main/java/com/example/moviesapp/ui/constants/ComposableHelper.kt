package com.example.moviesapp.ui.constants

import android.util.Patterns
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import javax.annotation.RegEx

enum class TextFieldType {
    FIRSTNAME,
    LASTNAME,
    EMAIL,
    PASSWORD,
    CONFIRM_PASSWORD
}

internal val REGEX_EMAIL_PATTERN: String = Patterns.EMAIL_ADDRESS.pattern()

internal fun String.isValueCorrect(@RegEx pattern: String): Boolean =
    Regex(pattern).matches(this)

internal fun isFieldCorrect(value: String, fieldType: TextFieldType): Boolean =
    when (fieldType) {
        TextFieldType.FIRSTNAME, TextFieldType.LASTNAME -> value.isNotBlank()
        TextFieldType.EMAIL -> value.isValueCorrect(REGEX_EMAIL_PATTERN)
        TextFieldType.PASSWORD -> value.length >= 8
        else -> false
    }

internal val SMALL_SPACING = 12.dp
internal val MEDIUM_SPACING = 32.dp
internal val LARGE_SPACING = 50.dp

internal val SMALL_TEXT_STYLE_SIZE = 18.sp

internal const val DEFAULT_ERROR_MESSAGE = "Oops! Something went wrong. Please try again later"
internal const val DEFAULT_EMPTY_FIELDS_MESSAGE =
    "Please confirm that no field is empty and you entered the correct details"
