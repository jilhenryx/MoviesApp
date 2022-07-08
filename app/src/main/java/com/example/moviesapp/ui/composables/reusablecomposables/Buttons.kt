package com.example.moviesapp.ui.composables.reusablecomposables

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.moviesapp.R
import com.example.moviesapp.ui.theme.MoviesAppTheme

val buttonPaddingVertical = 14.dp

@Composable
fun AppButtonContent(
    title: String,
    textColor: Color? = null,
    iconTint: Color? = null,
    @DrawableRes leadingIcon: Int? = null,
    @DrawableRes trailingIcon: Int? = null,
) {
    Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
        if (leadingIcon != null) {
            Icon(
                modifier = Modifier.size(32.dp),
                painter = painterResource(leadingIcon),
                contentDescription = null,
                tint = iconTint ?: Color.Unspecified
            )
        }
        Text(
            text = title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = textColor ?: Color.White,
            style = MaterialTheme.typography.button
        )
        if (trailingIcon != null) {
            Icon(
                modifier = Modifier.size(32.dp),
                painter = painterResource(trailingIcon),
                contentDescription = null,
                tint = iconTint ?: Color.Unspecified
            )
        }
    }
}

enum class AppButtonType {
    FILLED,
    OUTLINED
}

@Composable
fun AppButton(
    modifier: Modifier = Modifier,
    type: AppButtonType,
    title: String,
    iconTint: Color? = null,
    @DrawableRes leadingIcon: Int? = null,
    @DrawableRes trailingIcon: Int? = null,
    onClick: () -> Unit
) {
    when (type) {
        AppButtonType.FILLED -> {
            Button(
                modifier = modifier
                    .fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                contentPadding = PaddingValues(vertical = buttonPaddingVertical),
                onClick = onClick,
            ) {
                AppButtonContent(
                    title = title,
                    iconTint = iconTint,
                    leadingIcon = leadingIcon,
                    trailingIcon = trailingIcon
                )
            }
        }
        AppButtonType.OUTLINED -> {
            OutlinedButton(
                modifier = modifier
                    .fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                contentPadding = PaddingValues(vertical = buttonPaddingVertical),
                border = BorderStroke(width = 2.dp, color = MaterialTheme.colors.primary),
                onClick = onClick,
            ) {
                AppButtonContent(
                    title = title,
                    textColor = MaterialTheme.colors.onSurface,
                    iconTint = iconTint,
                    leadingIcon = leadingIcon,
                    trailingIcon = trailingIcon
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppButtonsPreview() {
    MoviesAppTheme {
        AppButton(
            type = AppButtonType.OUTLINED,
            title = stringResource(id = R.string.login_google_button_text),
            leadingIcon = R.drawable.google_icon,
            onClick = { }
        )
    }
}