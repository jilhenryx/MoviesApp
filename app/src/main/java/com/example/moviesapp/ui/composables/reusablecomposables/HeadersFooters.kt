package com.example.moviesapp.ui.composables.reusablecomposables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.example.moviesapp.ui.theme.MoviesAppTheme

private const val TAG = "HeadersFooters"

@Composable
fun AppDefaultHeader(
    title: String,
    subtitle: String,
    subtitleColor: Color? = null
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.h4,
        )
        Text(
            text = subtitle,
            color = subtitleColor ?: MaterialTheme.colors.onSurface
        )
    }
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
    val annotatedText = buildAnnotatedString {
        append(text)
        if (link != null) {
            pushStringAnnotation(tag = link, annotation = "")
            withStyle(
                style = SpanStyle(
                    color = linkColor ?: MaterialTheme.colors.primary,
                    fontWeight = FontWeight.Bold
                )
            ) { append(" $link ") }
            pop()
        }
        append(otherText)
    }
    ClickableText(
        modifier = modifier,
        text = annotatedText,
        style = MaterialTheme.typography.body1,
        onClick = { offset ->
            val tag = link ?: ""
            annotatedText
                .getStringAnnotations(tag = tag, start = offset, end = offset)
                .firstOrNull()?.let {
                    onLinkCLick()
                }

        })
}

@Preview(showBackground = true)
@Composable
fun AppHeaderFooterPreview() {
    MoviesAppTheme {

    }
}