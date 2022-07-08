package com.example.moviesapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import com.example.moviesapp.ui.composables.App
import com.example.moviesapp.ui.composables.SystemUiTheme
import com.example.moviesapp.ui.composables.WelcomeScreen
import com.example.moviesapp.ui.theme.MoviesAppTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
//        val windowInsetsController =
//            WindowCompat.getInsetsController(window, window.decorView)

        setContent {
            MoviesAppTheme {
                SystemUiTheme()
                App(
                    isDarkMode = isSystemInDarkTheme()
                )
            }
        }
    }
}