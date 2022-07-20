package com.example.moviesapp.ui.reusablecomposables

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.moviesapp.R

@Composable
fun AuthAppBar(
    modifier: Modifier = Modifier,
    showUpIndicator: Boolean = false,
    onUpButtonClick: () -> Unit = {}
) {
    TopAppBar(
        modifier = modifier
            .height(80.dp)
            .fillMaxWidth(),
        backgroundColor = Color.Transparent,
        elevation = 0.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding(),
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.animation.AnimatedVisibility(
                modifier = Modifier.align(Alignment.CenterStart),
                visible = showUpIndicator,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                IconButton(onClick = onUpButtonClick) {
                    Icon(
                        painter = painterResource(R.drawable.arrow_back_ios),
                        contentDescription = "Navigate Back Button",
                        tint = MaterialTheme.colors.onBackground,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
            Icon(
                modifier = Modifier.align(Alignment.Center),
                painter = painterResource(R.drawable.play_arrow),
                contentDescription = null,
                tint = MaterialTheme.colors.primary
            )
        }
    }
}