package com.example.myapp.view.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.myapp.ui.theme.LocalWindowSizeConstant

/**
 * PaddedSection - Composable function for creating sections with adaptive padding and alignment.
 * 
 * This composable creates a Column container with adaptive horizontal padding and alignment
 * based on the current window size. It's useful for creating consistent, responsive
 * content sections throughout the app.
 * 
 * @param content The composable content to be displayed within the padded section
 * 
 * Usage:
 * ```
 * PaddedSection {
 *     Text("Content goes here")
 *     Button("Click me") { }
 * }
 * ```
 */
@Composable
fun PaddedSection(content: @Composable ColumnScope.() -> Unit) {

    // Get the current window size constants for adaptive behavior
    val windowSizeConstant = LocalWindowSizeConstant.current

    Column(
        modifier = Modifier
            .fillMaxWidth() // Take full available width
            .padding(horizontal = windowSizeConstant.contentPadding), // Apply adaptive horizontal padding
        horizontalAlignment = windowSizeConstant.horizontalAlignment, // Use adaptive horizontal alignment
        content = content // Display the provided content
    )
}
