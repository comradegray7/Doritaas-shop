package com.example.myapp.view.screens.onboarding

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.myapp.R

/**
 * OnBoardingModel - Sealed class representing onboarding screen data.
 * 
 * This sealed class defines the structure for onboarding screen content,
 * including images, titles, and descriptions. Each object represents
 * a specific onboarding screen with its associated resources.
 * 
 * @param image Drawable resource ID for the screen's illustration
 * @param title String resource ID for the screen's title
 * @param description String resource ID for the screen's description text
 */
sealed class OnBoardingModel (
    @DrawableRes val image : Int, // Illustration image for the onboarding screen
    @StringRes val title : Int, // Title text for the onboarding screen
    @StringRes val description : Int // Description text for the onboarding screen
) {
    /**
     * FirstScreen - Welcome screen for new users.
     * 
     * Introduces users to the app with a welcoming message and
     * sets expectations for the onboarding experience.
     */
    object FirstScreen : OnBoardingModel(
        image = R.drawable.thrilled, // Thrilled/enthusiastic illustration
        title = R.string.welcome_title, // Welcome message
        description = R.string.welcome_subtitle // Welcome subtitle/description
    )
    
    /**
     * SecondScreen - AI shopping features introduction.
     * 
     * Highlights the app's AI-powered shopping features and
     * demonstrates the value proposition to users.
     */
    object SecondScreen : OnBoardingModel(
        image = R.drawable.ai_shopping, // AI shopping illustration
        title = R.string.ai_title, // AI features title
        description = R.string.ai_sub_title // AI features description
    )
    
    /**
     * ThirdScreen - Call-to-action for account creation.
     * 
     * Encourages users to create an account and get started
     * with the app's features.
     */
    object ThirdScreen : OnBoardingModel(
        image = R.drawable.get_started, // Get started illustration
        title = R.string.create_account_now_title, // Account creation title
        description = R.string.create_account_now_subtitle // Account creation description
    )
}