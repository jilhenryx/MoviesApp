package com.example.moviesapp.ui.composables.reusablecomposables

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AppContentColumn(
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.Center,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .systemBarsPadding()
            .padding(horizontal = 12.dp),
        verticalArrangement = verticalArrangement
    ) {
        content()
    }
}