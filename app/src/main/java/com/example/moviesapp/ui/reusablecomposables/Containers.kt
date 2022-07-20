package com.example.moviesapp.ui.reusablecomposables

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AppContentColumn(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    val bottomPadding by animateDpAsState(
        WindowInsets.ime
            .exclude(WindowInsets.navigationBars)
            .asPaddingValues()
            .calculateBottomPadding()
    )
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = bottomPadding, start = 8.dp, end = 8.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center
    ) {
        content()
    }
}


