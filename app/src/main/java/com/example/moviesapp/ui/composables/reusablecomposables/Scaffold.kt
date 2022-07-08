package com.example.moviesapp.ui.composables.reusablecomposables

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.moviesapp.ui.composables.spacerHeightMedium

@Composable
fun AppSignUpLoginScaffold(
    @DrawableRes headerIconRes: Int? = null,
    spacerHeight: Dp = spacerHeightMedium,
    headerTitle:String,
    headerSubtitle:String="",
    content: @Composable ColumnScope.() -> Unit
) {
    AppContentColumn {
        Column(
            verticalArrangement = Arrangement.spacedBy(spacerHeight)
        ) {
            if (headerIconRes != null) {
                Icon(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(200.dp),
                    painter = painterResource(headerIconRes),
                    contentDescription = null,
                    tint = MaterialTheme.colors.primary
                )
            }

            AppDefaultHeader(
                title = headerTitle,
                subtitle = headerSubtitle
            )

            content()
        }
    }
}