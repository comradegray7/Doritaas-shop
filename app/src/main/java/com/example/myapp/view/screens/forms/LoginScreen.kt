package com.example.myapp.view.screens.forms

// Import statements for Android Compose UI components and navigation
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import com.example.myapp.view.components.FormContainer
import com.example.myapp.view.components.HeadlineWidget
import com.example.myapp.view.components.Logo
import com.example.myapp.view.components.OrDivider
import com.example.myapp.view.components.PaddedSection
import com.example.myapp.view.components.custom.buttons.CustomButton
import com.example.myapp.view.utils.ButtonIcon

/**
 * LoginScreen - Composable function for the user login screen.
 * 
 * This screen provides multiple authentication options including:
 * - Google Sign-In
 * - Apple Sign-In
 * - Email Sign-In
 * - Sign Up navigation
 * 
 * The screen uses a custom scaffold container with pull-to-refresh functionality
 * and displays the app logo prominently at the top. This is the main entry point
 * for user authentication, offering various login methods to accommodate different
 * user preferences and requirements.
 * 
 * @param onContinueWithGoogleClick Callback for Google Sign-In button
 * @param onContinueWithAppleClick Callback for Apple Sign-In button
 * @param onContinueWithEmailClick Callback for Email Sign-In button
 * @param onSignUpClick Callback for Sign Up navigation
 * 
 * Usage:
 * ```
 * LoginScreen(
 *     onContinueWithGoogleClick = { /* handle Google sign in */ },
 *     onContinueWithAppleClick = { /* handle Apple sign in */ },
 *     onContinueWithEmailClick = { /* navigate to email login */ },
 *     onSignUpClick = { /* navigate to sign up */ }
 * )
 * ```
 */
@Composable
fun LoginScreen(
    onContinueWithGoogleClick: () -> Unit,
    onContinueWithAppleClick: () -> Unit,
    onContinueWithEmailClick: () -> Unit,
    onSignUpClick: () -> Unit,
) {
    // Remember scroll state for the form container to handle scrolling when keyboard appears
    val scrollState = rememberScrollState()

    // Main scaffold container that provides the screen structure
    CustomScaffoldContainer(
        showTopBar = false, // No top bar for login screen
        showBottomBar = false, // No bottom bar for login screen
        showBackArrow = false, //no back arrow for login screen
        onRefresh = {
            // Add your refresh logic here
            println("Refreshing login screen...")
            // Example: reload user data, refresh tokens, etc.
        },
        content = {
            // Form container that handles scrolling and layout
            FormContainer(scrollState = scrollState) {
                // Logo section - Display app logo prominently at the top
                Logo()

                // Main headline for the login screen - provides context and instructions
                HeadlineWidget(
                    middleText = R.string.sign_in_to_your_account
                )

                // Google Sign-In Button - primary social login option
                CustomButton(
                    label = R.string.sign_in_with_google,
                    icon = ButtonIcon.Resource(R.drawable.google_icon),
                    tintColor = MaterialTheme.colorScheme.scrim, // Use scrim color for Google icon
                    contentDescription = "Google Icon",
                    onClick = {
                        onContinueWithGoogleClick()
                    },
                )

                // Apple Sign In Button - secondary social login option
                CustomButton(
                    label = R.string.sign_in_with_Apple,
                    icon = ButtonIcon.Resource(R.drawable.apple_icon),
                    onClick = {
                        onContinueWithAppleClick()
                    },
                    contentDescription = "apple icon"
                )

                // Visual separator between social and email login options
                OrDivider()

                // Email Sign-In Button - traditional login method
                CustomButton(
                    label = R.string.sign_in_with_Email,
                    icon = ButtonIcon.Resource(R.drawable.mail_icon),
                    onClick = {
                        onContinueWithEmailClick()
                    },
                    contentDescription = "email icon"
                )

                // Sign Up navigation row - allows users to go to sign-up screen
                CustomRow(
                    leadingText = R.string.do_not_have_account,
                    trailingText = R.string.sign_up,
                    onClick = { onSignUpClick() }
                )
            }
        }
    )
}

/**
 * Preview composable for LoginScreen
 * 
 * This preview shows the screen in dark mode for design review
 */
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LoginScreenPreview() {
    MyAppTheme(
        dynamicColor = false, // Disable dynamic color for consistent preview
        content = {
            LoginScreen(
                onContinueWithGoogleClick = {},
                onContinueWithAppleClick = {},
                onContinueWithEmailClick = {},
                onSignUpClick = TODO()
            )
        }
    )
}

