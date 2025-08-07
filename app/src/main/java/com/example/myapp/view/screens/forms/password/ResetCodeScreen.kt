package com.example.myapp.view.screens.forms.password

// Import statements for Android Compose UI components and animations
import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CancelScheduleSend
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myapp.R
import com.example.myapp.ui.theme.LocalWindowSizeConstant
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
import com.example.myapp.view.components.PaddedSection
import com.example.myapp.view.components.custom.buttons.CustomButton
import com.example.myapp.view.utils.ButtonIcon

/**
 * ResetCodeScreen - Composable function for verification code input
 * 
 * This screen allows users to:
 * - Enter a 6-digit verification code sent to their email
 * - Auto-focus between code input fields
 * - Validate code format and completeness
 * - Resend verification code if needed
 * - Submit code for verification
 * 
 * The screen provides an intuitive code input experience with:
 * - Individual input fields for each digit
 * - Auto-focus navigation between fields
 * - Real-time validation
 * - Error handling and user feedback
 * 
 * @param resendCode Callback function to resend verification code
 * @param onVerifyCodeClick Callback function to handle code verification
 * 
 * Usage:
 * ```
 * ResetCodeScreen(
 *     resendCode = { /* handle code resend */ },
 *     onVerifyCodeClick = { /* handle code verification */ }
 * )
 * ```
 */
@Composable
fun ResetCodeScreen(
    resendCode: () -> Unit,
    onVerifyCodeClick: () -> Unit
) {
    // Window size constant for responsive design
    val windowSizeConstant = LocalWindowSizeConstant.current

    // Scroll state for the form container to handle scrolling when keyboard appears
    val scrollState = rememberScrollState()

    // Form state - stores the 6-digit verification code as a list of strings
    var code by remember { mutableStateOf(List(6) { "" }) }
    // Loading state for async operations
    var isLoading by remember { mutableStateOf(false) }
    // Error state for validation messages
    var errorMessage by remember { mutableStateOf<String?>(null) }
    // Focus manager for handling keyboard navigation between input fields
    val focusManager = LocalFocusManager.current
    // Submission state to prevent multiple submissions
    var isSubmitting by remember { mutableStateOf(false) }

    // Processing state for async operations
    var isProcessing by remember { mutableStateOf(false) }
    // Success state for completion feedback
    var showSuccess by remember { mutableStateOf(false) }

    /**
     * Validates the verification code input
     * 
     * Checks for:
     * - Complete 6-digit code
     * - Numeric characters only
     * 
     * @return Boolean indicating if code is valid
     */
    fun validateCode(): Boolean {
        val codeString = code.joinToString("")
        return when {
            codeString.length != 6 -> {
                errorMessage = "Please enter all 6 digits"
                false
            }

            !codeString.all { it.isDigit() } -> {
                errorMessage = "Code must contain only numbers"
                false
            }

            else -> {
                errorMessage = null
                true
            }
        }
    }

    /**
     * Checks if the verification code is complete
     * 
     * @return Boolean indicating if all 6 digits are entered
     */
    fun isCodeComplete(): Boolean {
        return code.all { it.isNotEmpty() } && code.joinToString("").length == 6
    }

    /**
     * Clears the form and resets all state
     * 
     * Used when resending code or refreshing the screen
     */
    fun clearForm() {
        code = List(6) { "" }
        errorMessage = null
    }

    // Main scaffold container that provides the screen structure
    CustomScaffoldContainer(
        showTopBar = false, // No top app bar for this screen
        showBottomBar = false, // No bottom navigation bar
        onRefresh = {
            // Add your refresh logic here
            println("Refreshing reset code screen...")
            clearForm()
        },
        content = {
            // Form container that handles scrolling and layout
            FormContainer(scrollState = scrollState){
                // Logo section - displays app logo at the top
                Logo()

                // Headline section - displays main title and subtitle for code verification
                HeadlineWidget(
                    middleText = R.string.code_verification,
                    subMiddleText = R.string.confirm_verification_code
                )

               // Code input section with validation
               Column(verticalArrangement = Arrangement.spacedBy(MaterialTheme.customSpacing.smaller)){
                   // Code Input Fields - 6 individual input fields for each digit
                   Row(
                       modifier = Modifier.fillMaxWidth(),
                       horizontalArrangement = Arrangement.spacedBy(MaterialTheme.customSpacing.customNormal)
                   ) {
                       repeat(6) { index ->
                           CodeInputField(
                               value = code[index],
                               onValueChange = { newValue ->
                                   if (newValue.length <= 1 && newValue.all { it.isDigit() }) {
                                       code = code.toMutableList()
                                           .apply { this[index] = newValue }

                                       // Clear error when user starts typing
                                       if (errorMessage != null) {
                                           errorMessage = null
                                       }

                                       // Auto-focus next field when digit is entered
                                       if (newValue.isNotEmpty() && index < 5) {
                                           focusManager.moveFocus(FocusDirection.Next)
                                       }

                                       // Auto-focus previous field when digit is deleted
                                       if (newValue.isEmpty() && index > 0) {
                                           focusManager.moveFocus(FocusDirection.Previous)
                                       }
                                   }
                               },
                               onKeyboardAction = {
                                   // Handle backspace to move to previous field
                                   if (code[index].isEmpty() && index > 0) {
                                       focusManager.moveFocus(FocusDirection.Previous)
                                   }
                               },
                               modifier = Modifier.weight(1f),
                               isError = errorMessage != null
                           )
                       }
                   }
                   // Code input error message display
                   if (errorMessage != null) {
                       Spacer(modifier = Modifier.height(MaterialTheme.customSpacing.small))
                       Text(
                           text = errorMessage!!,
                           color = MaterialTheme.colorScheme.error,
                           style = windowSizeConstant.labelTextStyle,
                           modifier = Modifier.fillMaxWidth()
                       )
                   }
               }
                // Verify Button - triggers code validation and submission
                CustomButton(
                    label = R.string.verify_code,
                    tintColor = MaterialTheme.colorScheme.surfaceTint,
                    onClick = {
                        if (validateCode()) {
                          onVerifyCodeClick()
                        }
                    },
                    contentDescription = "Verify code button",
                )

                // Resend Code navigation row - allows users to request new code
                CustomRow(
                    leadingText = R.string.did_not_get_code,
                    trailingText = R.string.resend_code,
                    onClick = {
                        clearForm()
                        resendCode()
                    }
                )
            }
        }
    )
}

/**
 * CodeInputField - Individual input field for verification code digits
 * 
 * This composable provides a single input field for one digit of the verification code.
 * It includes:
 * - Numeric keyboard input
 * - Center-aligned text
 * - Error state styling
 * - Keyboard navigation support
 * 
 * @param modifier Modifier to be applied to the input field
 * @param value Current value of the input field
 * @param onValueChange Callback for value changes
 * @param onKeyboardAction Callback for keyboard actions
 * @param isError Boolean indicating if field has validation error
 */
@Composable
fun CodeInputField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    onKeyboardAction: () -> Unit = {},
    isError: Boolean = false
) {
    // Focus manager for handling keyboard navigation
    val focusManager = LocalFocusManager.current

    // Outlined text field with custom styling for code input
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.height(MaterialTheme.customSpacing.customMedium),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number // Restrict to numeric input
        ),
        textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center), // Center-align text
        keyboardActions = KeyboardActions(
            onNext = {
                focusManager.moveFocus(FocusDirection.Next)
            },
            onPrevious = {
                focusManager.moveFocus(FocusDirection.Previous)
            },
            onDone = {
                onKeyboardAction()
            }
        ),
        singleLine = true, // Single line input
        shape = MaterialTheme.shapes.medium,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.outline,
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface
        )
    )
}

/**
 * Preview composable for ResetCodeScreen
 * 
 * This preview shows the screen in dark mode for design review
 */
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ResetCodeScreenPreview() {
    MyAppTheme(
        dynamicColor = false, // Disable dynamic color for consistent preview
        content = {
            ResetCodeScreen(
                resendCode = {},
                onVerifyCodeClick = {}
            )
        }
    )
}
