package com.example.moviesapp.ui.reusablecomposables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
private fun AuthDialogContent() {

}

@Composable
private fun AuthDialogButton(modifier: Modifier = Modifier, text: String, onClick: () -> Unit) {
    Button(
        modifier = modifier.fillMaxSize(),
        onClick = onClick
    ) {
        Text(
            text = text,
        )
    }
}