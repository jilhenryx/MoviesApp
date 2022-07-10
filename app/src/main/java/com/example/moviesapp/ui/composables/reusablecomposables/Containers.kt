package com.example.moviesapp.ui.composables.reusablecomposables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AppContentColumn(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .systemBarsPadding(),
    ) {
        content()
    }
}

