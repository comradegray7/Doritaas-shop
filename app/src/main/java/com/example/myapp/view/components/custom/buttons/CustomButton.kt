package com.example.myapp.view.components.custom.buttons

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.myapp.ui.theme.LocalWindowSizeConstant
import com.example.myapp.ui.theme.customSpacing
import com.example.myapp.view.utils.ButtonIcon

/**
 * CustomButton - Composable function for creating adaptive, customizable buttons.
 * 
 * This composable creates a Material Design 3 button with adaptive sizing,
 * optional icons, and flexible content. It supports both string resources
 * and custom content, making it suitable for various use cases throughout the app.
 * 
 * @param modifier Optional modifier to apply to the button
 * @param iconModifier Optional modifier to apply to the icon
 * @param label String resource ID for the button text
 * @param icon Optional icon to display alongside the text
 * @param tintColor Color for the icon tint (defaults to outline color)
 * @param onClick Callback function for button clicks
 * @param contentDescription Accessibility description for the button
 * @param enabled Whether the button is enabled (default: true)
 * @param useSmallWidth Whether to use a small fixed width (default: false)
 * @param content Optional custom content to replace the default text+icon layout
 * 
 * Usage:
 * ```
 * CustomButton(
 *     label = R.string.submit,
 *     onClick = { /* handle click */ }
 * )
 * 
 * CustomButton(
 *     label = R.string.save,
 *     icon = ButtonIcon.Vector(Icons.Default.Save),
 *     onClick = { /* handle save */ }
 * )
 * ```
 */
@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier,
    @StringRes label: Int,
    icon: ButtonIcon? = null,
    tintColor: Color = MaterialTheme.colorScheme.scrim,
    onClick: () -> Unit,
    contentDescription: String = "",
    enabled: Boolean = true,
    useSmallWidth: Boolean = false,
    content: (@Composable RowScope.() -> Unit)? = null
) {

    // Get the current window size constants for adaptive behavior
    val windowSizeConstant = LocalWindowSizeConstant.current

    Button(
        colors = ButtonDefaults.buttonColors(), // Use default Material Design 3 button colors
        enabled = enabled,
        onClick = onClick,
        shape = MaterialTheme.shapes.medium, // Use medium corner radius
        modifier = if (useSmallWidth)
            modifier.width(MaterialTheme.customSpacing.xxLarge) // Fixed small width
        else
            windowSizeConstant.adaptiveFormWidthModifier.height(windowSizeConstant.adaptiveFormHeight) // Adaptive width and height
    ) {
        if (content != null) {
            // Use custom content if provided
            content()
        } else {
            // Default layout with icon and text
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Display icon if provided
                icon?.let { buttonIcon ->
                    when (buttonIcon) {
                        is ButtonIcon.Resource -> {
                            // Display drawable resource icon
                            Icon(
                                painter = painterResource(id = buttonIcon.drawableRes),
                                contentDescription = contentDescription,
                                tint = tintColor,
                                modifier = iconModifier.size(windowSizeConstant.iconPadding) // Adaptive icon size
                            )
                        }

                        is ButtonIcon.Vector -> {
                            // Display vector icon
                            Icon(
                                imageVector = buttonIcon.imageVector,
                                contentDescription = contentDescription,
                                tint = tintColor,
                                modifier = iconModifier.size(windowSizeConstant.iconPadding) // Adaptive icon size
                            )
                        }
                    }
                    // Add spacing between icon and text
                    Spacer(modifier = Modifier.width(MaterialTheme.customSpacing.small))
                }
                
                // Display button text
                Text(
                    text = stringResource(label),
                    fontWeight = FontWeight.SemiBold, // Semi-bold text weight
                    style = windowSizeConstant.bodyTextStyle // Adaptive text style
                )
            }
        }
    }
}

