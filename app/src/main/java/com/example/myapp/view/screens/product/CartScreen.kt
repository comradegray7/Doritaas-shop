package com.example.myapp.view.screens.product

import android.content.res.Configuration
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingBasket
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.myapp.R
import com.example.myapp.ui.theme.MyAppTheme
import com.example.myapp.ui.theme.customSpacing
import com.example.myapp.view.components.CardItemData
import com.example.myapp.view.components.CustomBottomSection
import com.example.myapp.view.components.CustomItemCard
import com.example.myapp.view.components.CustomLazyColumn
import com.example.myapp.view.components.CustomScaffoldContainer
import com.example.myapp.view.components.CustomSpacer
import com.example.myapp.view.components.NavigationBarShimmer
import com.example.myapp.view.components.PaddedSection
import com.example.myapp.view.components.ProductShimmerList
import com.example.myapp.view.components.TopBarActionsShimmer
import com.example.myapp.view.components.custom.buttons.ButtonIconComposable
import com.example.myapp.view.screens.bottom_bar.EmptyWishlistState
import com.example.myapp.view.utils.ButtonIcon
import kotlinx.coroutines.delay

/**
 * Main cart screen composable that displays the user's shopping cart.
 * Shows cart items, total price, savings, and checkout functionality.
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun CartScreen(
    onSearchClick: () -> Unit,
    onBackNavigation: () -> Unit,
    onItemClick: (CardItemData) -> Unit = {},
    onCheckOutClick: () -> Unit = {},
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope){

    // Sample cart items data - in real app this would come from a repository
    var cartItems by remember {
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

    // Calculate total price of all items in cart
    val total by remember(cartItems) {
        derivedStateOf {
            cartItems.sumOf { it.currentPrice * it.quantity }
        }
    }

    // Calculate total savings from original prices
    val savings by remember(cartItems) {
        derivedStateOf {
            cartItems.sumOf {
                ((it.originalPrice ?: it.currentPrice) - it.currentPrice) * it.quantity
            }
        }
    }

    // Callback to update item quantity in cart
    val onQuantityChange = remember {
        { itemId: String, newQuantity: Int ->
            cartItems = cartItems.map {
                if (it.id == itemId) it.copy(quantity = newQuantity)
                else it
            }
        }
    }

    // Callback to remove item from cart
    val onRemoveItem = remember {
        { itemId: String ->
            cartItems = cartItems.filter { it.id != itemId }
        }
    }

    // Loading state for initial data fetch
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
        onNavigateBack = {
           onBackNavigation()
        },
        title = R.string.shopping_cart_title,
        bottomBarContent = {
            if (loading) {
                NavigationBarShimmer()
            } else {
            CustomBottomSection(
                total = total, onClick = { onCheckOutClick()},
                actionLabel = R.string.check_out,
            ) }
        },
        content = {
            if (loading) {
                ProductShimmerList()
            } else if (cartItems.isEmpty()) {
                 EmptyCartState(modifier = Modifier)
            } else {
                // Display cart summary with item count and savings
                PaddedSection {
                    ProductSummaryCard(
                        itemCount = cartItems.size,
                        savings = savings,
                        trailingIcon = {Icon(Icons.Filled.ShoppingBasket, contentDescription = null)}
                    )
                }
                // Display list of cart items
                CustomLazyColumn{
                items(cartItems, key = { it.id }) { item ->
                    CartItemCard(
                        item = item,
                        onQuantityChange = { newQuantity ->
                            onQuantityChange(item.id, newQuantity)
                        },
                        onRemove = {
                            onRemoveItem(item.id)
                        },
                        onItemClick = {onItemClick(item)} ,
                        sharedTransitionScope = sharedTransitionScope,
                        animatedContentScope = animatedContentScope
                    )
                }
            }
            }
        },
        actions = {
            if (loading) {
                TopBarActionsShimmer()
            } else {
            // Search button in top bar
            ButtonIconComposable(
                buttonIcon = ButtonIcon.Vector(Icons.Filled.Search),
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                onClick = { onSearchClick()},
                contentDescription = "Search"
            )
            // Delete all items button
            ButtonIconComposable(
                buttonIcon = ButtonIcon.Vector(Icons.Filled.Delete),
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                onClick = {},

                contentDescription = "Delete All"
            )
        }
        }
    )
}

/**
 * Individual cart item card component.
 * Displays item details with quantity controls and remove button.
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun CartItemCard(
    item: CardItemData,
    onQuantityChange: (Int) -> Unit,
    onRemove: () -> Unit,
    isSelected: Boolean = false,
    onItemClick: () -> Unit = {},
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
) {
    // Wrap in remember to prevent unnecessary recompositions
    val actions = remember(item.id, item.quantity, item.inStock) {
        @Composable {
            // Delete button for removing item from cart
            ButtonIconComposable(
                buttonIcon = ButtonIcon.Vector(Icons.Filled.Delete),
                tint = MaterialTheme.colorScheme.error,
                onClick = onRemove,
                contentDescription = "Delete"
            )

            // Quantity selector (only show if item is in stock)
            if (item.inStock) {
                QuantitySelector(
                    quantity = item.quantity,
                    onQuantityChange = onQuantityChange
                )
            }
        }
    }

    CustomItemCard(
        item = item,
        actions = actions,
        onItemClick = onItemClick,
        sharedTransitionScope = sharedTransitionScope,
        animatedContentScope = animatedContentScope,
    )
}

/**
 * Quantity selector component with increase/decrease buttons.
 * Allows users to adjust the quantity of items in their cart.
 */
@Composable
fun QuantitySelector(
    quantity: Int,
    onQuantityChange: (Int) -> Unit
) {
    // Cache the callbacks to prevent recreation
    val onDecrease = remember(quantity) {
        { if (quantity > 1) onQuantityChange(quantity - 1) }
    }

    val onIncrease = remember(quantity) {
        { onQuantityChange(quantity + 1) }
    }

    Row(
        modifier = Modifier
            .background(
                MaterialTheme.colorScheme.surfaceVariant,
                RoundedCornerShape(MaterialTheme.customSpacing.customNormal)
            )
            .padding(horizontal = MaterialTheme.customSpacing.small, vertical = MaterialTheme.customSpacing.extraSmall),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        // Decrease quantity button (disabled when quantity is 1)
        Box(
            modifier = Modifier
                .size(MaterialTheme.customSpacing.box)
                .clickable(
                    enabled = quantity > 1,
                    onClick = onDecrease
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Remove,
                contentDescription = "Decrease quantity",
                modifier = Modifier.size(MaterialTheme.customSpacing.medium),
                tint = if (quantity > 1) MaterialTheme.colorScheme.onSurfaceVariant
                else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.38f)
            )
        }

        // Current quantity display
        Text(
            text = quantity.toString(),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.widthIn(min = MaterialTheme.customSpacing.large),
            textAlign = TextAlign.Center
        )

        // Increase quantity button
        Box(
            modifier = Modifier
                .size(MaterialTheme.customSpacing.box)
                .clickable(onClick = onIncrease),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Add,
                contentDescription = "Increase quantity",
                modifier = Modifier.size(MaterialTheme.customSpacing.medium),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

/**
 * Empty cart state component.
 * Displayed when the cart has no items.
 */
@Composable
fun EmptyCartState(modifier: Modifier) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(MaterialTheme.customSpacing.baseMedium),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Empty cart icon
        Icon(
            Icons.Default.ShoppingCart,
            contentDescription = null,
            modifier = Modifier.size(MaterialTheme.customSpacing.xxLarge),
            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
        )

        Spacer(modifier = Modifier.height(MaterialTheme.customSpacing.large))

        // Empty cart message
        Text(
            text = "Your cart is empty",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Text(
            text = "Add items to get started",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = MaterialTheme.customSpacing.small)
        )

        Spacer(modifier = Modifier.height(MaterialTheme.customSpacing.baseMedium))

        // Start shopping button
        Button(
            onClick = { /* Navigate to shop */ },
            modifier = Modifier.fillMaxWidth(0.6f)
        ) {
            Icon(Icons.Default.Add, contentDescription = null)
            Spacer(modifier = Modifier.width(MaterialTheme.customSpacing.small))
            Text("Start Shopping")
        }
    }
}

/**
 * Preview function for the cart screen in night mode.
 */
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ProductCardPreviewScreen() {
    val navHost = rememberNavController()
    MyAppTheme(
        dynamicColor = false,
        content = {
//            CartScreen(
//                onBackNavigation = {},
//                navHostController = navHost,
//                onSearchClick = {}
//            )
        }
    )
}