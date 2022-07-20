package com.example.moviesapp.main

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Surface
import androidx.core.content.edit
import androidx.core.view.WindowCompat
import com.example.moviesapp.ui.theme.MoviesAppTheme
import dagger.hilt.android.AndroidEntryPoint

private const val SHARED_PREFS_KEY = "com.example.MainActivity.SHARED_PREFS_KEY"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        //Check if user is launching the app for the first time
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        val firstLaunch = sharedPref.getBoolean(SHARED_PREFS_KEY, true)
        if (firstLaunch) {
            sharedPref.edit { putBoolean(SHARED_PREFS_KEY, false) }
        }
        setContent {
            MoviesAppTheme {
                //SystemUiTheme()
                Surface {
                    App(
                        isFirstLaunch = firstLaunch,
                        isDarkMode = isSystemInDarkTheme()
                    )
                }

            }
        }
    }
}