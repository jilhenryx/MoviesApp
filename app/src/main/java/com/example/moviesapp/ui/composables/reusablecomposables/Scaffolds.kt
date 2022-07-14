package com.example.moviesapp.ui.composables.reusablecomposables

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.moviesapp.R

@Composable
fun AppLoginFlowScaffold(
    modifier: Modifier = Modifier,
    @DrawableRes headerIconRes: Int? = null,
    headerTitle: String,
    headerSubtitle: String = "",
    headerSubtitleColor: Color? = null,
    contentSpacing: Dp? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    AppContentColumn(
        modifier = modifier,
        contentSpacing = contentSpacing
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
            subtitle = headerSubtitle,
            subtitleColor = headerSubtitleColor
        )
        content()
    }
}

@Composable
fun AppScaffold(
    modifier: Modifier = Modifier,
    isNavHome: Boolean = true,
    onUpButtonClick: () -> Unit,
    content: @Composable (paddingValues: PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .navigationBarsPadding(),
        topBar = {
            Box(
                modifier = Modifier
                    .height(80.dp)
                    .fillMaxWidth(),
            ) {
                TopAppBar(
                    modifier = Modifier.statusBarsPadding(),
                    elevation = 0.dp,
                    contentPadding = PaddingValues(horizontal = 6.dp),
                    backgroundColor = Color.Transparent
                ) {
                    if (!isNavHome) {
                        IconButton(onClick = onUpButtonClick) {
                            Icon(
                                painter = painterResource(R.drawable.arrow_back_ios),
                                contentDescription = "Navigate Back Button",
                                tint = MaterialTheme.colors.onBackground,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }
                }
            }
        },
        content = content
    )
}
