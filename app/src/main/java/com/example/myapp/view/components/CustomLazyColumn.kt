package com.example.myapp.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapp.ui.theme.LocalWindowSizeConstant
import com.example.myapp.ui.theme.customSpacing

/**
 * CustomLazyColumn - Composable function for creating adaptive vertical lazy lists.
 * 
 * This composable creates a LazyColumn with adaptive alignment, spacing, and padding
 * based on the current window size. It provides consistent vertical scrolling behavior
 * throughout the app with proper spacing between items and bottom padding for navigation.
 * 
 * @param content The composable content to be displayed in the vertical list
 * 
 * Usage:
 * ```
 * CustomLazyColumn {
 *     items(products) { product ->
 *         ProductCard(product = product)
 *     }
 * }
 * ```
 */
@Composable
fun CustomLazyColumn(content: LazyListScope.() -> Unit) {

    // Get the current window size constants for adaptive behavior
    val windowSizeConstant = LocalWindowSizeConstant.current

    LazyColumn(
        modifier = Modifier,
        horizontalAlignment = windowSizeConstant.horizontalAlignment, // Adaptive horizontal alignment
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.customSpacing.medium), // Consistent vertical spacing
        contentPadding = PaddingValues(
           top = MaterialTheme.customSpacing.medium, bottom = MaterialTheme.customSpacing.xxLarge // Top padding for content, bottom padding for navigation bar
        )
    ) {
        content() // Display the provided content
    }
}

