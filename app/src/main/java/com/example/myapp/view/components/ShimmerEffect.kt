package com.example.myapp.view.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.example.myapp.ui.theme.LocalWindowSizeConstant
import com.example.myapp.ui.theme.customSpacing
import com.example.myapp.view.screens.product.ProductCardShimmerRow

/**
 * ShimmerEffect - Extension function that adds a shimmer loading animation to any composable.
 * 
 * This modifier creates a moving gradient effect that simulates content loading.
 * It uses an infinite animation with a linear gradient brush that moves across the surface.
 * 
 * Usage:
 * ```
 * Box(
 *     modifier = Modifier
 *         .size(100.dp)
 *         .shimmerEffect()
 * )
 * ```
 */
fun Modifier.shimmerEffect(): Modifier = composed {
    // Define shimmer colors with varying opacity for gradient effect
    val shimmerColors = listOf(
        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f), // Main shimmer color
        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f), // Transparent middle
        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f), // Main shimmer color
    )

    // Create infinite animation transition for continuous shimmer effect
    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateAnim by transition.animateFloat(
        initialValue = 0f, // Start position
        targetValue = 1000f, // End position
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing), // 1.2 second duration with linear easing
            repeatMode = RepeatMode.Restart // Restart animation when it completes
        ),
        label = "shimmerAnimation"
    )

    // Create linear gradient brush that moves across the surface
    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero, // Start from top-left
        end = Offset(x = translateAnim, y = translateAnim) // End at animated position
    )

    // Apply the shimmer background
    background(brush = brush)
}

/**
 * ProductCarouselShimmer - Composable for displaying a shimmer loading state for product carousels.
 * 
 * This composable creates a horizontal list of shimmer placeholders that mimic
 * the layout of a product carousel. It uses adaptive sizing based on window size.
 * 
 * Usage:
 * ```
 * ProductCarouselShimmer()
 * ```
 */
@Composable
fun ProductCarouselShimmer() {
    val windowSizeConstant = LocalWindowSizeConstant.current

    Column(
        modifier = Modifier.fillMaxWidth(), // Take full width
        horizontalAlignment = Alignment.CenterHorizontally // Center content
    ) {
        LazyRow(
            contentPadding = PaddingValues(horizontal = windowSizeConstant.contentPadding), // Adaptive horizontal padding
            horizontalArrangement = Arrangement.spacedBy(windowSizeConstant.carouselPageSpacing), // Adaptive spacing
            modifier = Modifier
                .fillMaxWidth()
                .height(windowSizeConstant.carouselImageHeight) // Adaptive height
        ) {
            items(5) { // Display 5 shimmer items
                Box(
                    modifier = Modifier
                        .width(windowSizeConstant.carouselImageWidth) // Adaptive width
                        .height(windowSizeConstant.carouselImageHeight) // Adaptive height
                        .clip(MaterialTheme.shapes.large) // Rounded corners
                        .shimmerEffect() // Apply shimmer animation
                )
            }
        }
    }
}

/**
 * CustomItemCardShimmer - Composable for displaying a shimmer loading state for item cards.
 * 
 * This composable creates a card-shaped shimmer placeholder that mimics the layout
 * of a typical item card with image, text content, and action buttons.
 * 
 * @param modifier Optional modifier to apply to the shimmer card
 * 
 * Usage:
 * ```
 * CustomItemCardShimmer()
 * ```
 */
@Composable
fun CustomItemCardShimmer(
    modifier: Modifier = Modifier
) {
    val windowSizeConstant = LocalWindowSizeConstant.current
    val spacing = MaterialTheme.customSpacing

    PaddedSection {
        Card(
            modifier = windowSizeConstant.adaptiveWidthModifier,
            elevation = CardDefaults.cardElevation(
                defaultElevation = spacing.extraSmall
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(windowSizeConstant.listCardPadding),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Shimmer placeholder for item image
                Box(
                    modifier = Modifier
                        .size(windowSizeConstant.listImagePadding)
                        .clip(MaterialTheme.shapes.medium)
                        .shimmerEffect()
                )

                // Shimmer placeholders for text content
                Column(
                    modifier = Modifier
                        .padding(horizontal = windowSizeConstant.listRightPadding)
                        .weight(1f, fill = false)
                ) {
                    // Main title shimmer
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(spacing.medium) // 16.dp -> medium
                            .clip(RoundedCornerShape(spacing.extraSmall)) // 4.dp
                            .shimmerEffect()
                    )

                    Spacer(modifier = Modifier.height(spacing.extraSmall)) // 4.dp

                    // Subtitle shimmer
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .height(spacing.baseNormal) // 14.dp
                            .clip(RoundedCornerShape(spacing.extraSmall)) // 4.dp
                            .shimmerEffect()
                    )

                    Spacer(modifier = Modifier.height(spacing.small)) // 8.dp

                    // Price shimmer
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.3f)
                            .height(spacing.medium) // 16.dp
                            .clip(RoundedCornerShape(spacing.extraSmall)) // 4.dp
                            .shimmerEffect()
                    )
                }

                // Shimmer placeholders for action buttons
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(spacing.normal) // 12.dp
                ) {
                    repeat(2) {
                        Box(
                            modifier = Modifier
                                .size(spacing.boxMedium) // 24.dp
                                .clip(CircleShape)
                                .shimmerEffect()
                        )
                    }
                }
            }
        }
    }
}

/**
 * ProductCardShimmer - Displays a shimmer placeholder for a product card.
 *
 * @param modifier Modifier to apply to the card.
 * @param isSelected Whether the card is in a selected state (affects border color).
 */
@Composable
fun ProductCardShimmer(
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
) {
    val windowSizeConstant = LocalWindowSizeConstant.current
    val spacing = MaterialTheme.customSpacing

    Card(
        modifier = modifier
            .width(spacing.customXXLarge) // 180.dp
            .height(windowSizeConstant.cardHeight)
            .border(
                width = if (isSelected) spacing.border else spacing.baseSmall,
                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceContainer,
                shape = MaterialTheme.shapes.medium
            )
    ) {
        Column {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clip(
                        RoundedCornerShape(
                            topStart = spacing.customNormal, // 10.dp
                            topEnd = spacing.customNormal
                        )
                    )
                    .shimmerEffect()
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = spacing.normal, // 12.dp
                        vertical = spacing.small // 8.dp
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(
                    modifier = Modifier
                        .height(spacing.normal) // 12.dp
                        .fillMaxWidth(0.8f)
                        .clip(RoundedCornerShape(spacing.normal)) // 12.dp
                        .shimmerEffect()
                )

                Spacer(modifier = Modifier.height(spacing.small)) // 8.dp

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(spacing.extraSmall) // 4.dp
                ) {
                    Box(
                        modifier = Modifier
                            .height(spacing.medium) // 16.dp
                            .width(spacing.boxMedium) // 40.dp
                            .clip(RoundedCornerShape(spacing.extraSmall)) // 4.dp
                            .shimmerEffect()
                    )

                    Box(
                        modifier = Modifier
                            .height(spacing.normal) // 12.dp
                            .width(spacing.outlineNormal) // 30.dp
                            .clip(RoundedCornerShape(spacing.extraSmall)) // 4.dp
                            .shimmerEffect()
                    )

                    Box(
                        modifier = Modifier
                            .size(spacing.medium) // 16.dp
                            .clip(CircleShape)
                            .shimmerEffect()
                    )
                }

                Spacer(modifier = Modifier.height(spacing.extraSmall)) // 4.dp

                ProductRatingShimmer()

                Spacer(modifier = Modifier.height(spacing.small)) // 8.dp
            }
        }
    }
}

/**
 * ProductRatingShimmer - Displays a shimmer placeholder for a product rating row.
 *
 * @param modifier Modifier to apply to the row.
 * @param maxRating Number of rating icons to display.
 */
@Composable
fun ProductRatingShimmer(
    modifier: Modifier = Modifier,
    maxRating: Int = 5
) {
    val spacing = MaterialTheme.customSpacing

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(spacing.extraSmall), // 4.dp
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(maxRating) {
            Box(
                modifier = Modifier
                    .size(spacing.baseNormal) // 14.dp
                    .clip(CircleShape)
                    .shimmerEffect()
            )
        }

        Spacer(modifier = Modifier.width(spacing.small)) // 8.dp

        Box(
            modifier = Modifier
                .height(spacing.normal) // 12.dp
                .width(spacing.large) // 24.dp
                .clip(RoundedCornerShape(spacing.extraSmall)) // 4.dp
                .shimmerEffect()
        )
    }
}

/**
 * ClickableSearchBarShimmer - Shimmer placeholder for a clickable search bar.
 *
 * @param modifier Modifier to apply to the search bar.
 */
@Composable
fun ClickableSearchBarShimmer(
    modifier: Modifier = Modifier
) {
    val windowSizeConstant = LocalWindowSizeConstant.current
    val spacing = MaterialTheme.customSpacing

    Box(
        modifier = windowSizeConstant.adaptiveWidthModifier
            .clip(MaterialTheme.shapes.medium)
            .height(windowSizeConstant.adaptiveHeight)
            .shimmerEffect()
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(horizontal = spacing.normal), // 12.dp
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(spacing.large) // 24.dp
                        .clip(CircleShape)
                        .shimmerEffect()
                )
                Spacer(modifier = Modifier.width(spacing.small)) // 8.dp
                Box(
                    modifier = Modifier
                        .height(spacing.medium) // 16.dp
                        .width(spacing.xxLarge) // 100.dp
                        .clip(RoundedCornerShape(spacing.extraSmall)) // 4.dp
                        .shimmerEffect()
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(spacing.extraSmall), // 4.dp
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(spacing.boxMedium) // 40.dp
                        .clip(CircleShape)
                        .shimmerEffect()
                )

                Box(
                    modifier = Modifier
                        .width(spacing.border) // 1.5.dp
                        .height(spacing.large) // 24.dp
                        .shimmerEffect()
                )

                Box(
                    modifier = Modifier
                        .size(spacing.boxMedium) // 40.dp
                        .clip(CircleShape)
                        .shimmerEffect()
                )
            }
        }
    }
}

/**
 * SmallProductImageShimmer - Shimmer placeholders for a row of small product images.
 *
 * @param count Number of shimmer images to display.
 */
@Composable
fun SmallProductImageShimmer(
    count: Int = 6
) {
    val spacing = MaterialTheme.customSpacing

    CustomLazyRow {
        items(count) {
            Box(
                modifier = Modifier
                    .padding(horizontal = spacing.extraSmall) // 4.dp
                    .size(spacing.customMedium) // 60.dp
                    .clip(MaterialTheme.shapes.large)
                    .shimmerEffect()
            )
        }
    }
}

/**
 * NavigationBarShimmer - Shimmer placeholder for a bottom navigation bar.
 *
 * @param modifier Modifier to apply to the navigation bar.
 */
@Composable
fun NavigationBarShimmer(
    modifier: Modifier = Modifier
) {
    val windowSizeConstant = LocalWindowSizeConstant.current
    val spacing = MaterialTheme.customSpacing

    NavigationBar(
        windowInsets = NavigationBarDefaults.windowInsets,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .padding(
                    vertical = spacing.medium,
                    horizontal = windowSizeConstant.contentPadding
                )
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .height(spacing.outline)
                    .fillMaxWidth(1f)
                    .clip(MaterialTheme.shapes.medium)
                    .shimmerEffect()
            )
        }
    }
}

/**
 * CategoryShimmerPlaceholder - Shimmer placeholders for product categories.
 *
 * @param count Number of category shimmer items to display.
 */
@Composable
fun CategoryShimmerPlaceholder(
    count: Int = 8
) {
    val spacing = MaterialTheme.customSpacing

    CustomLazyRow {
        items(count) {
            Box(
                modifier = Modifier
                    .padding(horizontal = spacing.extraSmall)
                    .clip(MaterialTheme.shapes.extraLarge)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .shimmerEffect()
                    .height(spacing.outlineMedium)
                    .width((60..100).random().dp)
            )
        }
    }
}

/**
 * ButtonIconShimmer - Shimmer placeholder for a circular button icon.
 *
 * @param modifier Modifier to apply to the icon.
 */
@Composable
fun ButtonIconShimmer(
    modifier: Modifier = Modifier
) {
    val spacing = MaterialTheme.customSpacing

    Box(
        modifier = modifier
            .size(spacing.outlineMedium)
            .clip(CircleShape)
            .shimmerEffect()
    )
}

/**
 * TopBarActionsShimmer - Shimmer placeholders for top bar action icons.
 */
@Composable
fun TopBarActionsShimmer() {
    val spacing = MaterialTheme.customSpacing

    Row(
        horizontalArrangement = Arrangement.spacedBy(spacing.small)
    ) {
        ButtonIconShimmer()
        ButtonIconShimmer()
    }
}

/**
 * ProductSummaryCardShimmer - Shimmer placeholder for a product summary card.
 *
 * @param modifier Modifier to apply to the card.
 */
@Composable
fun ProductSummaryCardShimmer(
    modifier: Modifier = Modifier
) {
    val windowSizeConstant = LocalWindowSizeConstant.current
    val spacing = MaterialTheme.customSpacing

    val radialGradient = Brush.radialGradient(
        colors = listOf(
            MaterialTheme.colorScheme.surfaceContainer,
            MaterialTheme.colorScheme.surface,
            MaterialTheme.colorScheme.surfaceContainerHighest
        ),
        radius = 800f
    )

    Card(
        modifier = windowSizeConstant.adaptiveWidthModifier,
        elevation = CardDefaults.cardElevation(defaultElevation = spacing.small)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(radialGradient)
                .padding(windowSizeConstant.listCardPadding),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(
                        width = spacing.customMedium,
                        height = spacing.base
                    )
                    .clip(MaterialTheme.shapes.small)
                    .shimmerEffect()
            )

            Box(
                modifier = Modifier
                    .size(windowSizeConstant.productSummaryImagePadding)
                    .clip(MaterialTheme.shapes.medium)
                    .shimmerEffect()
            )

            Box(
                modifier = Modifier
                    .weight(1f, fill = false)
                    .padding(horizontal = windowSizeConstant.listRightPadding)
                    .height(spacing.base)
                    .clip(MaterialTheme.shapes.small)
                    .shimmerEffect()
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(windowSizeConstant.baseVerticalPadding)
            ) {
                Box(
                    modifier = Modifier
                        .size(
                            width = spacing.baseLarge,
                            height = spacing.medium
                        )
                        .clip(MaterialTheme.shapes.small)
                        .shimmerEffect()
                )
                Box(
                    modifier = Modifier
                        .size(
                            width = spacing.customMedium,
                            height = spacing.base
                        )
                        .clip(MaterialTheme.shapes.small)
                        .shimmerEffect()
                )
            }

            Box(
                modifier = Modifier
                    .size(spacing.large)
                    .clip(CircleShape)
                    .shimmerEffect()
            )
        }
    }
}
/**
 * SearchListShimmer - Shimmer placeholders for a list of search results.
 */
@Composable
fun SearchListShimmer() {
    val windowSizeConstant = LocalWindowSizeConstant.current
    val spacing = MaterialTheme.customSpacing

    PaddedSection {
        CustomLazyColumn {
            items(6) {
                Card(
                    modifier = windowSizeConstant.adaptiveWidthModifier,
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = spacing.extraSmall
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(windowSizeConstant.listCardPadding),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(windowSizeConstant.listImagePadding)
                                .clip(MaterialTheme.shapes.medium)
                                .shimmerEffect()
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .height(spacing.base)
                                .clip(RoundedCornerShape(spacing.extraSmall))
                                .shimmerEffect()
                        )

                        Box(
                            modifier = Modifier
                                .size(spacing.large)
                                .clip(CircleShape)
                                .shimmerEffect()
                        )
                    }
                }
            }
        }
    }
}

/**
 * ProfileCardShimmer - Shimmer placeholders for a profile card and related items.
 */
@Composable
fun ProfileCardShimmer() {
    val windowSizeConstant = LocalWindowSizeConstant.current
    val spacing = MaterialTheme.customSpacing

    CustomLazyColumn {
        item {
            PaddedSection {
                Box(
                    modifier = Modifier
                        .size(windowSizeConstant.productImageSize)
                        .clip(MaterialTheme.shapes.medium)
                        .shimmerEffect()
                )
            }
        }
        items(3) {
            CustomItemCardShimmer()
        }
        item {
            Column(
                modifier = windowSizeConstant.adaptiveWidthModifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .height(spacing.box)
                        .fillMaxWidth(0.8f)
                        .clip(MaterialTheme.shapes.medium)
                        .shimmerEffect()
                )
            }
        }
    }
}
/**
 * ProductDescriptionShimmer - Shimmer placeholders for a product description screen.
 */
@Composable
fun ProductDescriptionShimmer() {
    val spacing = MaterialTheme.customSpacing

    CustomLazyColumn {
        item {
            PaddedSection {
                Box(
                    modifier = Modifier
                        .size(LocalWindowSizeConstant.current.productImageSize)
                        .clip(MaterialTheme.shapes.medium)
                        .shimmerEffect()
                )
            }
        }

        item {
            SmallProductImageShimmer(count = 6)
        }

        item {
            PaddedSection {
                Column(
                    verticalArrangement = Arrangement.spacedBy(spacing.small)
                ) {
                    repeat(4) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.7f)
                                .height(spacing.base)
                                .clip(MaterialTheme.shapes.small)
                                .shimmerEffect()
                        )
                    }
                }
            }
        }

        item {
            PaddedSection {
                Row(horizontalArrangement = Arrangement.spacedBy(spacing.normal)) {
                    repeat(4) {
                        Box(
                            modifier = Modifier
                                .size(spacing.boxMedium)
                                .clip(CircleShape)
                                .shimmerEffect()
                        )
                    }
                }
                Spacer(modifier = Modifier.height(spacing.normal))
                Row(horizontalArrangement = Arrangement.spacedBy(spacing.normal)) {
                    repeat(4) {
                        Box(
                            modifier = Modifier
                                .width(spacing.mediumLarge)
                                .height(spacing.outlineMedium)
                                .clip(MaterialTheme.shapes.small)
                                .shimmerEffect()
                        )
                    }
                }
            }
        }

        item {
            PaddedSection {
                repeat(3) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(spacing.height)
                            .clip(MaterialTheme.shapes.medium)
                            .shimmerEffect()
                    )
                    Spacer(modifier = Modifier.height(spacing.normal))
                }
            }
        }

        item {
            PaddedSection {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.medium)
                        .shimmerEffect()
                        .padding(spacing.medium)
                ) {
                    repeat(3) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                                .height(spacing.medium)
                                .clip(MaterialTheme.shapes.small)
                                .shimmerEffect()
                        )
                        Spacer(modifier = Modifier.height(spacing.small))
                    }
                }
            }
        }

        item {
            PaddedSection {
                Row(horizontalArrangement = Arrangement.spacedBy(spacing.normal)) {
                    repeat(3) {
                        Box(
                            modifier = Modifier
                                .size(spacing.outlineMedium)
                                .clip(MaterialTheme.shapes.small)
                                .shimmerEffect()
                        )
                    }
                }
            }
        }

        item {
            PaddedSection {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(spacing.outline)
                        .clip(MaterialTheme.shapes.medium)
                        .shimmerEffect()
                )
            }
        }
    }
}

/**
 * ProductShimmerList - Shimmer placeholders for a product summary and a list of item cards.
 */
@Composable
fun ProductShimmerList() {
    val spacing = MaterialTheme.customSpacing

    PaddedSection {
        ProductSummaryCardShimmer()
    }
    CustomLazyColumn {
        items(8) {
            CustomItemCardShimmer()
        }
    }
}

/**
 * ProductShimmer - Shimmer placeholders for a product carousel and a list of product cards.
 */
@Composable
fun ProductShimmer() {
    CustomLazyColumn {
        item {
            ProductCarouselShimmer()
        }
        items(8) {
            ProductCardShimmerRow()
        }
    }
}
