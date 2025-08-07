package com.example.myapp.view.components

import androidx.annotation.StringRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

/**
 * AppBarTitle - Composable function for displaying app bar titles.
 * 
 * This composable provides a flexible way to display titles in app bars,
 * supporting both string resources and direct string values. It uses
 * Material Design 3 typography for consistent styling.
 * 
 * @param title Optional string resource ID for the title
 * @param titleStr Optional direct string value for the title
 * 
 * Usage:
 * ```
 * AppBarTitle(title = R.string.my_title)
 * AppBarTitle(titleStr = "Custom Title")
 * ```
 */
@Composable
fun AppBarTitle(@StringRes title: Int? = null, titleStr: String? = null) {
    // Resolve the title from either string resource or direct string
    val resolvedTitle = title?.let { stringResource(it) } ?: titleStr
    
    // Only display the title if it's not null or blank
    if (!resolvedTitle.isNullOrBlank()) {
        Text(
            text = resolvedTitle,
            style = MaterialTheme.typography.titleLarge, // Use Material Design 3 title large style
        )
    }
}
