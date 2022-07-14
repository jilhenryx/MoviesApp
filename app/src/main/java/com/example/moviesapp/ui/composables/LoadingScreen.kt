package com.example.moviesapp.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.moviesapp.R
import com.example.moviesapp.ui.theme.MoviesAppTheme

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background.copy(alpha = 0.7f)),
        contentAlignment = Alignment.Center
    ) {
        LinearProgressIndicator(
            color = MaterialTheme.colors.secondary
        )
    }
}

@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
fun LoadingScreenPreview() {
    MoviesAppTheme {
        LoadingScreen()
    }
}