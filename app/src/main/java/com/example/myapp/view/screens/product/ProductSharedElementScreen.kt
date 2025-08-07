package com.example.myapp.view.screens.product

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.myapp.ui.theme.LocalWindowSizeConstant
import com.example.myapp.view.components.CardItemData
import com.example.myapp.view.components.CustomImageContainer
import com.example.myapp.view.components.CustomScaffoldContainer
import com.example.myapp.view.components.CustomSpacer
import com.example.myapp.view.components.FormContainer
import com.example.myapp.view.components.HeadlineWidget
import com.example.myapp.view.components.PaddedSection

/**
 * ProductSharedElementScreen - Product detail screen with shared element transitions.
 *
 * This composable creates a product detail view that supports smooth shared element
 * transitions from the product list. It displays the product image and details with
 * animated transitions that create a seamless user experience when navigating
 * between product list and detail views.
 *
 * Features:
 * - Shared element transitions for product images
 * - Shared element transitions for product text content
 * - Smooth animations with custom easing curves
 * - Responsive layout with proper spacing
 *
 * @param id Unique identifier for the shared element transition
 * @param item Product data to display
 * @param sharedTransitionScope Scope for managing shared element transitions
 * @param animatedContentScope Scope for managing content animations
 * @param onBackPressed Callback for back navigation (currently commented out)
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ProductSharedElementScreen(
    id: String,
    item: CardItemData,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    onBackPressed: () -> Unit
) {
    val windowSizeConstant = LocalWindowSizeConstant.current
    val scrollState = rememberScrollState()

    // Use shared transition scope for coordinated animations
    CustomScaffoldContainer(
        onRefresh = {
            // Add your refresh logic here
            println("Refreshing login screen...")
            // Example: reload user data, refresh tokens, etc.
        },
        showBottomBar = false,
        showTopBar = false,
        showBackArrow = false,
        content = {
            with(sharedTransitionScope) {
                // Main content column with centered alignment and consistent spacing
                FormContainer(scrollState = scrollState) {
                    // Top spacing for proper layout
                    CustomSpacer()

                    // Main content section with padding
                    PaddedSection {
                        // Product image with shared element transition
                        CustomImageContainer(
                            data = item.imageRes,
                            contentDescription = item.category,
                            modifier = Modifier
                                .sharedBounds(
                                    sharedContentState = rememberSharedContentState(key = "image-$id"),
                                    animatedVisibilityScope = animatedContentScope,
                                    boundsTransform = { _, _ ->
                                        tween(
                                            durationMillis = 500, // Animation duration for smooth transition
                                            easing = FastOutSlowInEasing // Smooth easing curve for natural movement
                                        )
                                    }
                                )
                                .size(windowSizeConstant.productImageSize)
                        )

                        CustomSpacer()

                        // Product headline widget with shared element transition for text content
                        HeadlineWidget(
                            modifier = Modifier.sharedBounds(
                                sharedContentState = rememberSharedContentState(key = "text-$id"),
                                animatedVisibilityScope = animatedContentScope
                            ),
                            middleTextStr = item.name, // Product name
                            subMiddleTextStr = item.description // Product description
                        )
                    }
                }
            }
        }
    )


}
