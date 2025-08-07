package com.example.myapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.CompositionLocalProvider
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.example.myapp.ui.theme.MyAppTheme
import com.example.myapp.ui.theme.customSpacing
import com.example.myapp.view.screens.SplashScreenApp
import com.example.myapp.view.utils.LocalWindowSizeClass

/**
 * MainActivity - The main entry point of the application.
 *
 * This activity serves as the root of the app and handles the initial setup,
 * including splash screen, edge-to-edge display, and theme configuration.
 * It also manages the window size class for responsive design.
 *
 * Key responsibilities:
 * - Optionally manages the system-level splash screen (see commented code).
 * - Enables edge-to-edge content drawing for a modern, immersive UI.
 * - Provides the window size class to the Compose hierarchy for adaptive layouts.
 * - Applies the app's theme and renders the main navigation or splash screen.
 */
class MainActivity : ComponentActivity() {

    // Controls whether the system splash screen should remain visible.
    // Set to false to dismiss the splash screen when your custom splash is done.
    private var keepSplashOn = true

    /**
     * Called when the activity is first created.
     *
     * - Optionally installs and manages the system splash screen (commented out).
     * - Enables edge-to-edge display for a modern look.
     * - Sets up the Compose content, providing the window size class and theme.
     * - Renders either the splash screen or the main navigation screen, depending on app state.
     *
     * @param savedInstanceState The saved instance state bundle, if any.
     */
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        // Optionally handle the system-level splash screen (currently commented out).
        // installSplashScreen().setKeepOnScreenCondition { keepSplashOn }

        // Enable edge-to-edge display for modern Android UI.
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        setContent {
            // Calculate window size class for responsive design (compact, medium, expanded).
            val windowSizeClass = calculateWindowSizeClass(this)

            // Provide window size class to the composition tree for adaptive layouts.
            CompositionLocalProvider(LocalWindowSizeClass provides windowSizeClass) {
                // Apply the app's theme (colors, typography, shapes).
                MyAppTheme {
                    // Main surface with tonal elevation for depth and shadow.
                    Surface(tonalElevation = MaterialTheme.customSpacing.baseSmall) {
                        // Display your custom splash screen logic.
                        // Once the splash screen is finished, set keepSplashOn to false to dismiss the system splash.
                        SplashScreenApp {
                            keepSplashOn = false
                        }
                    }
                }
            }
        }
    }
}
