package com.example.myapp.view.screens.product

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DeliveryDining
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.myapp.R
import com.example.myapp.ui.theme.LocalWindowSizeConstant
import com.example.myapp.ui.theme.MyAppTheme
import com.example.myapp.ui.theme.colors
import com.example.myapp.ui.theme.customSpacing
import com.example.myapp.view.components.CustomBottomSection
import com.example.myapp.view.components.CustomImageContainer
import com.example.myapp.view.components.CustomLazyColumn
import com.example.myapp.view.components.CustomLazyRow
import com.example.myapp.view.components.CustomScaffoldContainer
import com.example.myapp.view.components.CustomSpacer
import com.example.myapp.view.components.HeadlineWidget
import com.example.myapp.view.components.NavigationBarShimmer
import com.example.myapp.view.components.PaddedSection
import com.example.myapp.view.components.ProductDescriptionShimmer
import com.example.myapp.view.components.custom.buttons.ButtonIconComposable
import com.example.myapp.view.components.custom.buttons.CustomOutlinedButton
import com.example.myapp.view.utils.ButtonIcon
import kotlinx.coroutines.delay

/**
 * ProductDescriptionScreen - Detailed product view with images, specifications, and purchase options.
 *
 * This composable displays comprehensive product information including:
 * - Product images with selection capability
 * - Color and size selection options
 * - Product specifications and details
 * - Shipping options
 * - Quantity selection
 * - Purchase actions
 *
 * @param onBackNavigation Callback for back navigation
 * @param onBuyNowClick Callback for buy now action
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDescriptionScreen(
    onBackNavigation: () -> Unit,
    onBuyNowClick: () -> Unit,
) {

    val windowSizeConstant = LocalWindowSizeConstant.current

    // State management for product selection and display
    var selectedImageIndex by remember { mutableIntStateOf(0) }
    var selectedColor by remember { mutableStateOf("Black") }
    var selectedSize by remember { mutableStateOf("M") }
    var quantity by remember { mutableIntStateOf(1) }
    var isExpanded by remember { mutableStateOf(false) }
    var selectedShipping by remember { mutableStateOf("Standard") }

    // Product specifications data
    val allSpecs = listOf(
        "Material" to "100% Premium Cotton",
        "Weight" to "180 GSM",
        "Care" to "Machine wash cold",
        "Origin" to "Made in USA",
        "Fit" to "Regular fit",
        "Neck" to "Crew neck",
        "Sleeves" to "Short sleeves",
        "Stretch" to "Slight stretch",
    )

    // Show limited specs initially, expand on user interaction
    val specsToShow = if (isExpanded) allSpecs else allSpecs.take(3)

    // Product image gallery data
    val productImages = listOf(
        SmallImageItem(1, R.drawable.thrilled, "one"),
        SmallImageItem(2, R.drawable.thrilled, "one"),
        SmallImageItem(3, R.drawable.thrilled, "one"),
        SmallImageItem(4, R.drawable.thrilled, "one"),
        SmallImageItem(5, R.drawable.thrilled, "one"),
        SmallImageItem(6, R.drawable.thrilled, "one"),
    )

    // Available product options
    val colors = listOf("Black", "White", "Blue", "Red")
    val sizes = listOf("S", "M", "L", "XL")

    // Shipping options with delivery times and costs
    val shippingOptions = listOf(
        ShippingOption("Standard", "5-7 days", "$5.99"),
        ShippingOption("Express", "2-3 days", "$12.99"),
        ShippingOption("Overnight", "Next day", "$24.99")
    )

    // Loading state management
    var loading by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()

    // Simulate initial loading delay
    LaunchedEffect(Unit) {
        delay(1500) // simulate initial loading delay
        loading = false
    }

    // Main screen layout with scaffold container
    CustomScaffoldContainer(
        onRefresh = {
            //refresh logic
        },
        onNavigateBack = {
            onBackNavigation()
        },
        title = R.string.product_details_title,
        bottomBarContent = {
            if (loading) {
                    NavigationBarShimmer()
            } else {
                PaddedSection {
                    CustomBottomSection(
                        total = 400.99, onClick = { onBuyNowClick() },
                        actionLabel = R.string.buy_now,
                    )
                }
            }
        },
        content = {
            if (loading) {
                    ProductDescriptionShimmer()
            } else {
                CustomLazyColumn {
                    item {
                        PaddedSection {
                            // Main product image card with discount badge
                            Card(
                                modifier = Modifier
                                    .size(windowSizeConstant.productImageSize),
                                elevation = CardDefaults.cardElevation(defaultElevation = MaterialTheme.customSpacing.extraSmall),
                                shape = MaterialTheme.shapes.medium
                            ) {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    // Product image container with background
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(MaterialTheme.colorScheme.outlineVariant),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        CustomImageContainer(
                                            data = productImages[selectedImageIndex].imageResId,
                                            shape = MaterialTheme.shapes.large
                                        )
                                    }

                                    // Discount Badge - shows savings percentage
                                    Box(
                                        modifier = Modifier
                                            .align(Alignment.TopStart)
                                            .background(
                                                MaterialTheme.colorScheme.tertiaryContainer,
                                                MaterialTheme.shapes.medium
                                            )
                                            .size(windowSizeConstant.badgePadding),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "-25%",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onTertiaryContainer,
                                        )
                                    }
                                }
                            }
                        }
                        CustomSpacer()
                        // Small image gallery for product image selection
                        SmallProductImages(
                            productImages = productImages,
                            selectedIndex = selectedImageIndex,
                            onImageSelected = { selectedImageIndex = it }
                        )
                    }
                    item {
                        // Product information section
                        PaddedSection {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalAlignment = windowSizeConstant.horizontalAlignment,
                                verticalArrangement = Arrangement.spacedBy(MaterialTheme.customSpacing.small)
                            )
                            {
                                // Product Title - main product name
                                Text(
                                    text = "Premium Cotton T-Shirt with Modern Design",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold
                                )

                                // Brand information
                                Text(
                                    text = "Brand: StyleCraft",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )

                                // Rating and Reviews
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    repeat(5) { index ->
                                        Icon(
                                            imageVector = if (index < 4) Icons.Default.Star else Icons.Default.StarBorder,
                                            contentDescription = null,
                                            tint = Color(0xFFFFD700),
                                            modifier = Modifier.size(MaterialTheme.customSpacing.medium)
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(MaterialTheme.customSpacing.small))
                                    Text(
                                        text = "4.8 (2,847 reviews)",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Spacer(modifier = Modifier.width(MaterialTheme.customSpacing.small))
                                    Text(
                                        text = "• 5,234 sold",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                                // Price
                                Row(
                                    verticalAlignment = Alignment.Bottom
                                ) {
                                    Text(
                                        text = "$29.99",
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    Spacer(modifier = Modifier.width(MaterialTheme.customSpacing.small))
                                    Text(
                                        text = "$39.99",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        textDecoration = TextDecoration.LineThrough
                                    )
                                    Spacer(modifier = Modifier.width(MaterialTheme.customSpacing.small))
                                    Text(
                                        text = "Save $10.00",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colors.green,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }
                    }
                    item {
                        // Color Selection
                        PaddedSection {
                            Text(
                                text = "Color: $selectedColor",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium
                            )
                        }
                        CustomSpacer(modifier = Modifier.height(MaterialTheme.customSpacing.small))
                        CustomLazyRow {
                            items(colors) { color ->
                                val colorValue = when (color) {
                                    "Black" -> MaterialTheme.colors.black
                                    "White" -> MaterialTheme.colors.white
                                    "Blue" -> MaterialTheme.colors.blue
                                    "Red" -> MaterialTheme.colorScheme.error
                                    else -> MaterialTheme.colorScheme.onSurface
                                }

                                Box(
                                    modifier = Modifier
                                        .size(MaterialTheme.customSpacing.boxMedium)
                                        .clip(CircleShape)
                                        .background(colorValue)
                                        .border(
                                            MaterialTheme.customSpacing.baseSpacer,
                                            if (selectedColor == color) MaterialTheme.colorScheme.primary
                                            else Color.Gray.copy(alpha = 0.3f),
                                            CircleShape
                                        )
                                        .clickable { selectedColor = color }
                                )
                            }
                        }
                    }
                    item {
                        // Size Selection
                        PaddedSection {
                            Text(
                                text = "Size: $selectedSize",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium
                            )
                        }
                        CustomSpacer(modifier = Modifier.height(MaterialTheme.customSpacing.small))
                        CustomLazyRow {
                            items(sizes) { size ->
                                OutlinedButton(
                                    onClick = { selectedSize = size },
                                    colors = ButtonDefaults.outlinedButtonColors(
                                        containerColor = if (selectedSize == size)
                                            MaterialTheme.colorScheme.primary else Color.Transparent,
                                        contentColor = if (selectedSize == size)
                                            Color.White else MaterialTheme.colorScheme.primary
                                    ),
                                    modifier = Modifier.width(MaterialTheme.customSpacing.mediumLarge)
                                ) {
                                    Text(text = size)
                                }
                            }
                        }
                    }
                    item {
                        // Shipping Options
                        // headline  widget
                        HeadlineWidget(
                            leading = R.string.shipping,
                            trailing = {
                                Icon(
                                    Icons.Filled.DeliveryDining,
                                    contentDescription = null
                                )
                            }
                        )
                        CustomSpacer(modifier = Modifier.height(MaterialTheme.customSpacing.small))
                        PaddedSection {
                            shippingOptions.forEach { option ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { selectedShipping = option.name },
                                    colors = CardDefaults.cardColors(
                                        containerColor = if (selectedShipping == option.name)
                                            MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                                        else Color.Gray.copy(alpha = 0.05f)
                                    ),
                                    border = if (selectedShipping == option.name)
                                        BorderStroke(
                                            MaterialTheme.customSpacing.border,
                                            MaterialTheme.colorScheme.primary
                                        )
                                    else null
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(MaterialTheme.customSpacing.medium),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column {
                                            Text(
                                                text = option.name,
                                                fontWeight = FontWeight.Medium,
                                                style = windowSizeConstant.appSubHeadLineTextStyle
                                            )
                                            Text(
                                                text = option.delivery,
                                                style = windowSizeConstant.labelTextStyle,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                        Text(
                                            text = option.price,
                                            style = windowSizeConstant.bodyTextStyle,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(MaterialTheme.customSpacing.normal))
                            }
                        }
                    }
                    item {
                        // Product Specifications
                        PaddedSection {
                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(
                                        alpha = 0.2f
                                    )
                                )
                            ) {
                                Column(
                                    modifier = Modifier.padding(MaterialTheme.customSpacing.medium)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "Product Specifications",
                                            style = windowSizeConstant.appHeadLineTextStyle,
                                            fontWeight = FontWeight.Medium
                                        )
                                        IconButton(
                                            onClick = { isExpanded = !isExpanded }
                                        ) {
                                            Icon(
                                                imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                                contentDescription = if (isExpanded) "Collapse" else "Expand"
                                            )
                                        }
                                    }
                                    CustomSpacer(modifier = Modifier.height(MaterialTheme.customSpacing.small))
                                    specsToShow.forEach { (label, value) ->
                                        SpecificationRow(label, value)
                                    }
                                    Text(
                                        text = if (isExpanded) "Show Less" else "Read More",
                                        color = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier
                                            .padding(top = MaterialTheme.customSpacing.small)
                                            .clickable { isExpanded = !isExpanded }
                                    )
                                }
                            }
                        }
                    }
                    item {
                        // Quantity and Add to Cart
                        CustomLazyRow {
                            // Quantity Selector
                            item {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Qty:",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Medium
                                    )
                                    Spacer(modifier = Modifier.width(MaterialTheme.customSpacing.small))

                                    OutlinedButton(
                                        onClick = { if (quantity > 1) quantity-- },
                                        modifier = Modifier.size(MaterialTheme.customSpacing.boxMedium),
                                        contentPadding = PaddingValues(MaterialTheme.customSpacing.none)
                                    ) {
                                        Icon(Icons.Default.Remove, contentDescription = "Decrease")
                                    }

                                    Text(
                                        text = quantity.toString(),
                                        modifier = Modifier.padding(horizontal = MaterialTheme.customSpacing.medium),
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Medium
                                    )

                                    OutlinedButton(
                                        onClick = { quantity++ },
                                        modifier = Modifier.size(MaterialTheme.customSpacing.boxMedium),
                                        contentPadding = PaddingValues(MaterialTheme.customSpacing.none)
                                    ) {
                                        Icon(Icons.Default.Add, contentDescription = "Increase")
                                    }
                                }
                            }
                        }
                    }
                    item {
                        // Action Button
                        PaddedSection {
                            CustomOutlinedButton(
                                label = R.string.add_to_cart,
                                icon = ButtonIcon.Vector(Icons.Filled.ShoppingCart),
                                onClick = {},
                            )
                        }
                    }
                }
            }
        },
        actions = {
            ButtonIconComposable(
                buttonIcon = ButtonIcon.Vector(Icons.Filled.MoreVert),
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                onClick = {},
                contentDescription = "More"
            )
        }
    )
}

@Composable
fun SpecificationRow(label: String, value: String) {
    val windowSizeConstant = LocalWindowSizeConstant.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = MaterialTheme.customSpacing.extraSmall),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = windowSizeConstant.appSubHeadLineTextStyle,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = windowSizeConstant.bodyTextStyle
        )
    }
}

data class ShippingOption(
    val name: String,
    val delivery: String,
    val price: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ProductDescriptionPreview() {
    val nav = rememberNavController()

    MyAppTheme(
        dynamicColor = false,
        content = {
            ProductDescriptionScreen(
                onBackNavigation = {},
                onBuyNowClick = {}
            )
        }
    )
}