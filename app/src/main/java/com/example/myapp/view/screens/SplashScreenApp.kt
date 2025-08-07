package com.example.myapp.view.screens

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.myapp.ui.theme.MyAppTheme
import com.example.myapp.ui.theme.customSpacing
import com.example.myapp.view.screens.bottom_bar.MainScreen

/**
 * SplashScreenApp - Root composable for handling the splash screen flow.
 *
 * This composable manages the transition between the animated splash screen and the main app content.
 * - Shows the [AnimatedSplashScreen] on launch.
 * - Once the splash animation is finished, it triggers [onSplashFinished] and displays the main app UI.
 *
 * @param onSplashFinished Callback invoked when the splash animation completes.
 */
@Composable
fun SplashScreenApp(onSplashFinished: () -> Unit) {
    // State to control whether to show the splash screen or the main app content
    var showSplash by remember { mutableStateOf(true) }

    if (showSplash) {
        // Show the animated splash screen. When finished, update state and notify parent.
        AnimatedSplashScreen {
            showSplash = false
            onSplashFinished()
        }
    } else {
        // Display the main screen of the application after splash is done
        MyAppTheme {
            Surface(tonalElevation = MaterialTheme.customSpacing.baseSmall) {
                MainScreen()
            }
        }
    }
}
