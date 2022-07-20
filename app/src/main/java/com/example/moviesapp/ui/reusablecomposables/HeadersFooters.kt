package com.example.moviesapp.ui.reusablecomposables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.moviesapp.R

private const val TAG = "HeadersFooters"

@Composable
fun AppDefaultHeader(
    title: String,
    subtitle: String,
    isSubtitleError: Boolean = false
) {
    Text(
        text = title,
        style = MaterialTheme.typography.h4,
    )
    Spacer(modifier = Modifier.height(1.dp))
    Text(
        text = subtitle,
        color =
        if (isSubtitleError) MaterialTheme.colors.error else MaterialTheme.colors.onSurface
    )
}

@Composable
fun AuthScreensHeader(
    title: String,
    subtitle: String,
    isSubtitleError: Boolean = false
) {
    Text(
        text = title,
        style = MaterialTheme.typography.h4,
    )
    Spacer(modifier = Modifier.height(1.dp))
    Text(
        text = subtitle,
        color =
        if (isSubtitleError) MaterialTheme.colors.error else MaterialTheme.colors.onSurface
    )
}

@Composable
fun AppDefaultFooter(
    modifier: Modifier = Modifier,
    text: String,
    link: String? = null,
    otherText: String = "",
    linkColor: Color? = null,
    onLinkCLick: () -> Unit
) {

    Row(modifier = modifier, horizontalArrangement = Arrangement.Center) {
        Text(text = text)

        if (link != null) {
            Spacer(modifier = Modifier.width(2.dp))
            Text(
                modifier = Modifier
                    .clickable(onClick = onLinkCLick),
                text = link,
                color = linkColor ?: MaterialTheme.colors.primary,
                fontWeight = FontWeight.Bold
            )
        }

        if (otherText.isNotBlank()) {
            Spacer(modifier = Modifier.width(2.dp))
            Text(text = otherText)
        }
    }
}

@Composable
fun RetryEmailText(
    modifier: Modifier = Modifier,
    timerText: String,
    linkColor: Color? = null,
    onRetryClicked: () -> Unit
) {
    AppDefaultFooter(
        modifier = modifier,
        text = stringResource(R.string.no_email_text),
        link = stringResource(R.string.no_email_text_retry),
        linkColor = linkColor,
        otherText = if (timerText.isNotBlank()) "in $timerText" else "",
        onLinkCLick = onRetryClicked
    )
}
