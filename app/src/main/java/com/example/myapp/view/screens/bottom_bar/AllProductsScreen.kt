package com.example.myapp.view.screens.bottom_bar

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowCircleRight
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberDrawerState
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myapp.R
import com.example.myapp.ui.theme.MyAppTheme
import com.example.myapp.ui.theme.customSpacing
import com.example.myapp.view.components.ButtonIconShimmer
import com.example.myapp.view.components.CategoryShimmerPlaceholder
import com.example.myapp.view.components.ClickableSearchBarShimmer
import com.example.myapp.view.components.CustomLazyColumn
import com.example.myapp.view.components.CustomLazyRow
import com.example.myapp.view.components.CustomScaffoldContainer
import com.example.myapp.view.components.CustomSpacer
import com.example.myapp.view.components.HeadlineWidget
import com.example.myapp.view.components.PaddedSection
import com.example.myapp.view.components.ProductCarouselShimmer
import com.example.myapp.view.components.ProductShimmer
import com.example.myapp.view.components.TopBarActionsShimmer
import com.example.myapp.view.components.custom.buttons.ButtonIconComposable
import com.example.myapp.view.screens.product.ProductCard
import com.example.myapp.view.screens.product.ProductCardShimmerRow
import com.example.myapp.view.screens.product.ProductCarousel
import com.example.myapp.view.screens.product.ProductItem
import com.example.myapp.view.screens.product.categories.Categories
import com.example.myapp.view.utils.ButtonIcon
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * AllProductsScreen - Displays all products, categories, and featured products.
 *
 * This composable shows a list of categories, a carousel, and a horizontally scrollable
 * row of featured products. It also handles loading and refresh states.
 *
 * @param onSearchClick Callback for when the search icon is clicked
 * @param onCartClick Callback for when the cart icon is clicked
 * @param onCategoryClick Callback for when a category is clicked
 * @param onProductClick Callback for when a product is clicked
 * @param onAllProductsClick Callback for when "all products" is clicked
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllProductsScreen(
    onSearchClick: () -> Unit,
    onCartClick: () -> Unit,
    onCategoryClick: () -> Unit = {},
    onProductClick: () -> Unit = {},
    onAllProductsClick: () -> Unit
) {
    // Example product items
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

    var loading by remember { mutableStateOf(true) }

    // Simulate loading delay
    LaunchedEffect(Unit) {
        delay(1500)
        loading = false
    }

    CustomScaffoldContainer(
        verticalArrangement = Arrangement.Top,
        onRefresh = {
            // Add your refresh logic here
            println("Refreshing login screen...")
        },
        showBackArrow = false,
        showBottomBar = false,
        title = R.string.all_products_title,
        content = {
            if (loading) {
                CategoryShimmerPlaceholder()
                ProductShimmer()
            } else {
                Categories(
                    onCategoryClick = { categoryName ->
                        onCategoryClick()
                    }
                )
                CustomLazyColumn {
                    item {
                        ProductCarousel()
                    }
                    item {
                        HeadlineWidget(
                            leading = R.string.featured_products,
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
                            ) { item ->
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
                            leading = R.string.featured_products,
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
                            ) { item ->
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
                            leading = R.string.featured_products,
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
                            ) { item ->
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
                TopBarActionsShimmer()
            } else {
                ButtonIconComposable(
                    buttonIcon = ButtonIcon.Vector(Icons.Filled.Search),
                    onClick = {},
                    contentDescription = "Search"
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
 * AllProductsPreview - Preview for AllProductsScreen in dark mode.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AllProductsPreview() {
    MyAppTheme(
        dynamicColor = false,
        content = {
            AllProductsScreen(
                onSearchClick = {},
                onCartClick = {},
                onAllProductsClick = {}
            )
        }
    )
}
