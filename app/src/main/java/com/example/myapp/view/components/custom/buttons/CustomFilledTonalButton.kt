package com.example.myapp.view.components.custom.buttons

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.myapp.ui.theme.LocalWindowSizeConstant
import com.example.myapp.ui.theme.customSpacing

/**
 * CustomFilledTonalButton - Composable function for creating circular filled tonal buttons with icons.
 * 
 * This composable creates a Material Design 3 filled tonal button in a circular shape,
 * typically used for action buttons like favorites, likes, or other icon-based actions.
 * The button uses adaptive sizing based on the current window size.
 * 
 * @param onClick Callback function for button clicks
 * @param icon ImageVector to display as the button icon
 * 
 * Usage:
 * ```
 * CustomFilledTonalButton(
 *     onClick = { /* handle click */ },
 *     icon = Icons.Default.FavoriteBorder
 * )
 * ```
 */
@Composable
fun CustomFilledTonalButton(onClick:() -> Unit = {}, icon: ImageVector){
    // Get the current window size constants for adaptive behavior
    val windowSizeConstant = LocalWindowSizeConstant.current

    FilledTonalButton(
        onClick = { onClick() }, // Handle button clicks
        modifier = Modifier.size(windowSizeConstant.iconPadding), // Adaptive size based on window size
        shape = CircleShape, // Circular button shape
        contentPadding = PaddingValues(MaterialTheme.customSpacing.none) // No internal padding
    ) {
        Icon(
            imageVector = icon, // Display the provided icon
            contentDescription = "Add to Favourite", // Accessibility description
            modifier = Modifier.size(windowSizeConstant.iconPadding) // Adaptive icon size
        )
    }
}