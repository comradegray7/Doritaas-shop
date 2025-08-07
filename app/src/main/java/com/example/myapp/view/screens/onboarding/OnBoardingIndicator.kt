package com.example.myapp.view.screens.onboarding

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapp.ui.theme.MyAppTheme
import com.example.myapp.ui.theme.customSpacing

/**
 * OnBoardingIndicator - Composable for displaying page indicators in onboarding screens.
 * 
 * This composable creates a row of indicator dots that show the current page position
 * in a multi-page onboarding flow. The current page is highlighted with a different color.
 * 
 * @param pageSize Total number of pages in the onboarding flow
 * @param currentPage Current page index (0-based)
 * @param selectedColor Color for the current page indicator
 * @param unSelectedColor Color for non-current page indicators
 * 
 * Usage:
 * ```
 * OnBoardingIndicator(
 *     pageSize = 3,
 *     currentPage = 1,
 *     selectedColor = MaterialTheme.colorScheme.primary,
 *     unSelectedColor = MaterialTheme.colorScheme.outline
 * )
 * ```
 */
@Composable
fun OnBoardingIndicator(
    pageSize : Int, // Total number of pages
    currentPage : Int, // Current page index (0-based)
    selectedColor : Color = MaterialTheme.colorScheme.primaryContainer, // Color for current page
    unSelectedColor : Color = MaterialTheme.colorScheme.onPrimaryContainer, // Color for other pages
) {
    Row(horizontalArrangement = Arrangement.SpaceBetween){ // Space indicators evenly
        repeat ( pageSize ){ // Create indicators for each page
            Spacer(modifier = Modifier.size(MaterialTheme.customSpacing.spacer)) // Left spacing

            Box(modifier = Modifier
                .width(MaterialTheme.customSpacing.large) // Fixed width for indicator
                .height(MaterialTheme.customSpacing.customNormal) // Fixed height for indicator
                .clip(RoundedCornerShape(MaterialTheme.customSpacing.customNormal)) // Rounded corners
                .background(color = if(it == currentPage) selectedColor else unSelectedColor)) // Color based on current page

            Spacer(modifier = Modifier.size(MaterialTheme.customSpacing.spacer)) // Right spacing
        }
    }
}

