package com.example.myapp.view.screens.onboarding

import android.content.Context
import androidx.core.content.edit

/**
 * OnBoardingUtil - Utility class for managing onboarding state persistence.
 * 
 * This class provides methods to check and set the onboarding completion status
 * using Android SharedPreferences. It ensures that users only see the onboarding
 * screens once, and subsequent app launches skip directly to the main content.
 * 
 * @param context Android context for accessing SharedPreferences
 * 
 * Usage:
 * ```
 * val onboardingUtil = OnBoardingUtil(context)
 * if (!onboardingUtil.isOnBoardingCompleted()) {
 *     // Show onboarding screens
 * } else {
 *     // Navigate to main app
 * }
 * ```
 */
class OnBoardingUtil(private val context: Context) {
    
    /**
     * Checks if the user has completed the onboarding process.
     * 
     * @return true if onboarding is completed, false otherwise
     */
    fun isOnBoardingCompleted(): Boolean {
        return context.getSharedPreferences("onboarding", Context.MODE_PRIVATE)
            .getBoolean("completed", false) // Default to false (not completed)
    }
    
    /**
     * Marks the onboarding process as completed.
     * 
     * This method persists the completion status so that future app launches
     * will skip the onboarding screens.
     */
    fun setOnBoardingCompleted() {
        return context.getSharedPreferences("onboarding", Context.MODE_PRIVATE)
            .edit() {
                putBoolean("completed", true) // Mark as completed
            }
    }
}