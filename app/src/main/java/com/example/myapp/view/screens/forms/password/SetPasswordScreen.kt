package com.example.myapp.view.screens.forms.password

// Import statements for Android Compose UI components
import android.content.res.Configuration
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapp.R
import com.example.myapp.ui.theme.LocalWindowSizeConstant
import com.example.myapp.ui.theme.MyAppTheme
// Import custom UI components for form building
import com.example.myapp.view.components.CustomLazyColumn
import com.example.myapp.view.components.CustomScaffoldContainer
import com.example.myapp.view.components.CustomSpacer
import com.example.myapp.view.components.CustomTextField
import com.example.myapp.view.components.FormContainer
import com.example.myapp.view.components.HeadlineWidget
import com.example.myapp.view.components.Logo
import com.example.myapp.view.components.PaddedSection
import com.example.myapp.view.components.confirmPasswords
import com.example.myapp.view.components.custom.buttons.CustomButton
import com.example.myapp.view.components.custom.buttons.CustomOutlinedButton
// Import validation utilities
import com.example.myapp.view.components.isValidPassword

/**
 * SetPasswordScreen - Composable function for setting new password
 * 
 * This screen allows users to:
 * - Enter a new password with strength requirements
 * - Confirm the new password
 * - Validate password strength and confirmation match
 * - Submit password reset
 * - Navigate back to previous screen
 * 
 * The screen is part of the password reset flow and ensures users
 * create a strong password that meets security requirements.
 * 
 * @param onBackNavigation Callback function to navigate back to previous screen
 * @param onResetPasswordClick Callback function to handle password reset submission
 * 
 * Usage:
 * ```
 * SetPasswordScreen(
 *     onBackNavigation = { /* navigate back */ },
 *     onResetPasswordClick = { /* handle password reset */ }
 * )
 * ```
 */
@Composable
fun SetPasswordScreen(
    onBackNavigation: () -> Unit,
    onResetPasswordClick: () -> Unit
) {
    // Window size constant for responsive design
    val windowSizeConstant = LocalWindowSizeConstant.current

    // Scroll state for the form container to handle scrolling when keyboard appears
    val scrollState = rememberScrollState()

    // Form state variables - store user input values
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    // Validation state variables - store error messages for each field
    var passwordError by remember { mutableStateOf("") }
    var confirmPasswordError by remember { mutableStateOf("") }

    /**
     * Validates the password input field
     * 
     * Checks for:
     * - Empty password (required field validation)
     * - Password strength requirements (minimum 8 characters with letters and numbers)
     * 
     * @return Boolean indicating if password is valid
     */
    fun validatePassword(): Boolean {
        return when {
            password.isEmpty() -> {
                passwordError = "Password is required"
                false
            }

            !isValidPassword(password) -> {
                passwordError = "Password must be at least 8 characters with letters and numbers"
                false
            }

            else -> {
                passwordError = ""
                true
            }
        }
    }

    /**
     * Validates the confirm password input field
     * 
     * Checks for:
     * - Empty confirm password (required field validation)
     * - Password confirmation match
     * 
     * @return Boolean indicating if confirm password is valid
     */
    fun validateConfirmPassword(): Boolean {
        return when {
            confirmPassword.isEmpty() -> {
                confirmPasswordError = "Confirm password is required"
                false
            }

            !confirmPasswords(password, confirmPassword) -> {
                confirmPasswordError = "The two passwords do not match"
                false
            }

            else -> {
                confirmPasswordError = ""
                true
            }
        }
    }

    /**
     * Validates the entire form before submission
     * 
     * Runs validation for all form fields:
     * - Password validation
     * - Confirm password validation
     * 
     * @return Boolean indicating if all form fields are valid
     */
    fun validateForm(): Boolean {
        val isPasswordValid = validatePassword()
        val isConfirmPasswordValid = validateConfirmPassword()

        return isPasswordValid && isConfirmPasswordValid
    }

    // Main scaffold container that provides the screen structure
    CustomScaffoldContainer(
        showTopBar = false, // No top app bar for this screen
        showBottomBar = false, // No bottom navigation bar
        onRefresh = {
            // Add your refresh logic here
            println("Refreshing login screen...")
            // Example: reload user data, refresh tokens, etc.
        },
        content = {
            // Form container that handles scrolling and layout
            FormContainer(
                scrollState = scrollState)
                    {
                        // Logo section - displays app logo at the top
                        Logo()
                        
                        // Headline section - displays main title and subtitle for password change
                        HeadlineWidget(
                            middleText = R.string.change_password,
                            subMiddleText = R.string.password_requirement
                        )

                        // Password input field with validation and password visibility toggle
                        CustomTextField(
                            label = R.string.password,
                            placeholder = R.string.enter_password,
                            value = password,
                            onValueChange = {
                                password = it
                                // Clear validation error when user starts typing
                                if (passwordError.isNotEmpty()) validatePassword()
                            },
                            isError = passwordError.isNotEmpty(),
                            errorMessage = passwordError,
                            isPassword = true // Enables password visibility toggle
                        )

                        // Confirm password input field with validation and password visibility toggle
                        CustomTextField(
                            label = R.string.confirm_password,
                            placeholder = R.string.re_enter_password,
                            value = confirmPassword,
                            onValueChange = {
                                confirmPassword = it
                                // Clear validation error when user starts typing
                                if (confirmPasswordError.isNotEmpty()) validateConfirmPassword()
                            },
                            isError = confirmPasswordError.isNotEmpty(),
                            errorMessage = confirmPasswordError,
                            isPassword = true // Enables password visibility toggle
                        )

                        // Reset password button with form validation
                        CustomButton(
                            label = R.string.reset_password,
                            onClick = {
                                if (validateForm()) {
                                    // Proceed with sign up
                                    onResetPasswordClick()
                                    println("Form is valid, proceeding with sign up")
                                    // Add your sign up logic here
                                }
                            }
                        )

                        // Back navigation button - positioned with custom offset
                        Row(
                            modifier = Modifier.absoluteOffset(x = windowSizeConstant.backButtonOffsetPadding),
                        ) {
                            CustomOutlinedButton(
                                useSmallWidth = true,
                                label = R.string.back,
                                onClick = { onBackNavigation() },
                                contentDescription = ""
                            )
                        }
                    }
        }
    )
}

/**
 * Preview composable for SetPasswordScreen
 * 
 * This preview shows the screen in dark mode for design review
 * Uses ExperimentalMaterial3Api for Material 3 components
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun ProfileScreenPreview() {

    MyAppTheme(
        dynamicColor = false, // Disable dynamic color for consistent preview
        content = {
            SetPasswordScreen(
                onResetPasswordClick = {},
                onBackNavigation = {},
            )
        }
    )
}