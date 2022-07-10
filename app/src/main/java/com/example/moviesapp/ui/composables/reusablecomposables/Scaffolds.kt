package com.example.moviesapp.ui.composables.reusablecomposables

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.moviesapp.R

@Composable
fun AppSignUpLoginScaffold(
    @DrawableRes headerIconRes: Int? = null,
    headerTitle: String,
    headerSubtitle: String = "",
    isNavHome: Boolean = false,
    onNavBackClick: () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit
) {
    val bottomPadding by animateDpAsState(
        WindowInsets.ime
            .exclude(WindowInsets.navigationBars)
            .asPaddingValues()
            .calculateBottomPadding()
    )
    AppContentColumn {
        TopAppBar(
            backgroundColor = Color.Transparent,
            elevation = 0.dp,
            contentPadding = PaddingValues(12.dp)
        ) {
            if (!isNavHome) {
                IconButton(onClick = { onNavBackClick() }) {
                    Icon(
                        painter = painterResource(R.drawable.arrow_back_ios),
                        contentDescription = "Navigate Back Button",
                        tint = MaterialTheme.colors.onBackground,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = bottomPadding)
                .padding(horizontal = 12.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(32.dp)
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
}