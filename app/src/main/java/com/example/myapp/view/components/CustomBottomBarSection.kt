package com.example.myapp.view.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.example.myapp.ui.theme.LocalWindowSizeConstant
import com.example.myapp.view.components.custom.buttons.CustomButton
import com.example.myapp.view.utils.ButtonIcon
import java.util.Locale

/**
 * CustomBottomSection - Composable function for creating bottom sections with total and action button.
 * 
 * This composable creates a bottom section typically used in shopping carts or checkout screens.
 * It displays a total amount and an action button (like "Checkout" or "Add to Cart").
 * The layout is responsive and uses adaptive styling based on window size.
 * 
 * @param modifier Optional modifier to apply to the container
 * @param actionLabel String resource ID for the action button text
 * @param icon Optional icon for the action button
 * @param total Optional total amount to display (defaults to 0.0)
 * @param onClick Callback function for the action button click
 * 
 * Usage:
 * ```
 * CustomBottomSection(
 *     actionLabel = R.string.checkout,
 *     total = 99.99,
 *     onClick = { /* handle checkout */ }
 * )
 * ```
 */
@Composable
fun CustomBottomSection(
    modifier: Modifier = Modifier,
    @StringRes actionLabel: Int,
    icon: ButtonIcon? = null,
    total: Double? = 0.0,
    onClick: () -> Unit
) {

    val windowSizeConstant = LocalWindowSizeConstant.current

    // Row displaying "Total" label and total amount
    Row(
        modifier = Modifier.fillMaxWidth(), // Take full available width
        horizontalArrangement = Arrangement.SpaceBetween, // Space items apart
        verticalAlignment = Alignment.CenterVertically // Center items vertically
    ) {
        // "Total" label
        Text(
            text = "Total",
            style = MaterialTheme.typography.titleMedium, // Medium title style
            color = MaterialTheme.colorScheme.onSurfaceVariant // Surface variant color
        )
        
        // Total amount with currency formatting
        Text(
            text = String.format(Locale.US, "$%.2f", total), // Format as USD currency
            style = MaterialTheme.typography.titleMedium, // Medium title style
            fontWeight = FontWeight.Bold, // Bold weight for emphasis
            color = MaterialTheme.colorScheme.primary // Primary color for emphasis
        )
    }
    
    // Add adaptive spacing
    CustomSpacer()
    
    // Action button (e.g., "Checkout", "Add to Cart")
    CustomButton(
        label = actionLabel,
        icon = icon,
        onClick = { onClick() }, // Handle button click
    )
}
