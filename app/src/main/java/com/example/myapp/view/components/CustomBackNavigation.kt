package com.example.myapp.view.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable

/**
 * CustomBackNavigation - Composable function for back navigation button.
 * 
 * This composable creates a back navigation button using Material Design 3
 * styling. It uses the AutoMirrored arrow back icon which automatically
 * flips direction for RTL (right-to-left) languages.
 * 
 * @param onNavigateBack Callback function to handle back navigation
 * 
 * Usage:
 * ```
 * CustomBackNavigation(onNavigateBack = { navController.popBackStack() })
 * ```
 */
@Composable
fun CustomBackNavigation(onNavigateBack: () -> Unit) {
    IconButton(onClick = onNavigateBack) {
        Icon(
            Icons.AutoMirrored.Filled.ArrowBack, // Auto-mirrored for RTL support
            contentDescription = "Back" // Accessibility description
        )
    }
}