package com.example.myapp.view.screens.forms.password

// Import statements for Android Compose UI components
import android.content.res.Configuration
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myapp.R
import com.example.myapp.ui.theme.MyAppTheme
// Import custom UI components for form building
import com.example.myapp.view.components.CustomRow
import com.example.myapp.view.components.CustomScaffoldContainer
import com.example.myapp.view.components.CustomSpacer
import com.example.myapp.view.components.CustomTextField
import com.example.myapp.view.components.FormContainer
import com.example.myapp.view.components.HeadlineWidget
import com.example.myapp.view.components.Logo
import com.example.myapp.view.components.custom.buttons.CustomButton
// Import validation utilities
import com.example.myapp.view.components.isValidEmail
import com.example.myapp.view.components.isValidPassword

/**
 * ForgotPasswordScreen - A composable screen for password reset functionality
 * 
 * This screen allows users to:
 * - Enter their email address for password reset
 * - Validate email format and presence
 * - Submit password reset request
 * - Navigate back to sign in screen
 * 
 * The screen provides a simple form with email validation and clear user feedback.
 * It's part of the password recovery flow and serves as the entry point for
 * users who have forgotten their passwords.
 * 
 * @param rememberPasswordClick Callback function to navigate back to sign in screen
 * @param resetPasswordClick Callback function to handle password reset submission
 * 
 * Usage:
 * ```
 * ForgotPasswordScreen(
 *     rememberPasswordClick = { /* navigate back to sign in */ },
 *     resetPasswordClick = { /* handle password reset */ }
 * )
 * ```
 */
@Composable
fun ForgotPasswordScreen(
    rememberPasswordClick: () -> Unit,
    resetPasswordClick: () -> Unit
) {
    // Scroll state for the form container to handle scrolling when keyboard appears
    val scrollState = rememberScrollState()
    
    // Form state - stores the email input value
    var email by remember { mutableStateOf("") }

    // Validation state - stores error messages for email validation
    var emailError by remember { mutableStateOf("") }

    /**
     * Validates the email input field
     * 
     * Checks for:
     * - Empty email (required field validation)
     * - Valid email format using regex pattern
     * 
     * @return Boolean indicating if email is valid
     */
    fun validateEmail(): Boolean {
        return when {
            email.isEmpty() -> {
                emailError = "Email is required"
                false
            }

            !isValidEmail(email) -> {
                emailError = "Please enter a valid email address"
                false
            }

            else -> {
                emailError = ""
                true
            }
        }
    }

    /**
     * Validates the entire form before submission
     * 
     * Currently validates:
     * - Email field
     * 
     * @return Boolean indicating if all form fields are valid
     */
    fun validateForm(): Boolean {
        val isEmailValid = validateEmail()

        return isEmailValid
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
                scrollState = scrollState){
                    // Logo section - displays app logo at the top
                    Logo()

                    // Headline section - displays main title and subtitle for password reset
                    HeadlineWidget(
                        middleText = R.string.forgot_password,
                        subMiddleText = R.string.do_not_worry
                    )

                    // Email input field with validation
                    CustomTextField(
                        label = R.string.email,
                        placeholder = R.string.enter_email,
                        value = email,
                        onValueChange = {
                            email = it
                            // Clear validation error when user starts typing
                            if (emailError.isNotEmpty()) validateEmail()
                        },
                        isError = emailError.isNotEmpty(),
                        errorMessage = emailError
                    )

                    // Reset password button - triggers form validation and submission
                    CustomButton(
                        label = R.string.reset_password,
                        onClick = {
                            if (validateForm()) {
                                // Proceed with sign up
                                resetPasswordClick()
                                println("Form is valid, proceeding with sign up")
                                // Add your sign up logic here
                            }
                        }
                    )

                    // Navigation row - allows users to go back to sign in screen
                    CustomRow(
                        leadingText = R.string.remember_password,
                        trailingText = R.string.sign_in,
                        onClick = { rememberPasswordClick() }
                    )
                }
        }
    )
}

/**
 * Bonus - Preview composable for ForgotPasswordScreen
 * 
 * This preview shows the screen in dark mode for design review
 * Uses ExperimentalMaterial3Api for Material 3 components
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun ForgotPasswordScreenPreview() {

    MyAppTheme(
        dynamicColor = false, // Disable dynamic color for consistent preview
        content = {
            ForgotPasswordScreen(
                rememberPasswordClick = {},
                resetPasswordClick = {}
            )
        }
    )
}