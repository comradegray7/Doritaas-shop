package com.example.myapp.navigation.navigation_helper

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically

/**
 * NavigationAnimations - Object containing predefined navigation animations.
 * 
 * This object provides a collection of reusable animation transitions for navigation
 * between screens. It includes horizontal slides, vertical slides, and fade transitions
 * with consistent timing and easing.
 */
object NavigationAnimations {
    /** Duration for all navigation animations in milliseconds */
    private const val ANIMATION_DURATION = 300

    /**
     * Slide in from right, slide out to left
     * Used for forward navigation (pushing new screens)
     */
    fun slideInFromRight(): EnterTransition = slideInHorizontally(
        initialOffsetX = { fullWidth -> fullWidth }, // Start from right edge
        animationSpec = tween(ANIMATION_DURATION)
    ) + fadeIn(animationSpec = tween(ANIMATION_DURATION))

    fun slideOutToLeft(): ExitTransition = slideOutHorizontally(
        targetOffsetX = { fullWidth -> -fullWidth }, // Exit to left edge
        animationSpec = tween(ANIMATION_DURATION)
    ) + fadeOut(animationSpec = tween(ANIMATION_DURATION))

    /**
     * Slide in from left, slide out to right (for back navigation)
     * Used when navigating back to previous screens
     */
    fun slideInFromLeft(): EnterTransition = slideInHorizontally(
        initialOffsetX = { fullWidth -> -fullWidth }, // Start from left edge
        animationSpec = tween(ANIMATION_DURATION)
    ) + fadeIn(animationSpec = tween(ANIMATION_DURATION))

    fun slideOutToRight(): ExitTransition = slideOutHorizontally(
        targetOffsetX = { fullWidth -> fullWidth }, // Exit to right edge
        animationSpec = tween(ANIMATION_DURATION)
    ) + fadeOut(animationSpec = tween(ANIMATION_DURATION))

    /**
     * Slide up from bottom (for modals/sheets)
     * Used for bottom sheet and modal presentations
     */
    fun slideInFromBottom(): EnterTransition = slideInVertically(
        initialOffsetY = { fullHeight -> fullHeight }, // Start from bottom edge
        animationSpec = tween(ANIMATION_DURATION)
    ) + fadeIn(animationSpec = tween(ANIMATION_DURATION))

    fun slideOutToBottom(): ExitTransition = slideOutVertically(
        targetOffsetY = { fullHeight -> fullHeight }, // Exit to bottom edge
        animationSpec = tween(ANIMATION_DURATION)
    ) + fadeOut(animationSpec = tween(ANIMATION_DURATION))

    /**
     * Cross fade for bottom bar navigation
     * Used for smooth transitions between bottom bar destinations
     */
    fun crossFade(): EnterTransition = fadeIn(
        animationSpec = tween(ANIMATION_DURATION)
    )

    fun crossFadeOut(): ExitTransition = fadeOut(
        animationSpec = tween(ANIMATION_DURATION)
    )
}