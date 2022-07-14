package com.example.moviesapp.ui

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.core.content.edit
import androidx.core.view.WindowCompat
import com.example.moviesapp.R
import com.example.moviesapp.ui.composables.App
import com.example.moviesapp.ui.constants.PACKAGE_NAME
import com.example.moviesapp.ui.theme.MoviesAppTheme
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import dagger.hilt.android.AndroidEntryPoint

private const val SHARED_PREFS_KEY = "$PACKAGE_NAME.SHARED_PREFS_KEY"
private const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_MoviesApp)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        //Check if user is launching the app for the first time
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        val firstLaunch = sharedPref.getBoolean(SHARED_PREFS_KEY, true)
        sharedPref.edit {
            putBoolean(SHARED_PREFS_KEY, false)
        }
        setContent {
            MoviesAppTheme {
                //SystemUiTheme()
                App(
                    isFirstLaunch = firstLaunch,
                    isDarkMode = isSystemInDarkTheme()
                )

            }
        }
    }

    override fun onStart() {
        super.onStart()
        FirebaseDynamicLinks.getInstance()
            .getDynamicLink(intent)
            .addOnSuccessListener {
                var deepLink = Uri.parse("")
                if (it != null) {
                    deepLink = it.link
                    Log.d(TAG, "onStart: $deepLink")
                }

            }
            .addOnFailureListener {
                Log.w(TAG, "getDynamicLink:onFailure", it)
            }
    }
}