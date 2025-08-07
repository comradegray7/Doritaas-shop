package com.example.myapp.view.screens.product

// Import statements for Android Compose UI components and animations
import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.StarHalf
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.myapp.ui.theme.MyAppTheme
import com.example.myapp.ui.theme.customSpacing
import com.example.myapp.view.components.CustomSpacer
import com.example.myapp.ui.theme.LocalWindowSizeConstant
import com.example.myapp.ui.theme.constant
import com.example.myapp.view.components.CustomImageContainer
import com.example.myapp.view.components.CustomLazyRow
import com.example.myapp.view.components.ProductCardShimmer
import com.example.myapp.view.components.shimmerEffect
//import com.example.myapp.view.components.CustomImage
//import com.example.myapp.view.components.VersatileImage
import java.util.Locale

/**
 * ProductCard - Composable function for displaying product information in a card format
 * 
 * This composable creates a visually appealing product card with the following features:
 * - Product image with favorite/wishlist functionality
 * - Product name with text overflow handling
 * - Price display with optional old price (for discounts)
 * - Star rating system with partial star support
 * - Interactive animations (scale and elevation on press)
 * - Optional selection state with border highlighting
 * - More info button for additional product details
 * 
 * The card uses a two-section layout:
 * - Top section: Product image with favorite icon overlay
 * - Bottom section: Product details, pricing, and rating
 * 
 * @param modifier Modifier to be applied to the root composable
 * @param id Unique identifier for the product
 * @param productName Name of the product to display
 * @param price Current price of the product
 * @param imageRes Drawable resource ID for the product image
 * @param isFavorite Boolean indicating if the product is in user's favorites
 * @param onFavoriteClick Callback for favorite button clicks
 * @param rating Optional rating value (0.0 to 5.0)
 * @param oldPrice Optional original price for discount display
 * @param onProductClick Optional callback for product card clicks
 * @param onMoreInfoClick Optional callback for more info button clicks
 * @param isSelected Boolean indicating if the card is in selected state
 * 
 * Usage:
 * ```
 * ProductCard(
 *     productName = "Wireless Headphones",
 *     price = "$99.99",
 *     imageRes = R.drawable.headphones,
 *     rating = 4.5f,
 *     onFavoriteClick = { /* handle favorite */ },
 *     onProductClick = { /* navigate to product details */ }
 * )
 * ```
 */
@Composable
fun ProductCard(
    modifier: Modifier = Modifier,
    id: Int = 0,
    productName: String,
    price: String,
    @DrawableRes imageRes: Int,
    isFavorite: Boolean = false,
    onFavoriteClick: () -> Unit = {},
    rating: Float? = null,
    oldPrice: String? = null,
    onProductClick: (() -> Unit)? = null,
    onMoreInfoClick: (() -> Unit)? = null,
    isSelected: Boolean = false
) {
    // Window size constant for responsive design
    val windowSizeConstant = LocalWindowSizeConstant.current

    // Interaction source for modern ripple handling and press state detection
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    // Scale animation for press effect - card shrinks slightly when pressed
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = tween(
            durationMillis = 150,
            delayMillis = 0
        ),
        label = "scale_animation"
    )

    // Elevation animation for shadow effect - shadow reduces when pressed
    val elevation by animateFloatAsState(
        targetValue = if (isPressed) 2f else 8f,
        animationSpec = tween(
            durationMillis = 150,
            delayMillis = 0
        ),
        label = "elevation_animation"
    )

    // Main product card container with animations and selection state
    Card(
        modifier = modifier
            .width(MaterialTheme.customSpacing.customXXLarge)
            .scale(scale)
            .height(windowSizeConstant.cardHeight)
            .graphicsLayer {
                shadowElevation = elevation
            }
            .clickable(onClick = { onProductClick?.invoke() })
            .border(
                width = if (isSelected) MaterialTheme.customSpacing.border else MaterialTheme.customSpacing.baseSmall,
                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceContainer,
                shape = MaterialTheme.shapes.medium
            )
    ) {
        // Top Half: Product Image with Favorite Icon Overlay
        Box(
            modifier = Modifier
                .weight(1f) // Takes up top half of the card
                .fillMaxWidth()
        ) {
            // Product image container with rounded top corners
            CustomImageContainer(
                data = imageRes,
                shape = RoundedCornerShape(
                    topStart = MaterialTheme.customSpacing.customNormal,
                    topEnd = MaterialTheme.customSpacing.customNormal
                )
            )
            
            // Favorite/Wishlist button with semi-transparent background
            IconButton(
                onClick = onFavoriteClick,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(MaterialTheme.customSpacing.extraSmall)
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            color = Color.Black.copy(alpha = 0.3f),
                            shape = CircleShape
                        )
                        .padding(MaterialTheme.customSpacing.smaller) // Controls background size
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
                        tint = if (isFavorite) Color.Red else Color.White,
                        modifier = Modifier.size(MaterialTheme.customSpacing.normalLarge)
                    )
                }
            }
        }

        // Bottom Half: Product Details Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = MaterialTheme.customSpacing.normal,
                    vertical = MaterialTheme.customSpacing.small
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Product name with text overflow handling
            Text(
                text = productName,
                style = windowSizeConstant.titleTextStyle,
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(MaterialTheme.customSpacing.small))

            // Price section with optional old price and more info button
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.customSpacing.extraSmall)
            ) {
                // Current price display
                Text(
                    text = price,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                // Old price display (optional) - shows strikethrough for discounts
                if (oldPrice != null) {
                    Text(
                        text = oldPrice,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.secondary,
                        textDecoration = TextDecoration.LineThrough
                    )
                }

                // More info icon button (optional)
                if (onMoreInfoClick != null) {
                    IconButton(
                        onClick = onMoreInfoClick,
                        modifier = Modifier.size(MaterialTheme.customSpacing.base)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "More info",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(MaterialTheme.customSpacing.medium)
                        )
                    }
                }
            }

            // Rating section (optional) - displays star rating with partial stars
            if (rating != null) {
                Spacer(modifier = Modifier.height(MaterialTheme.customSpacing.extraSmall))
                ProductRating(
                    rating = rating,
                    maxRating = 5
                )
            }
            CustomSpacer(modifier = Modifier.height(MaterialTheme.customSpacing.small))
        }
    }
}

/**
 * ProductRating - Composable function for displaying star ratings
 *
 * This composable displays a row of stars (full, half, or empty) based on the rating value.
 * It also shows the numeric rating value next to the stars.
 *
 * @param modifier Modifier to be applied to the row
 * @param rating The rating value (e.g., 4.5)
 * @param maxRating The maximum number of stars (default: 5)
 */
@Composable
fun ProductRating(
    modifier: Modifier = Modifier,
    rating: Float,
    maxRating: Int = MaterialTheme.constant.five,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.customSpacing.extraSmall),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(maxRating) { index ->
            val starRating = when {
                index < rating.toInt() -> 1f // Full star
                index < rating && rating % 1 != 0f -> rating % 1 // Partial star
                else -> 0f // Empty star
            }

            Icon(
                imageVector = when {
                    starRating >= 1f -> Icons.Filled.Star
                    starRating > 0f -> Icons.AutoMirrored.Filled.StarHalf
                    else -> Icons.Outlined.StarBorder
                },
                contentDescription = null,
                tint = if (starRating > 0f) Color(0xFFFFD700) else Color.Gray.copy(alpha = 0.3f),
                modifier = Modifier.size(MaterialTheme.customSpacing.baseNormal)
            )
        }

        Spacer(modifier = Modifier.width(MaterialTheme.customSpacing.small))

        Text(
            text = String.format(Locale.US, "%.1f", rating),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

/**
 * ProductCardShimmerRow - Displays a row of shimmer placeholders for loading state
 *
 * @param itemCount Number of shimmer cards to display
 */
@Composable
fun ProductCardShimmerRow(
    itemCount: Int = 5
) {
    CustomLazyRow {
        items(itemCount) {
            ProductCardShimmer(
                modifier = Modifier,
                isSelected = false
            )
        }
    }
}

/**
 * ProductItem - Data class representing a product for use in lists or previews
 */
data class ProductItem(
    val id: Int,
    val productName: String,
    val price: String,
    @DrawableRes val imageRes: Int,
    val rating: Float? = null,
    val oldPrice: String? = null,
    val isFavorite: Boolean = false,
)
