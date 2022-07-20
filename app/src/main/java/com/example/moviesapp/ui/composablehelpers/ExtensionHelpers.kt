package com.example.moviesapp.ui.composablehelpers

import android.util.Patterns
import com.example.moviesapp.ui.reusablecomposables.TextFieldType
import javax.annotation.RegEx

private val REGEX_EMAIL_PATTERN: String = Patterns.EMAIL_ADDRESS.pattern()

private fun String.isValueCorrect(@RegEx pattern: String): Boolean =
    Regex(pattern).matches(this)

internal fun String.isFieldCorrect(fieldType: TextFieldType): Boolean =
    when (fieldType) {
        TextFieldType.FIRSTNAME, TextFieldType.LASTNAME -> this.isNotBlank()
        TextFieldType.EMAIL -> this.isValueCorrect(REGEX_EMAIL_PATTERN)
        TextFieldType.PASSWORD -> this.length >= 8
        else -> false
    }