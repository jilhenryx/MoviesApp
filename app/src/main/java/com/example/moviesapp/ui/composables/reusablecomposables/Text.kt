package com.example.moviesapp.ui.composables.reusablecomposables

import androidx.annotation.DrawableRes
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.moviesapp.R
import com.example.moviesapp.ui.theme.MoviesAppTheme

@Composable
fun AppOutlinedTextField(
    modifier: Modifier = Modifier,
    value: String,
    label: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    onValueChange: (value: String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        singleLine = true,
        textStyle = TextStyle(fontSize = 18.sp),
        label = { Text(text = label) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        onValueChange = onValueChange
    )
}

@Composable
fun AppOutlinedTextFieldPassword(
    modifier: Modifier = Modifier,
    value: String,
    label: String,
    showCharacters: Boolean = false,
    @DrawableRes visibilityOnIcon: Int? = null,
    @DrawableRes visibilityOffIcon: Int? = null,
    onValueChange: (value: String) -> Unit,
    onPasswordToggle: () -> Unit = {}
) {
    val iconIsSet = visibilityOffIcon != null && visibilityOnIcon != null

    val passwordIcon by remember(showCharacters) {
        derivedStateOf {
            if (showCharacters) visibilityOffIcon
            else visibilityOnIcon
        }
    }
    val passwordIconDescription by remember(showCharacters) {
        derivedStateOf {
            if (showCharacters) R.string.password_visible_icon_description
            else R.string.password_not_visible_icon_description
        }
    }


    OutlinedTextField(
        modifier = modifier,
        value = value,
        textStyle = TextStyle(fontSize = 18.sp),
        singleLine = true,
        trailingIcon = {
            if (iconIsSet) {
                IconButton(onClick = onPasswordToggle) {
                    Icon(
                        painter = painterResource(passwordIcon!!),
                        contentDescription = stringResource(passwordIconDescription)
                    )

                }
            }
        },
        label = { Text(text = label) },
        visualTransformation =
        if (showCharacters) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        onValueChange = onValueChange
    )
}

@Composable
fun RetryEmailText(
    modifier: Modifier = Modifier,
    timerText: String,
    onRetryClicked: () -> Unit
) {
    AppDefaultFooter(
        modifier = modifier,
        text = stringResource(R.string.no_email_text),
        link = stringResource(R.string.no_email_text_retry),
        otherText = "in $timerText",
        onLinkCLick = onRetryClicked
    )
}

@Preview(showBackground = true)
@Composable
fun AppTextFieldsPreview() {
    MoviesAppTheme {

    }
}