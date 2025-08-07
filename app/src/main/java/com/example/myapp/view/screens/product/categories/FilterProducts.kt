package com.example.myapp.view.screens.product.categories

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ViewList
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.SearchOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.myapp.R
import com.example.myapp.ui.theme.LocalWindowSizeConstant
import com.example.myapp.ui.theme.customSpacing
import com.example.myapp.view.components.ButtonIconShimmer
import com.example.myapp.view.components.CardItemData
import com.example.myapp.view.components.CustomItemCard
import com.example.myapp.view.components.CustomLazyColumn
import com.example.myapp.view.components.CustomScaffoldContainer
import com.example.myapp.view.components.CustomSpacer
import com.example.myapp.view.components.HeadlineWidget
import com.example.myapp.view.components.PaddedSection
import com.example.myapp.view.components.ProductShimmerList
import com.example.myapp.view.components.custom.buttons.ButtonIconComposable
import com.example.myapp.view.components.custom.buttons.CustomFilledTonalButton
import com.example.myapp.view.components.custom.buttons.CustomOutlinedButton
import com.example.myapp.view.screens.product.ProductCard
import com.example.myapp.view.utils.ButtonIcon
import kotlinx.coroutines.delay

enum class DisplayMode {
    ROW, GRID
}

// Data classes for filter state
data class FilterState(
    val selectedCategory: String = "All",
    val selectedPriceRange: String = "All",
    val selectedRating: Float = 0f,
    val sortBy: String = "Name",
    val showFilters: Boolean = false
)

data class FilterActions(
    val onCategoryChange: (String) -> Unit,
    val onPriceRangeChange: (String) -> Unit,
    val onRatingChange: (Float) -> Unit,
    val onSortByChange: (String) -> Unit,
    val onToggleFilters: () -> Unit,
    val onClearFilters: () -> Unit
)

data class FilterData(
    val categories: List<String> = listOf("All", "Electronics", "Clothing", "Books", "Home", "Sports"),
    val priceRanges: List<String> = listOf("All", "$0-$50", "$51-$100", "$101-$200", "$200+"),
    val sortOptions: List<String> = listOf("Name", "Price Low to High", "Price High to Low", "Rating")
)

// Empty State Composable
@Composable
fun EmptyFilterResults(
    onClearFilters: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(MaterialTheme.customSpacing.baseMedium),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Outlined.SearchOff,
            contentDescription = stringResource(R.string.no_results),
            modifier = Modifier.size(MaterialTheme.customSpacing.mediumLarge),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(MaterialTheme.customSpacing.small))

        Text(
            text = stringResource(R.string.no_results),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(MaterialTheme.customSpacing.small))

        Text(
            text =  stringResource(R.string.adjust_search),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(MaterialTheme.customSpacing.large))

        CustomOutlinedButton(
            icon = ButtonIcon.Vector(Icons.Outlined.Clear),
            onClick = onClearFilters,
            label = R.string.clear_filter
        )

    }
}

// Stateless Filter Section Composable
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterSection(
    modifier: Modifier = Modifier,
    filterState: FilterState,
    filterActions: FilterActions,
    filterData: FilterData = FilterData(),
) {
    if (filterState.showFilters) {
        CustomSpacer()
        Column(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(MaterialTheme.customSpacing.customNormal))
                .background(MaterialTheme.colorScheme.surfaceContainer),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.customSpacing.medium)
        ) {
            // Category Filter
            PaddedSection(content = {
                Text(
                    text = "Category",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(MaterialTheme.customSpacing.small))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.customSpacing.small)
                ) {
                    items(filterData.categories) { category ->
                        FilterChip(
                            onClick = { filterActions.onCategoryChange(category) },
                            label = { Text(category) },
                            selected = filterState.selectedCategory == category
                        )
                    }
                }
            })

            // Price Range Filter
            PaddedSection(content = {
                Text(
                    text = "Price Range",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(MaterialTheme.customSpacing.small))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.customSpacing.small)
                ) {
                    items(filterData.priceRanges) { range ->
                        FilterChip(
                            onClick = { filterActions.onPriceRangeChange(range) },
                            label = { Text(range) },
                            selected = filterState.selectedPriceRange == range
                        )
                    }
                }
            })

            // Rating Filter
            PaddedSection(content = {
                Text(
                    text = "Minimum Rating: ${filterState.selectedRating.toInt()}+ stars",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(MaterialTheme.customSpacing.small))
                Slider(
                    value = filterState.selectedRating,
                    onValueChange = filterActions.onRatingChange,
                    valueRange = 0f..5f,
                    steps = 4
                )
            })

            // Sort Options
            PaddedSection(content = {
                Text(
                    text = "Sort By",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(MaterialTheme.customSpacing.small))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.customSpacing.small)
                ) {
                    items(filterData.sortOptions) { option ->
                        FilterChip(
                            onClick = { filterActions.onSortByChange(option) },
                            label = { Text(option) },
                            selected = filterState.sortBy == option
                        )
                    }
                }
            })
        }
    }
}

// Updated FilterProducts composable using the stateless filter
@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun FilterProducts(
    onBackNavigation: () -> Unit = {},
    onSearchClick: () -> Unit = {},
    onCartClick: () -> Unit = {},
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
) {
    val windowSizeConstant = LocalWindowSizeConstant.current

    var loading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(1500) // simulate initial loading delay
        loading = false
    }

    var displayMode by remember { mutableStateOf(DisplayMode.GRID) }

    // Consolidated filter state
    var filterState by remember {
        mutableStateOf(FilterState())
    }

    // Create filter actions
    val filterActions = remember {
        FilterActions(
            onCategoryChange = { category ->
                filterState = filterState.copy(selectedCategory = category)
            },
            onPriceRangeChange = { priceRange ->
                filterState = filterState.copy(selectedPriceRange = priceRange)
            },
            onRatingChange = { rating ->
                filterState = filterState.copy(selectedRating = rating)
            },
            onSortByChange = { sortBy ->
                filterState = filterState.copy(sortBy = sortBy)
            },
            onToggleFilters = {
                filterState = filterState.copy(showFilters = !filterState.showFilters)
            },
            onClearFilters = {
                filterState = FilterState() // Reset to default state
            }
        )
    }

    // Sample data (keeping the same as original)
    val allItems = remember {
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
    }

    // Filter and sort logic using the consolidated state
    val filteredItems = remember(filterState) {
        var filtered = allItems

        // Category filter
        if (filterState.selectedCategory != "All") {
            filtered = filtered.filter { it.category == filterState.selectedCategory }
        }

        // Price range filter
        if (filterState.selectedPriceRange != "All") {
            filtered = when (filterState.selectedPriceRange) {
                "$0-$50" -> filtered.filter { it.price <= 50 }
                "$51-$100" -> filtered.filter { it.price in 51.0..100.0 }
                "$101-$200" -> filtered.filter { it.price in 101.0..200.0 }
                "$200+" -> filtered.filter { it.price > 200 }
                else -> filtered
            }
        }

        // Rating filter
        if (filterState.selectedRating > 0) {
            filtered = filtered.filter { it.rating!! >= filterState.selectedRating }
        }

        // Sorting
        when (filterState.sortBy) {
            "Name" -> filtered.sortedBy { it.name }
            "Price Low to High" -> filtered.sortedBy { it.price }
            "Price High to Low" -> filtered.sortedByDescending { it.price }
            "Rating" -> filtered.sortedByDescending { it.rating }
            else -> filtered
        }
    }

    CustomScaffoldContainer(
        onRefresh = {
            println("Refreshing login screen...")
        },
        showBottomBar = false,
        onNavigateBack = {
            onBackNavigation()
        },
        title = R.string.ai_title,
        actions = {
            if (loading) {
                ButtonIconShimmer()
                CustomSpacer(modifier = Modifier.width(MaterialTheme.customSpacing.small))
                ButtonIconShimmer()
            } else {
                ButtonIconComposable(
                    buttonIcon = ButtonIcon.Vector(Icons.Filled.Search),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    onClick = { onSearchClick() },
                    contentDescription = "Search"
                )
                ButtonIconComposable(
                    buttonIcon = ButtonIcon.Vector(Icons.Filled.ShoppingCart),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    onClick = { onCartClick() },
                    contentDescription = "Shopping Cart"
                )
            }
        },
        content = {
            if (loading) {
                ProductShimmerList()
            } else {
                HeadlineWidget(
                    leadingStr = "Products (${filteredItems.size})",
                    trailing = {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.customSpacing.small),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Filter toggle
                            IconButton(
                                onClick = { filterActions.onToggleFilters() }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.FilterList,
                                    contentDescription = "Toggle Filters",
                                    tint = if (filterState.showFilters) MaterialTheme.colorScheme.primary
                                    else MaterialTheme.colorScheme.onSurface
                                )
                            }

                            // Display mode toggle
                            Row(
                                modifier = Modifier
                                    .background(
                                        MaterialTheme.colorScheme.surfaceVariant,
                                        RoundedCornerShape(MaterialTheme.customSpacing.medium)
                                    )
                                    .padding(MaterialTheme.customSpacing.extraSmall)
                            ) {
                                IconButton(
                                    onClick = { displayMode = DisplayMode.ROW },
                                    modifier = Modifier
                                        .size(MaterialTheme.customSpacing.boxMedium)
                                        .background(
                                            if (displayMode == DisplayMode.ROW)
                                                MaterialTheme.colorScheme.primary
                                            else Color.Transparent,
                                            RoundedCornerShape(16.dp)
                                        )
                                ) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ViewList,
                                        contentDescription = "Row View",
                                        tint = if (displayMode == DisplayMode.ROW)
                                            MaterialTheme.colorScheme.onPrimary
                                        else MaterialTheme.colorScheme.onSurface
                                    )
                                }

                                IconButton(
                                    onClick = { displayMode = DisplayMode.GRID },
                                    modifier = Modifier
                                        .size(MaterialTheme.customSpacing.boxMedium)
                                        .background(
                                            if (displayMode == DisplayMode.GRID)
                                                MaterialTheme.colorScheme.primary
                                            else Color.Transparent,
                                            RoundedCornerShape(16.dp)
                                        )
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.GridView,
                                        contentDescription = "Grid View",
                                        tint = if (displayMode == DisplayMode.GRID)
                                            MaterialTheme.colorScheme.onPrimary
                                        else MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }
                    }
                )

                // Use the stateless FilterSection
                FilterSection(
                    filterState = filterState,
                    filterActions = filterActions
                )

                // Content Area
                when {
                    filteredItems.isEmpty() -> {
                        // Show empty state when no results found
                        EmptyFilterResults(
                            onClearFilters = filterActions.onClearFilters
                        )
                    }
                    displayMode == DisplayMode.ROW -> {
                        CustomLazyColumn {
                            items(filteredItems) { item ->
                                RowItemCard(
                                    item = item,
                                    onItemClick = {},
                                    sharedTransitionScope = sharedTransitionScope,
                                    animatedContentScope = animatedContentScope
                                )
                            }
                        }
                    }
                    displayMode == DisplayMode.GRID -> {
                        CustomSpacer()
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(windowSizeConstant.gridCols),
                            modifier = windowSizeConstant.adaptiveWidthModifier,
                            contentPadding = PaddingValues(horizontal = windowSizeConstant.contentPadding),
                            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.customSpacing.normal),
                            verticalArrangement = Arrangement.spacedBy(MaterialTheme.customSpacing.normal)
                        ) {
                            items(filteredItems) { item ->
                                GridItemCard(item = item)
                            }
                        }
                    }
                }
            }
        },
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun RowItemCard(
    modifier: Modifier = Modifier,
    item: CardItemData,
    onItemClick: () -> Unit = {},
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    onAddToCart: () -> Unit = { }

) {
    CustomItemCard(
        modifier,
        onItemClick = onItemClick,
        item = item,
        sharedTransitionScope = sharedTransitionScope,
        animatedContentScope = animatedContentScope,
        actions = {
            CustomFilledTonalButton(
                onClick = { onAddToCart() },
                icon = Icons.Filled.FavoriteBorder,
            )
        },
    )
}

@Composable
fun GridItemCard(item: CardItemData) {
    ProductCard(
        productName = item.name,
        price = "${item.price}",
        onProductClick = {},
        imageRes = item.imageRes,
        isFavorite = false,
        onFavoriteClick = {},
        rating = item.rating
    )
}