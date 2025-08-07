package com.example.myapp.view.screens.onboarding

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myapp.R
import com.example.myapp.ui.theme.MyAppTheme
import com.example.myapp.ui.theme.customSpacing
import com.example.myapp.view.components.CustomScaffoldContainer
import com.example.myapp.view.components.CustomSpacer
import com.example.myapp.view.components.FormContainer
import com.example.myapp.view.components.PaddedSection
import com.example.myapp.view.components.custom.buttons.CustomButton
import com.example.myapp.view.components.custom.buttons.CustomOutlinedButton
import com.example.myapp.view.screens.forms.password.SetPasswordScreen
import kotlinx.coroutines.launch

/**
 * OnboardingScreen - Main onboarding screen with horizontal pager navigation.
 * 
 * This composable creates a multi-page onboarding experience with smooth horizontal
 * paging, page indicators, and navigation buttons. Users can swipe through screens
 * or use the back/next buttons to navigate.
 * 
 * @param onFinished Callback function called when onboarding is completed
 * 
 * Usage:
 * ```
 * OnboardingScreen(
 *     onFinished = { /* Navigate to main app */ }
 * )
 * ```
 */
@Composable
fun OnboardingScreen(
    onFinished: () -> Unit = {}
) {
    // List of onboarding screens to display
    val screens = listOf(
        OnBoardingModel.FirstScreen, // Welcome screen
        OnBoardingModel.SecondScreen, // AI features screen
        OnBoardingModel.ThirdScreen // Get started screen
    )
    
    // Pager state for horizontal scrolling
    val pagerState = rememberPagerState(initialPage = 0) {
        screens.size
    }

    val scope = rememberCoroutineScope() // Coroutine scope for animations
    val scrollState = rememberScrollState() // Scroll state for form container

    CustomScaffoldContainer(
        showTopBar = false, // Hide top bar for full-screen experience
        showBottomBar = false, // Hide bottom bar during onboarding
        content = {
            FormContainer(scrollState = scrollState) { // Scrollable container
                // Full screen horizontal pager for onboarding screens
                  HorizontalPager(
                      state = pagerState,
                      modifier = Modifier.fillMaxWidth()
                  ) { index ->
                      OnboardingModelScreen(onBoardingModel = screens[index]) // Display current screen
                  }
                    
                    // Navigation controls row
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = when (pagerState.currentPage) {
                            0 -> Arrangement.End // First screen: only next button
                            screens.size - 1 -> Arrangement.spacedBy(MaterialTheme.customSpacing.normal) // Last screen: back and finish buttons
                            else -> Arrangement.spacedBy(MaterialTheme.customSpacing.normal) // Middle screens: back and next buttons
                        },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Show back button only on second and third screens
                        if (pagerState.currentPage > 0) {
                            CustomOutlinedButton(
                                label = R.string.back,
                                useSmallWidth = true,
                                onClick = {
                                    scope.launch {
                                        pagerState.animateScrollToPage(pagerState.currentPage - 1) // Animate to previous page
                                    }
                                },
                            )
                        }

                        // Page indicator in the center
                        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                            OnBoardingIndicator(
                                pageSize = screens.size,
                                currentPage = pagerState.currentPage,
                            )
                        }

                        // Show next button on first and second screens, finish button on last screen
                        if (pagerState.currentPage < screens.size - 1) {
                            CustomButton(
                                label = R.string.next,
                                useSmallWidth = true,
                                onClick = {
                                    scope.launch {
                                        pagerState.animateScrollToPage(pagerState.currentPage + 1) // Animate to next page
                                    }
                                },
                            )
                        } else {
                            // Finish button on the last screen
                            CustomButton(
                                label = R.string.finish,
                                useSmallWidth = true,
                                onClick = {
                                    onFinished() // Call completion callback
                                },
                            )
                        }
                    }
            }
        }
    )
}

/**
 * ProfileScreenPreview - Preview for OnboardingScreen in dark mode.
 * Note: This preview function has an incorrect name but is used for onboarding preview.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun ProfileScreenPreview() {

    MyAppTheme(
        dynamicColor = false,
        content = {
            OnboardingScreen(
                onFinished = {},
            )
        }
    )
}