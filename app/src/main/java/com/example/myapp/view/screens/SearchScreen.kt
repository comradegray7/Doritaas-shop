package com.example.myapp.view.screens

import android.content.res.Configuration
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapp.R
import com.example.myapp.ui.theme.LocalWindowSizeConstant
import com.example.myapp.ui.theme.MyAppTheme
import com.example.myapp.ui.theme.colors
import com.example.myapp.ui.theme.customSpacing
import com.example.myapp.view.components.ClickableSearchBarShimmer
import com.example.myapp.view.components.CustomLazyColumn
import com.example.myapp.view.components.CustomScaffoldContainer
import com.example.myapp.view.components.CustomSpacer
import com.example.myapp.view.components.PaddedSection
import com.example.myapp.view.components.ProductShimmer
import com.example.myapp.view.components.ProductShimmerList
import com.example.myapp.view.components.SearchListShimmer
import com.example.myapp.view.components.custom.buttons.ButtonIconComposable
import com.example.myapp.view.screens.bottom_bar.EmptyWishlistState
import com.example.myapp.view.utils.ButtonIcon
import kotlinx.coroutines.delay

/**
 * SearchScreen - Composable for the product search experience.
 *
 * This screen provides a search bar, recent/popular items, and search results.
 * - Shows a shimmer while loading.
 * - Filters a static list of items based on the user's query.
 * - Displays recent searches or popular items when the query is empty.
 * - Shows search results or a "no results" message when the user types.
 * - Handles navigation back and result selection via callbacks.
 *
 * @param onBackNavigation Callback for when the user navigates back.
 * @param onResultClick Callback for when a search result is clicked.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onBackNavigation: () -> Unit,
    onResultClick: (String) -> Unit = {},
) {
    val windowSizeConstant = LocalWindowSizeConstant.current

    // State for the search query
    var query by rememberSaveable { mutableStateOf("") }

    // Static list of items to search/filter
    val items = listOf(
        "Cupcake", "Donut", "Eclair", "Froyo", "Gingerbread", "Honeycomb",
        "Ice Cream Sandwich", "Jelly Bean", "KitKat", "Lollipop", "Marshmallow",
        "Nougat", "Oreo", "Pie", "Wireless Headphones", "Bluetooth Speaker",
        "Phone Case", "Charger", "Screen Protector"
    )

    // Filtered items based on the query
    val filteredItems by remember {
        derivedStateOf {
            if (query.isEmpty()) {
                items
            } else {
                items.filter { it.contains(query, ignoreCase = true) }
            }
        }
    }

    // Loading state for shimmer effect
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
        onNavigateBack = {
            onBackNavigation()
        },
        topBarComposable = {
            if (loading) {
                ClickableSearchBarShimmer()
            } else {
                // Search bar with icons and clear button
                SearchBar(
                    colors = SearchBarDefaults.colors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    ),
                    modifier = windowSizeConstant.adaptiveWidthModifier
                        .height(windowSizeConstant.adaptiveHeight),
                    shape = MaterialTheme.shapes.medium,
                    inputField = {
                        CompositionLocalProvider(LocalTextStyle provides MaterialTheme.typography.bodyLarge) {
                            SearchBarDefaults.InputField(
                                query = query,
                                onQueryChange = { query = it },
                                onSearch = { /* Handle search */ },
                                expanded = false,
                                onExpandedChange = { },
                                placeholder = {
                                    Text(
                                        text = stringResource(R.string.search_products),
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                },
                                leadingIcon = {
                                    Icon(Icons.Default.Search, contentDescription = "Search")
                                },
                                trailingIcon = {
                                    if (query.isNotEmpty()) {
                                        IconButton(onClick = { query = "" }) {
                                            Icon(Icons.Default.Clear, contentDescription = "Clear")
                                        }
                                    } else {
                                        Row(
                                            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.customSpacing.extraSmall),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            ButtonIconComposable(
                                                buttonIcon = ButtonIcon.Vector(Icons.Filled.CameraAlt),
                                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                                onClick = {},
                                                contentDescription = "Camera"
                                            )
                                            VerticalDivider(
                                                color = MaterialTheme.colorScheme.outline,
                                                thickness = MaterialTheme.customSpacing.border,
                                                modifier = Modifier.height(MaterialTheme.customSpacing.large)
                                            )
                                            ButtonIconComposable(
                                                buttonIcon = ButtonIcon.Vector(Icons.Filled.Mic),
                                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                                onClick = {},
                                                contentDescription = "Mic"
                                            )
                                        }
                                    }
                                })
                        }
                    },
                    expanded = false,
                    onExpandedChange = { }
                ) {}
            }
        },
        showTitle = false,
        showBottomBar = false,
        content = {
            CustomSpacer()
            if (loading) {
                SearchListShimmer()
            } else if (query.isEmpty()) {
                // Show recent searches or popular items
                PaddedSection {
                    Text(
                        text = stringResource(R.string.recent_search),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                PaddedSection {
                    CustomLazyColumn {
                        items(5) { index ->
                            ListItem(
                                headlineContent = { Text("Popular item ${index + 1}") },
                                supportingContent = { Text(text = stringResource(R.string.trending_now)) },
                                leadingContent = {
                                    Icon(
                                        Icons.AutoMirrored.Filled.TrendingUp,
                                        contentDescription = null
                                    )
                                },
                                modifier = Modifier
                                    .clip(MaterialTheme.shapes.medium)
                                    .clickable {
                                        onResultClick("Popular item ${index + 1}")
                                    }
                            )
                        }
                    }
                }
            } else {
                // Show search results or "no results" message
                PaddedSection {
                    Text(
                        modifier = Modifier.padding(vertical = MaterialTheme.customSpacing.small),
                        text = stringResource(R.string.search_results) + "(${filteredItems.size})",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                if (filteredItems.isEmpty()) {
                    PaddedSection { CustomLazyColumn {
                        item {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(MaterialTheme.customSpacing.medium),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    imageVector = Icons.Default.SearchOff,
                                    contentDescription = null,
                                    modifier = Modifier.size(MaterialTheme.customSpacing.customMedium),
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Spacer(modifier = Modifier.height(MaterialTheme.customSpacing.medium))
                                Text(
                                    text = "No results found",
                                    style = MaterialTheme.typography.headlineSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = "Try adjusting your search terms",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }}
                } else {
                    PaddedSection {
                        CustomLazyColumn {
                            items(filteredItems.size) { index ->
                                val item = filteredItems[index]
                                ListItem(
                                    headlineContent = { Text(item) },
                                    supportingContent = { Text("Product description for $item") },
                                    leadingContent = {
                                        Icon(Icons.Default.Star, contentDescription = null)
                                    },
                                    trailingContent = {
                                        Icon(Icons.Default.ChevronRight, contentDescription = null)
                                    },
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(MaterialTheme.customSpacing.customNormal))
                                        .clickable {
                                            onResultClick(item)
                                        }
                                )
                            }
                        }
                    }
                }
            }
        },
    )
}

/**
 * ClickableSearchBar - Simple search bar with icons for use in shimmer/loading state.
 *
 * @param onClick Callback when the search bar is clicked.
 * @param modifier Optional modifier for styling.
 */
@Composable
fun ClickableSearchBar(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val windowSizeConstant = LocalWindowSizeConstant.current

    Box(
        modifier = windowSizeConstant.adaptiveWidthModifier
            .clip(MaterialTheme.shapes.medium)
            .height(windowSizeConstant.adaptiveHeight)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable { onClick() }
            .padding(horizontal = MaterialTheme.customSpacing.normal),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            // Left side - Search icon and text
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.width(MaterialTheme.customSpacing.small))
                Text(
                    text = stringResource(R.string.search_products),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Right side - Camera and Mic icons
            Row(
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.customSpacing.extraSmall),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ButtonIconComposable(
                    buttonIcon = ButtonIcon.Vector(Icons.Filled.CameraAlt),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    onClick = {
                        // Prevent event bubbling and handle camera click
                        onClick()
                    },
                    contentDescription = "Camera"
                )

                VerticalDivider(
                    color = MaterialTheme.colorScheme.outline,
                    thickness = MaterialTheme.customSpacing.border,
                    modifier = Modifier.height(MaterialTheme.customSpacing.large)
                )

                ButtonIconComposable(
                    buttonIcon = ButtonIcon.Vector(Icons.Filled.Mic),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    onClick = {
                        // Prevent event bubbling and handle mic click
                        onClick()
                    },
                    contentDescription = "Mic"
                )
            }
        }
    }
}

/**
 * SearchPreviewScreen - Preview for SearchScreen in dark mode.
 */
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SearchPreviewScreen() {
    MyAppTheme(
        dynamicColor = false,
        content = {
            SearchScreen(
                onBackNavigation = {},
                onResultClick = {},
            )
        }
    )
}