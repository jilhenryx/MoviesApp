package com.example.moviesapp.ui.reusablecomposables

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.moviesapp.R
import com.example.moviesapp.ui.composablehelpers.ComposeConstants.SMALL_TEXT_STYLE_SIZE

@Composable
fun AppOutlinedTextField(
    modifier: Modifier = Modifier,
    value: String,
    label: String,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    onValueChange: (value: String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        singleLine = true,
        textStyle = TextStyle(fontSize = SMALL_TEXT_STYLE_SIZE),
        label = { Text(text = label) },
        isError = isError,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        onValueChange = onValueChange
    )
}

@Composable
fun AppOutlinedTextFieldPassword(
    modifier: Modifier = Modifier,
    value: String,
    label: String,
    showCharacters: Boolean = false,
    isError: Boolean = false,
    @DrawableRes visibilityOnIcon: Int? = null,
    @DrawableRes visibilityOffIcon: Int? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    onValueChange: (value: String) -> Unit,
    onPasswordToggle: () -> Unit = {}
) {
    val iconIsSet = visibilityOffIcon != null && visibilityOnIcon != null

    val passwordIcon by remember(showCharacters) {
        derivedStateOf {
            if (showCharacters) PasswordIcon.IconVisibilityOff(
                visibilityOffIcon!!,
                R.string.password_visible_icon_description
            )
            else PasswordIcon.IconVisibilityOn(
                visibilityOnIcon!!,
                R.string.password_not_visible_icon_description
            )
        }
    }


    OutlinedTextField(
        modifier = modifier,
        value = value,
        textStyle = TextStyle(fontSize = SMALL_TEXT_STYLE_SIZE),
        singleLine = true,
        trailingIcon = {
            if (iconIsSet) {
                IconButton(onClick = onPasswordToggle) {
                    Icon(
                        painter = painterResource(passwordIcon.iconId),
                        contentDescription = stringResource(passwordIcon.iconDescriptionId)
                    )

                }
            }
        },
        label = { Text(text = label) },
        visualTransformation =
        if (showCharacters) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        onValueChange = onValueChange,
        isError = isError,
    )
}

sealed class PasswordIcon(
    @DrawableRes open val iconId: Int,
    @StringRes open val iconDescriptionId: Int
) {
    data class IconVisibilityOn(
        @DrawableRes override val iconId: Int,
        @StringRes override val iconDescriptionId: Int
    ) : PasswordIcon(iconId, iconDescriptionId)

    data class IconVisibilityOff(
        @DrawableRes override val iconId: Int,
        @StringRes override val iconDescriptionId: Int
    ) : PasswordIcon(iconId, iconDescriptionId)
}

enum class TextFieldType {
    FIRSTNAME,
    LASTNAME,
    EMAIL,
    PASSWORD,
    CONFIRM_PASSWORD
}