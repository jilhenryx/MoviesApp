package com.example.moviesapp.ui.composables.reusablecomposables

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
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
import com.example.moviesapp.ui.composables.LoadingScreen

@Composable
fun AppLoginFlowScaffold(
    modifier: Modifier = Modifier,
    @DrawableRes headerIconRes: Int? = null,
    headerTitle: String,
    headerSubtitle: String = "",
    isSubtitleError: Boolean = false,
    isContentLoading: Boolean = false,
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
            isSubtitleError = isSubtitleError
        )
        content()
    }

    AnimatedVisibility(isContentLoading) {
        LoadingScreen(modifier = Modifier)
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
            TopAppBar(
                modifier = Modifier
                    .height(80.dp)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 6.dp),
                backgroundColor = Color.Transparent
            ) {
                Row(
                    modifier = Modifier.statusBarsPadding()
                ) {
                    AnimatedVisibility(!isNavHome) {
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
