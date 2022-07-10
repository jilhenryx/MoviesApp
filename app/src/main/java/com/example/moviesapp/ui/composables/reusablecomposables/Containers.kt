package com.example.moviesapp.ui.composables.reusablecomposables

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.moviesapp.ui.composables.spacedByLarge

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
            .padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Column(
            modifier = Modifier
                .padding(bottom = bottomPadding)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(spacedByLarge)
        ) {
            content()
        }
    }
}

