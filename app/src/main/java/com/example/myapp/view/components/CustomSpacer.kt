package com.example.myapp.view.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapp.ui.theme.LocalWindowSizeConstant
import com.example.myapp.ui.theme.customSpacing

/**
 * CustomSpacer - Composable function for creating adaptive vertical spacing.
 * 
 * This composable creates a spacer with height based on the current window size,
 * providing consistent and responsive spacing throughout the app. It uses the
 * contentVerticalPadding from the window size constants for adaptive behavior.
 * 
 * @param modifier Optional modifier to apply to the spacer
 * 
 * Usage:
 * ```
 * CustomSpacer() // Uses default adaptive spacing
 * CustomSpacer(modifier = Modifier.padding(MaterialTheme.customSpacing.medium)) // With additional padding
 * ```
 */
@Composable
fun CustomSpacer(modifier: Modifier = Modifier) {

    // Get the current window size constants for adaptive spacing
    val windowSizeConstant = LocalWindowSizeConstant.current

    // Create a spacer with adaptive vertical padding height
    Spacer(modifier = modifier.height(windowSizeConstant.contentVerticalPadding))
}