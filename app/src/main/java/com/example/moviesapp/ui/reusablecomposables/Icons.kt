package com.example.moviesapp.ui.reusablecomposables

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun AuthHeaderIcon(
    modifier: Modifier = Modifier,
    @DrawableRes iconRes: Int
) {
    Icon(
        modifier = modifier
            .size(200.dp),
        painter = painterResource(iconRes),
        contentDescription = null,
        tint = MaterialTheme.colors.primary
    )
}