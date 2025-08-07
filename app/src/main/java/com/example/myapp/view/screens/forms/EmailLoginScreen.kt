package com.example.myapp.view.screens.forms

// Import statements for Android Compose UI components and navigation
import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myapp.R
import com.example.myapp.ui.theme.LocalWindowSizeConstant
import com.example.myapp.ui.theme.MyAppTheme
// Import custom UI components for form building
import com.example.myapp.view.components.*
import com.example.myapp.view.components.custom.buttons.CustomButton
// Import validation utilities
import com.example.myapp.view.components.isValidEmail
import com.example.myapp.view.components.isValidPassword
import com.example.myapp.view.utils.ButtonIcon

/**
 * EmailLoginScreen - Composable function for the email/password login screen.
 *
 * This screen allows users to log in using their email and password.
 * It includes validation, error handling, and links to password reset and sign up.
 * The screen provides multiple authentication options:
 * - Google Sign-In
 * - Apple Sign-In
 * - Email/Password login
 * - Forgot password navigation
 * - Sign up navigation
 *
 * @param modifier Modifier to be applied to the root composable.
 * @param onForgetPasswordClick Callback for when the "Forgot Password" text is clicked.
 * @param onSignClick Callback for when the "Sign Up" text is clicked.
 *
 * Usage:
 * ```
 * EmailLoginScreen(
 *     onForgetPasswordClick = { /* navigate to forgot password */ },
 *     onSignClick = { /* navigate to sign up */ }
 * )
 * ```
 */
@Composable
fun EmailLoginScreen(
    modifier: Modifier = Modifier,
    onForgetPasswordClick: () -> Unit,
    onSignClick: () -> Unit = {}
) {
    // Window size constant for responsive design
    val windowSizeConstant = LocalWindowSizeConstant.current
    // Scroll state for the form container to handle scrolling when keyboard appears
    val scrollState = rememberScrollState()

    // Form state variables - store user input values
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Validation state variables - store error messages for each field
    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }

    /**
     * Validates the email field and sets error messages
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
     * Validates the password field and sets error messages
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
     * Validates the entire form before submission
     * 
     * Runs validation for all form fields:
     * - Email validation
     * - Password validation
     * 
     * @return Boolean indicating if all form fields are valid
     */
    fun validateForm(): Boolean {
        val isEmailValid = validateEmail()
        val isPasswordValid = validatePassword()
        return isEmailValid && isPasswordValid
    }

    // Main scaffold container that provides the screen structure
    CustomScaffoldContainer(
        showTopBar = false, // No top app bar for login screen
        showBottomBar = false, // No bottom navigation bar
        onRefresh = {
            // Add your refresh logic here
            println("Refreshing login screen...")
            // Example: reload user data, refresh tokens, etc.
        },
        content = {
            // Form container that handles scrolling and layout
            FormContainer(scrollState = scrollState) {
                // Logo section - displays app logo at the top
                Logo()

                // Headline section - displays main title for login
                HeadlineWidget(
                    middleText = R.string.sign_in_to_your_account,
                )

                // Google Sign-In Button - primary social login option
                CustomButton(
                    label = R.string.sign_in_with_google,
                    icon = ButtonIcon.Resource(R.drawable.google_icon),
                    contentDescription = "Google Icon",
                    onClick = {},
                )

                // Apple Sign-In Button - secondary social login option
                CustomButton(
                    label = R.string.sign_in_with_Apple,
                    icon = ButtonIcon.Resource(R.drawable.apple_icon),
                    onClick = {},
                    tintColor = MaterialTheme.colorScheme.scrim,
                    contentDescription = "apple icon",
                )

                // Visual separator between social and email login options
                OrDivider()

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

                // Sign In button with form validation
                CustomButton(
                    label = R.string.sign_in,
                    onClick = {
                        if (validateForm()) {
                            // Proceed with sign up
                            println("Form is valid, proceeding with sign up")
                            // Add your sign up logic here
                        }
                    }
                )

                // Forgot password link - clickable text for password reset
                Text(
                    text = stringResource(R.string.forgot_password),
                    style = windowSizeConstant.bodyTextStyle,
                    color = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.clickable { onForgetPasswordClick() }
                )

                // Sign up navigation row - allows users to go to sign-up screen
                CustomRow(
                    leadingText = R.string.do_not_have_account,
                    trailingText = R.string.sign_up,
                    onClick = {}
                )
            }
        }
    )
}

/**
 * Preview composable for EmailLoginScreen
 * 
 * This preview shows the screen in dark mode for design review
 */
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun EmailLoginPreview() {
    MyAppTheme(
        dynamicColor = false, // Disable dynamic color for consistent preview
        content = {
            EmailLoginScreen(
                onForgetPasswordClick = {}
            )
        }
    )
}

