package com.example.myapp.view.screens.bottom_bar

import android.content.res.Configuration
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowCircleRight
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapp.R
import com.example.myapp.ui.theme.MyAppTheme
import com.example.myapp.ui.theme.customSpacing
import com.example.myapp.view.components.ButtonIconShimmer
import com.example.myapp.view.components.ClickableSearchBarShimmer
import com.example.myapp.view.components.CustomLazyColumn
import com.example.myapp.view.components.CustomLazyRow
import com.example.myapp.view.components.CustomScaffoldContainer
import com.example.myapp.view.components.CustomSpacer
import com.example.myapp.view.components.HeadlineWidget
import com.example.myapp.view.components.PaddedSection
import com.example.myapp.view.components.ProductCardShimmer
import com.example.myapp.view.components.ProductCarouselShimmer
import com.example.myapp.view.components.ProductShimmer
import com.example.myapp.view.components.TopBarActionsShimmer
import com.example.myapp.view.components.custom.buttons.ButtonIconComposable
import com.example.myapp.view.screens.ClickableSearchBar
import com.example.myapp.view.screens.product.ProductCard
import com.example.myapp.view.screens.product.ProductCardShimmerRow
import com.example.myapp.view.screens.product.ProductCarousel
import com.example.myapp.view.screens.product.ProductItem
import com.example.myapp.view.utils.ButtonIcon
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * ShopScreen - Main shop screen displaying featured products and categories.
 *
 * This composable shows a search bar, product carousel, and multiple sections
 * of featured products in horizontally scrollable rows. It handles loading states
 * and provides navigation to search, cart, and product details.
 *
 * @param onSearchClick Callback for when the search icon is clicked
 * @param onProductClick Callback for when a product is clicked
 * @param onCartClick Callback for when the cart icon is clicked
 * @param onAllProductsClick Callback for when "all products" is clicked
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopScreen(
    onSearchClick: () -> Unit,
    onProductClick: () -> Unit,
    onCartClick: () -> Unit,
    onAllProductsClick: () -> Unit
) {

    // Sample product items for demonstration
    val items = listOf(
        ProductItem(1, "Sneakers", "$59.99", R.drawable.domino, 2.8f, "$79.99"),
        ProductItem(2, "Sneakers", "$59.99", R.drawable.the_dk, 2.8f, "$79.99"),
        ProductItem(3, "Sneakers", "$59.99", R.drawable.elias, 2.8f, "$79.99"),
        ProductItem(4, "Sneakers", "$59.99", R.drawable.paul_gaudriault, 2.8f, "$79.99"),
        ProductItem(5, "Sneakers", "$59.99", R.drawable.irene_kredenets, 2.8f, "$79.99"),
        ProductItem(6, "Sneakers", "$59.99", R.drawable.imani, 2.8f, "$79.99"),
        ProductItem(7, "Red Shirt", "$19.99", R.drawable.turturro, 4.5f, "$29.99"),
        ProductItem(8, "Red Shirt", "$19.99", R.drawable.dom_hill, 4.5f, "$29.99"),
        ProductItem(9, "Red Shirt", "$19.99", R.drawable.nicoll, 4.5f, "$29.99"),
        ProductItem(10, "Red Shirt", "$19.99", R.drawable.gudakov, 4.5f, "$29.99"),
    )

    // Loading state for initial data fetch
    var loading by remember { mutableStateOf(true) }

    // Simulate loading delay for better UX
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
        showBottomBar = false, // Hide bottom bar on this screen
        showBackArrow = false, // No back arrow needed for main shop screen
        title = R.string.logo, // Display app logo as title
        content = {
            if (loading) {
                // Show shimmer loading states during initial load
                PaddedSection {
                    ClickableSearchBarShimmer() // Shimmer for search bar
                }
                    ProductShimmer() // Shimmer for product content
        } else {
                // Show actual content after loading
                PaddedSection {
                    ClickableSearchBar(
                        onClick = { onSearchClick() } // Handle search bar click
                    )
                }
            CustomLazyColumn {
                item {
                    ProductCarousel() // Main product carousel at the top
                }
                item {
                        HeadlineWidget(
                            leading = R.string.featured_products, // Section title
                            trailing = {
                                ButtonIconComposable(
                                    buttonIcon = ButtonIcon.Vector(Icons.Filled.ArrowCircleRight),
                                    onClick = {},
                                    contentDescription = "more options"
                                )
                            }
                        )
                        CustomSpacer(modifier = Modifier.height(MaterialTheme.customSpacing.smaller))
                        // Horizontally scrollable row of featured products
                    CustomLazyRow {
                        items(
                            count = items.size
                        ) {
                            item ->
                            val product = items[item]
                            var isFavorite by remember { mutableStateOf(product.isFavorite) }

                            ProductCard(
                                id = product.id,
                                productName = product.productName,
                                price = product.price,
                                onProductClick = { onProductClick() },
                                imageRes = product.imageRes,
                                isFavorite = isFavorite,
                                onFavoriteClick = { isFavorite = !isFavorite },
                            )
                        }
                    }
                 }
                item {
                        HeadlineWidget(
                            leading = R.string.featured_products, // Second featured section
                            trailing = {
                                ButtonIconComposable(
                                    buttonIcon = ButtonIcon.Vector(Icons.Filled.ArrowCircleRight),
                                    onClick = {},
                                    contentDescription = "more options"
                                )
                            }
                        )
                        CustomSpacer(modifier = Modifier.height(MaterialTheme.customSpacing.smaller))
                        // Horizontally scrollable row of products
                    CustomLazyRow {
                        items(
                            count = items.size
                        ) {
                            item ->
                            val product = items[item]
                            var isFavorite by remember { mutableStateOf(product.isFavorite) }
                            ProductCard(
                                id = product.id,
                                productName = product.productName,
                                price = product.price,
                                onProductClick = { onProductClick() },
                                imageRes = product.imageRes,
                                isFavorite = isFavorite,
                                onFavoriteClick = { isFavorite = !isFavorite },
                            )
                        }
                    }
                 }
                item {
                        HeadlineWidget(
                            leading = R.string.featured_products, // Third featured section
                            trailing = {
                                ButtonIconComposable(
                                    buttonIcon = ButtonIcon.Vector(Icons.Filled.ArrowCircleRight),
                                    onClick = {},
                                    contentDescription = "more options"
                                )
                            }
                        )
                        CustomSpacer(modifier = Modifier.height(MaterialTheme.customSpacing.smaller))
                        // Horizontally scrollable row of products
                    CustomLazyRow {
                        items(
                            count = items.size
                        ) {
                            item ->
                            val product = items[item]
                            var isFavorite by remember { mutableStateOf(product.isFavorite) }
                            ProductCard(
                                id = product.id,
                                productName = product.productName,
                                price = product.price,
                                onProductClick = { onProductClick() },
                                imageRes = product.imageRes,
                                isFavorite = isFavorite,
                                onFavoriteClick = { isFavorite = !isFavorite },
                            )
                        }
                    }
                 }
                item {
                        HeadlineWidget(
                            leading = R.string.featured_products, // Fourth featured section
                            trailing = {
                                ButtonIconComposable(
                                    buttonIcon = ButtonIcon.Vector(Icons.Filled.ArrowCircleRight),
                                    onClick = {},
                                    contentDescription = "more options"
                                )
                            }
                        )
                        CustomSpacer(modifier = Modifier.height(MaterialTheme.customSpacing.smaller))
                        // Horizontally scrollable row of products
                    CustomLazyRow {
                        items(
                            count = items.size
                        ) {
                            item ->
                            val product = items[item]
                            var isFavorite by remember { mutableStateOf(product.isFavorite) }
                            ProductCard(
                                id = product.id,
                                productName = product.productName,
                                price = product.price,
                                onProductClick = { onProductClick() },
                                imageRes = product.imageRes,
                                isFavorite = isFavorite,
                                onFavoriteClick = { isFavorite = !isFavorite },
                            )
                        }
                    }
                 }
                }
            }
      
        },
        actions = {
            if (loading) {
                TopBarActionsShimmer() // Show shimmer for top bar actions while loading
            } else {
                // Notification and cart action buttons
                ButtonIconComposable(
                    buttonIcon = ButtonIcon.Vector(Icons.Filled.Notifications),
                    onClick = {},
                    contentDescription = "notifications"
                )
                ButtonIconComposable(
                    buttonIcon = ButtonIcon.Vector(Icons.Filled.ShoppingCart),
                    onClick = {
                        onCartClick()
                    },
                    contentDescription = "shopping cart"
                )
            }
        }
    )
}

/**
 * ShopScreenPreview - Preview for ShopScreen in dark mode.
 */
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopScreenPreview() {
    MyAppTheme(
        dynamicColor = false,
        content = {
            ShopScreen(
                onCartClick = {},
                onSearchClick = {},
                onProductClick = {},
                onAllProductsClick = {}
            )
        }
    )
}
