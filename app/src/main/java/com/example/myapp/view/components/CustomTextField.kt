package com.example.myapp.view.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.myapp.ui.theme.LocalWindowSizeConstant
import com.example.myapp.ui.theme.customSpacing

/**
 * VALIDATION FUNCTIONS
 * 
 * These functions provide common validation logic for form fields.
 * They can be used throughout the app to ensure data integrity.
 */

/**
 * Validates if the provided string is a valid email address.
 * 
 * @param email The email string to validate
 * @return true if the email is valid, false otherwise
 */
fun isValidEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

/**
 * Validates if the provided string meets password requirements.
 * 
 * @param password The password string to validate
 * @return true if the password is at least 8 characters and contains both letters and numbers
 */
fun isValidPassword(password: String): Boolean {
    return password.length >= 8 &&
            password.any { it.isDigit() } &&
            password.any { it.isLetter() }
}

/**
 * Confirms that two password strings match and meet password requirements.
 * 
 * @param password The first password string
 * @param confirmPassword The second password string to compare
 * @return true if passwords match and are valid, false otherwise
 */
fun confirmPasswords(password: String, confirmPassword: String): Boolean {
    return password == confirmPassword && isValidPassword(password)
}

/**
 * Validates if the provided string is a valid full name.
 * 
 * @param name The name string to validate
 * @return true if the name is at least 2 characters and contains at least 2 words
 */
fun isValidFullName(name: String): Boolean {
    return name.trim().length >= 2 && name.trim().split(" ").size >= 2
}

/**
 * CustomTextField - Composable function for creating customizable text input fields.
 * 
 * This composable creates a Material Design 3 outlined text field with support for:
 * - Labels and placeholders
 * - Password visibility toggle
 * - Custom icons
 * - Error states and messages
 * - Adaptive sizing based on window size
 * 
 * @param modifier Optional modifier to apply to the text field
 * @param label String resource ID for the field label
 * @param shape Corner radius for the text field (defaults to custom normal spacing)
 * @param placeholder String resource ID for the placeholder text
 * @param icon Optional icon to display as trailing icon
 * @param onClickIcon Callback for when the icon is clicked
 * @param value Current value of the text field
 * @param onValueChange Callback for when the text value changes
 * @param isError Whether to show error state
 * @param errorMessage Error message to display when isError is true
 * @param isPassword Whether this field is for password input
 * 
 * Usage:
 * ```
 * CustomTextField(
 *     label = R.string.email,
 *     placeholder = R.string.enter_email,
 *     value = email,
 *     onValueChange = { email = it },
 *     isError = emailError.isNotEmpty(),
 *     errorMessage = emailError
 * )
 * ```
 */
@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    @StringRes label: Int,
    shape: Dp = MaterialTheme.customSpacing.customNormal,
    @StringRes placeholder: Int,
    icon: ImageVector? = null,
    onClickIcon: () -> Unit = {},
    value: String = "",
    onValueChange: (String) -> Unit = {},
    isError: Boolean = false,
    errorMessage: String = "",
    isPassword: Boolean = false
) {
    val windowSizeConstant = LocalWindowSizeConstant.current

    // State for password visibility toggle
    var passwordVisible by remember { mutableStateOf(false) }

    Column {
        OutlinedTextField(
            modifier = windowSizeConstant.adaptiveFormWidthModifier, // Adaptive width based on window size
            value = value, // Current text value
            singleLine = true, // Single line input
            maxLines = 1, // Maximum 1 line
            placeholder = { 
                Text(
                    style = windowSizeConstant.labelTextStyle, // Adaptive label style
                    text = stringResource(placeholder) // Placeholder text
                ) 
            },
            onValueChange = onValueChange, // Handle text changes
            label = { 
                Text(
                    style = windowSizeConstant.labelTextStyle, // Adaptive label style
                    text = stringResource(label) // Label text
                ) 
            },
            shape = RoundedCornerShape(shape), // Rounded corners
            isError = isError, // Error state
            visualTransformation = if (isPassword && !passwordVisible) {
                PasswordVisualTransformation() // Hide password characters
            } else {
                VisualTransformation.None // Show text normally
            },
            trailingIcon = {
                if (isPassword) {
                    // Password visibility toggle button
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            contentDescription = if (passwordVisible) "Hide password" else "Show password",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                } else if (icon != null) {
                    // Custom action icon
                    IconButton(onClick = onClickIcon) {
                        Icon(
                            imageVector = icon,
                            contentDescription = "Action icon",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        )

        // Error message display
        if (isError && errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                softWrap = true, // Allow text wrapping
                color = MaterialTheme.colorScheme.error, // Error color
                style = windowSizeConstant.labelTextStyle, // Adaptive label style
                modifier = Modifier
                    .padding(top = MaterialTheme.customSpacing.smaller) // Top padding
                    .width(MaterialTheme.customSpacing.xxxLarge) // Fixed width for error message
            )
        }
    }
}