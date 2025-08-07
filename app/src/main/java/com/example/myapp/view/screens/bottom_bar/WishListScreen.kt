package com.example.myapp.view.screens.bottom_bar

import android.content.res.Configuration
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.myapp.R
import com.example.myapp.ui.theme.LocalWindowSizeConstant
import com.example.myapp.ui.theme.MyAppTheme
import com.example.myapp.ui.theme.customSpacing
import com.example.myapp.view.components.CardItemData
import com.example.myapp.view.components.CustomItemCard
import com.example.myapp.view.components.CustomLazyColumn
import com.example.myapp.view.components.CustomScaffoldContainer
import com.example.myapp.view.components.PaddedSection
import com.example.myapp.view.components.ProductShimmerList
import com.example.myapp.view.components.TopBarActionsShimmer
import com.example.myapp.view.components.custom.buttons.ButtonIconComposable
import com.example.myapp.view.screens.product.ProductSummaryCard
import com.example.myapp.view.utils.ButtonIcon
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * WishlistScreen - Displays user's wishlist items with add to cart and remove functionality.
 *
 * This composable shows a list of wishlist items with options to add them to cart
 * or remove them from the wishlist. It handles empty states and provides
 * shared element transitions for smooth navigation.
 *
 * @param modifier Optional modifier to apply to the screen
 * @param onAddToCart Callback for when an item is added to cart
 * @param onRemoveFromWishlist Callback for when an item is removed from wishlist
 * @param onSearchClick Callback for when the search icon is clicked
 * @param onCartClick Callback for when the cart icon is clicked
 * @param onItemClick Callback for when an item is clicked
 * @param sharedTransitionScope Scope for shared element transitions
 * @param animatedContentScope Scope for animated content transitions
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun WishlistScreen(
    modifier: Modifier = Modifier,
    onAddToCart: (CardItemData) -> Unit = {},
    onRemoveFromWishlist: (CardItemData) -> Unit = {},
    onSearchClick: () -> Unit,
    onCartClick: () -> Unit,
    onItemClick: (CardItemData) -> Unit = {},
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
) {
    // Sample wishlist items for demonstration
    var wishlistItems by remember {
        // For empty list testing: mutableStateOf(emptyList<CardItemData>())
        mutableStateOf(
            listOf(
                CardItemData(
                    id = "1",
                    name = "Wireless Bluetooth Headphones",
                    price = 89999.99,
                    originalPrice = 119.99,
                    quantity = 1,
                    rating = 3.3f,
                    category = "Electronics",
                    inStock = true,
                    currentPrice = 89.99,
                    imageRes = R.drawable.elias,
                    description = "Premium noise-cancelling headphones with 30-hour battery life"
                ),
                CardItemData(
                    id = "2",
                    name = "Smart Fitness Watch",
                    price = 1994848.99,
                    originalPrice = 249.99,
                    quantity = 1,
                    category = "Wearables",
                    inStock = true,
                    currentPrice = 199.99,
                    imageRes = R.drawable.doritaas,
                    description = "Track your workouts, heart rate, and sleep patterns"
                ),
                CardItemData(
                    id = "3",
                    name = "Premium Coffee Maker",
                    price = 149.99,
                    originalPrice = 189.99,
                    quantity = 2,
                    rating = 4.3f,
                    category = "Home & Kitchen",
                    inStock = true,
                    currentPrice = 149.99,
                    imageRes = R.drawable.turturro,
                    description = "Programmable coffee maker with thermal carafe"
                ),
                CardItemData(
                    id = "4",
                    name = "Designer Leather Jacket",
                    price = 299.99,
                    originalPrice = 399.99,
                    quantity = 1,
                    category = "Clothing",
                    inStock = true,
                    currentPrice = 299.99,
                    imageRes = R.drawable.paul_gaudriault,
                    description = "Genuine leather jacket with premium stitching"
                ),
                CardItemData(
                    id = "7",
                    name = "Wireless Charging Pad",
                    price = 29.99,
                    originalPrice = 39.99,
                    quantity = 3,
                    rating = 4.3f,
                    category = "Accessories",
                    inStock = true,
                    currentPrice = 29.99,
                    imageRes = R.drawable.domino,
                    description = "Fast-charging pad compatible with all Qi devices"
                ),
                CardItemData(
                    id = "8",
                    name = "4K Action Camera",
                    price = 189.99,
                    originalPrice = 249.99,
                    quantity = 1,
                    category = "Electronics",
                    inStock = true,
                    rating = 4.3f,
                    currentPrice = 189.99,
                    imageRes = R.drawable.the_dk,
                    description = "Ultra HD camera with waterproof casing"
                ),
                CardItemData(
                    id = "9",
                    name = "Ergonomic Office Chair",
                    price = 259.99,
                    originalPrice = 299.99,
                    quantity = 1,
                    category = "Furniture",
                    inStock = true,
                    currentPrice = 259.99,
                    imageRes = R.drawable.imani,
                    description = "Adjustable chair with lumbar support"
                ),
                CardItemData(
                    id = "10",
                    name = "Stainless Steel Water Bottle",
                    price = 24.99,
                    originalPrice = 24.99,
                    quantity = 2,
                    category = "Lifestyle",
                    inStock = true,
                    currentPrice = 24.99,
                    imageRes = R.drawable.irene_kredenets,
                    description = "Insulated bottle that keeps drinks cold for 24 hours"
                ),
            )
        )
    }

    // Calculate total value of wishlist items
    val total by remember(wishlistItems) {
        derivedStateOf {
            wishlistItems.sumOf { it.currentPrice * it.quantity }
        }
    }

    // Calculate total savings from original prices
    val savings by remember(wishlistItems) {
        derivedStateOf {
            wishlistItems.sumOf {
                ((it.originalPrice ?: it.currentPrice) - it.currentPrice) * it.quantity
            }
        }
    }

    var loading by remember { mutableStateOf(true) }

    // Simulate loading delay
    LaunchedEffect(Unit) {
        delay(1500) // simulate initial loading delay
        loading = false
    }

    CustomScaffoldContainer(
        onRefresh = {
            // Add your refresh logic here
            println("Refreshing login screen...")
            // Example: reload user data, refresh tokens, etc.
        },
        showBackArrow = false,
        showBottomBar = false,
        title = R.string.wishlist_title,
        content = {
            if (loading) {
                // Show shimmer loading state
                ProductShimmerList()
            } else if (wishlistItems.isEmpty()) {
                // Show empty state when no items
                EmptyWishlistState(modifier = Modifier.fillMaxSize())
            } else {
                // Show wishlist items
                PaddedSection {
                    // Summary card showing item count and savings
                    ProductSummaryCard(
                        itemCount = wishlistItems.size,
                        savings = savings,
                        trailingIcon = {
                            Icon(Icons.Filled.Favorite, contentDescription = null)
                        }
                    )
                }
                // List of wishlist items
                CustomLazyColumn {
                    items(wishlistItems) { item ->
                        WishlistItemCard(
                            item = item,
                            onAddToCart = { onAddToCart(item) },
                            onItemClick = { onItemClick(item) },
                            sharedTransitionScope = sharedTransitionScope,
                            animatedContentScope = animatedContentScope,
                            onRemoveFromWishlist = {
                                // Remove item from local state and notify parent
                                wishlistItems = wishlistItems.filter { it.id != item.id }
                                onRemoveFromWishlist(item)
                            }
                        )
                    }
                }
            }
        },
        actions = {
            if (loading) {
                TopBarActionsShimmer()
            } else {
                // Search and cart action buttons
                ButtonIconComposable(
                    buttonIcon = ButtonIcon.Vector(Icons.Filled.Search),
                    onClick = {},
                    contentDescription = "Search"
                )
                ButtonIconComposable(
                    buttonIcon = ButtonIcon.Vector(Icons.Filled.ShoppingCart),
                    onClick = { onCartClick() },
                    contentDescription = "shopping cart"
                )
            }
        }
    )
}

/**
 * WishlistItemCard - Individual wishlist item card with remove functionality.
 *
 * This composable displays a single wishlist item with a remove button.
 * It uses shared element transitions for smooth navigation.
 *
 * @param modifier Optional modifier to apply to the card
 * @param item The wishlist item data to display
 * @param onAddToCart Callback for when item is added to cart
 * @param onItemClick Callback for when item is clicked
 * @param onRemoveFromWishlist Callback for when item is removed from wishlist
 * @param sharedTransitionScope Scope for shared element transitions
 * @param animatedContentScope Scope for animated content transitions
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun WishlistItemCard(
    modifier: Modifier = Modifier,
    item: CardItemData,
    onAddToCart: () -> Unit,
    onItemClick: () -> Unit = {},
    onRemoveFromWishlist: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
) {
    val windowSizeConstant = LocalWindowSizeConstant.current

    CustomItemCard(
        modifier,
        onItemClick = onItemClick,
        item = item,
        sharedTransitionScope = sharedTransitionScope,
        animatedContentScope = animatedContentScope,
        actions = {
            // Remove from wishlist button
            OutlinedButton(
                onClick = onRemoveFromWishlist,
                modifier = Modifier.size(MaterialTheme.customSpacing.outline),
                shape = CircleShape,
                contentPadding = PaddingValues(MaterialTheme.customSpacing.none),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Remove from Wishlist",
                    modifier = Modifier.size(windowSizeConstant.iconPadding)
                )
            }
        },
    )
}

/**
 * EmptyWishlistState - Displays when the wishlist is empty.
 *
 * This composable shows a friendly message encouraging users to add items
 * to their wishlist with a call-to-action button to start shopping.
 *
 * @param modifier Optional modifier to apply to the container
 */
@Composable
fun EmptyWishlistState(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Empty wishlist icon
        Icon(
            imageVector = Icons.Default.FavoriteBorder,
            contentDescription = null,
            modifier = Modifier.size(MaterialTheme.customSpacing.boxLarge),
            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
        )

        Spacer(modifier = Modifier.height(MaterialTheme.customSpacing.large))

        // Empty state title
        Text(
            text = "Your wishlist is empty",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(MaterialTheme.customSpacing.small))

        // Empty state description
        Text(
            text = "Add items you love to your wishlist.\nReview them anytime and easily move them to your bag.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            lineHeight = 20.sp
        )

        Spacer(modifier = Modifier.height(MaterialTheme.customSpacing.baseMedium))

        // Call-to-action button
        Button(
            onClick = { /* Navigate to shopping */ },
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(MaterialTheme.customSpacing.outline),
            shape = RoundedCornerShape(MaterialTheme.customSpacing.large)
        ) {
            Text(
                text = "Start Shopping",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
