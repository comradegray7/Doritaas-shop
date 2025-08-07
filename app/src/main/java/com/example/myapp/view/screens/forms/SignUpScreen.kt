package com.example.myapp.view.screens.forms

// Import statements for Android Compose UI components and navigation
import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myapp.R
import com.example.myapp.ui.theme.MyAppTheme
import com.example.myapp.ui.theme.customSpacing
// Import custom UI components for form building
import com.example.myapp.view.components.CustomLazyColumn
import com.example.myapp.view.components.CustomRow
import com.example.myapp.view.components.CustomScaffoldContainer
import com.example.myapp.view.components.CustomSpacer
import com.example.myapp.view.components.CustomTextField
import com.example.myapp.view.components.FormContainer
import com.example.myapp.view.components.HeadlineWidget
import com.example.myapp.view.components.Logo
import com.example.myapp.view.components.OrDivider
import com.example.myapp.view.components.PaddedSection
import com.example.myapp.view.components.TermsOfServiceAndUse
import com.example.myapp.view.components.custom.buttons.CustomButton
// Import validation utilities
import com.example.myapp.view.components.isValidEmail
import com.example.myapp.view.components.isValidFullName
import com.example.myapp.view.components.isValidPassword
import com.example.myapp.view.screens.forms.password.ResetCodeScreen
import com.example.myapp.view.utils.ButtonIcon

/**
 * SignUpScreen - Composable function for user registration
 * 
 * This screen provides a comprehensive sign-up form with the following features:
 * - Full name, email, and password input fields with real-time validation
 * - Terms of service agreement checkbox
 * - Social login options (Google and Apple)
 * - Navigation to sign-in screen
 * - Form validation with error messages
 * 
 * The screen uses a custom scaffold container with pull-to-refresh functionality
 * and displays the app logo prominently at the top.
 * 
 * @param modifier Modifier to be applied to the root composable
 * @param onSignInClick Callback function for navigating to sign-in screen
 * 
 * Usage:
 * ```
 * SignUpScreen(
 *     onSignInClick = { /* navigate to sign in */ }
 * )
 * ```
 */
@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    onSignInClick: () -> Unit
) {
    // State for terms of service agreement checkbox
    var isAgreed by remember { mutableStateOf(false) }

    // Form state variables - store user input values
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Validation state variables - store error messages for each field
    var fullNameError by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }
    var termsError by remember { mutableStateOf("") }

    /**
     * Validates the full name input field
     * 
     * Checks for:
     * - Empty name (required field validation)
     * - Valid name format (first and last name)
     * 
     * @return Boolean indicating if full name is valid
     */
    fun validateFullName(): Boolean {
        return when {
            fullName.isEmpty() -> {
                fullNameError = "Full name is required"
                false
            }

            !isValidFullName(fullName) -> {
                fullNameError = "Please enter first and last name"
                false
            }

            else -> {
                fullNameError = ""
                true
            }
        }
    }

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
     * Validates the terms of service agreement
     * 
     * Checks if user has agreed to terms and conditions
     * 
     * @return Boolean indicating if terms are agreed to
     */
    fun validateTerms(): Boolean {
        return if (!isAgreed) {
            termsError = "You must agree to the terms and conditions"
            false
        } else {
            termsError = ""
            true
        }
    }

    // Scroll state for the form container to handle scrolling when keyboard appears
    val scrollState = rememberScrollState()

    /**
     * Validates the entire form before submission
     * 
     * Runs validation for all form fields:
     * - Full name validation
     * - Email validation
     * - Password validation
     * - Terms agreement validation
     * 
     * @return Boolean indicating if all form fields are valid
     */
    fun validateForm(): Boolean {
        val isFullNameValid = validateFullName()
        val isEmailValid = validateEmail()
        val isPasswordValid = validatePassword()
        val isTermsValid = validateTerms()

        return isFullNameValid && isEmailValid && isPasswordValid && isTermsValid
    }

    // Main scaffold container that provides the screen structure
    CustomScaffoldContainer(
        showTopBar = false, // No top app bar for sign-up screen
        showBottomBar = false, // No bottom navigation bar
        showBackArrow = false, // No back-arrow for sign-up screen
        onRefresh = {
            // Add your refresh logic here
            println("Refreshing login screen...")
            // Example: reload user data, refresh tokens, etc.
        },
        content = {
            // Form container that handles scrolling and layout
            FormContainer(scrollState = scrollState)  {
                // Logo section - displays app logo at the top
                Logo()

                // Headline section - displays main title for sign-up
                HeadlineWidget(
                    middleText = R.string.create_account,
                )

                // Full name input field with validation
                CustomTextField(
                    label = R.string.full_name,
                    placeholder = R.string.enter_full_name,
                    value = fullName,
                    onValueChange = {
                        fullName = it
                        // Clear validation error when user starts typing
                        if (fullNameError.isNotEmpty()) validateFullName()
                    },
                    isError = fullNameError.isNotEmpty(),
                    errorMessage = fullNameError,
                    onClickIcon = {}
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

                // Terms of service agreement checkbox with validation
                TermsOfServiceAndUse(
                    isChecked = isAgreed,
                    onCheckedChange = {
                        isAgreed = it
                        // Clear validation error when user changes selection
                        if (termsError.isNotEmpty()) validateTerms()
                    },
                    termsUrl = "https://example.com/terms-of-service",
                    privacyUrl = "https://example.com/privacy-policy",
                    modifier = Modifier.padding(horizontal = MaterialTheme.customSpacing.medium),
                    errorMessage = termsError,
                    isError = termsError.isNotEmpty()
                )

                // Sign Up Button with form validation
                CustomButton(
                    label = R.string.sign_up,
                    onClick = {
                        if (validateForm()) {
                            // Proceed with sign up
                            println("Form is valid, proceeding with sign up")
                            // Add your sign up logic here
                        }
                    }
                )
                
                // Visual separator between main form and social login options
                OrDivider()

                // Google Sign-In Button
                CustomButton(
                    label = R.string.sign_in_with_google,
                    icon = ButtonIcon.Resource(R.drawable.google_icon),
                    tintColor = MaterialTheme.colorScheme.scrim,
                    contentDescription = "Google Icon",
                    onClick = { }
                )

                // Apple Sign In Button
                CustomButton(
                    label = R.string.sign_in_with_Apple,
                    icon = ButtonIcon.Resource(R.drawable.apple_icon),
                    onClick = { },
                    contentDescription = "apple icon"
                )

                // Navigation row - allows users to go to sign-in screen
                CustomRow(
                    leadingText = R.string.do_not_have_account,
                    trailingText = R.string.sign_in,
                    onClick = { onSignInClick() }
                )
            }
        }
    )
}

/**
 * Preview composable for SignUpScreen
 * 
 * This preview shows the screen in dark mode for design review
 */
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SignUpScreenPreview() {
    MyAppTheme(
        dynamicColor = false, // Disable dynamic color for consistent preview
        content = {
            SignUpScreen(
                onSignInClick = {}
            )
        }
    )
}

