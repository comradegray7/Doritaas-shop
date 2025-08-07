package com.example.myapp.view.screens.product

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapp.R
import com.example.myapp.ui.theme.LocalWindowSizeConstant
import com.example.myapp.ui.theme.MyAppTheme
import com.example.myapp.ui.theme.customSpacing
import com.example.myapp.view.components.CustomImageContainer
import com.example.myapp.view.components.CustomLazyColumn
import com.example.myapp.view.components.CustomScaffoldContainer
import com.example.myapp.view.components.CustomSpacer
import com.example.myapp.view.components.HeadlineWidget
import com.example.myapp.view.components.NavigationBarShimmer
import com.example.myapp.view.components.PaddedSection
import com.example.myapp.view.components.ProductShimmerList
import com.example.myapp.view.components.custom.buttons.CustomButton
import com.example.myapp.view.utils.ButtonIcon
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Checkout screen for completing purchases.
 * Handles order summary, shipping, and payment information.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckOutScreen(
    onBackNavigation: () -> Unit,
) {
    val windowSizeConstant = LocalWindowSizeConstant.current
    val spacing = MaterialTheme.customSpacing

    var selectedPayment by remember { mutableStateOf("stripe") }
    var isProcessing by remember { mutableStateOf(false) }
    var showSuccess by remember { mutableStateOf(false) }

    LaunchedEffect(showSuccess) {
        if (showSuccess) {
            delay(1500)
            showSuccess = false
            isProcessing = false
        }
    }

    var loading by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        delay(1500)
        loading = false
    }

    val color = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    CustomScaffoldContainer(
        onRefresh = {},
        onNavigateBack = { onBackNavigation() },
        verticalArrangement = Arrangement.Top,
        title = R.string.check_out_title,
        bottomBarContent = {
            if (loading) {
                NavigationBarShimmer()
            } else {
                PaddedSection {
                    AnimatedVisibility(
                        visible = !showSuccess,
                        exit = slideOutVertically() + fadeOut()
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            CustomButton(
                                label = R.string.check_out,
                                icon = ButtonIcon.Vector(Icons.Filled.ShoppingCart),
//                                onClick = { isProcessing = true },
                                onClick = {
                                    isProcessing = true
                                    coroutineScope.launch {
                                        delay(1000) // Simulate processing time
                                        showSuccess = true
                                    }
                                },

                                enabled = !isProcessing,
                            ) {
                                if (isProcessing) {
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(spacing.medium), // 12.dp
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        CircularProgressIndicator(
                                            modifier = Modifier.size(spacing.baseMedium), // 20.dp
                                            color = MaterialTheme.colorScheme.onPrimary,
                                            strokeWidth = spacing.border // 2.dp
                                        )
                                        Text(
                                            text = stringResource(R.string.processing),
                                            style = MaterialTheme.typography.labelLarge,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                } else {
                                    Text(
                                        text = stringResource(R.string.complete_purchase),
                                        style = MaterialTheme.typography.labelLarge,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }
                    }
                }
            }
        },
        content = {
            if (loading) {
                ProductShimmerList()
            } else {
                // Section for payment success animation
                PaddedSection {
                    AnimatedVisibility(
                        visible = showSuccess,
                        enter = slideInVertically(initialOffsetY = { it }) + fadeIn()
                    ) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer
                            )
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(spacing.large),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(spacing.medium)
                            ) {
                                Icon(
                                    Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    modifier = Modifier.size(spacing.xxLarge),
                                    tint = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    "Payment Successful!",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                                Text(
                                    "Your order will be delivered in 2-3 days",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                        }
                    }
                    CustomSpacer()
                }

                // Section for order summary and product details
                PaddedSection {
                    ProductSummaryCard(
                        showLeading = true,
                        showItems = false,
                        itemCount = 4,
                        savings = 6999.99,
                        showMiddle = true,
                        middleComposable = {
                            Column(
                                modifier = Modifier
                                    .padding(horizontal = windowSizeConstant.listRightPadding)
                                    .width(spacing.extraLarge),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(spacing.normal)
                            ) {
                                Text(
                                    text = "Vans Sneakers",
                                    style = windowSizeConstant.appSubHeadLineTextStyle,
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 2,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                                Text(
                                    text = stringResource(R.string.lorem_short),
                                    style = windowSizeConstant.appSubHeadLineTextStyle,
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 2,
                                )
                            }
                        },
                        leadingIcon = {
                            CustomImageContainer(
                                data = R.drawable.paul_gaudriault,
                                width = windowSizeConstant.carouselImageWidth,
                                height = windowSizeConstant.carouselImageHeight,
                            )
                        }
                    )
                }

                // Section for payment method selection and order cost breakdown
                CustomLazyColumn {
                    item {
                        HeadlineWidget(
                            leading = R.string.payment_methods,
                            trailing = {
                                Icon(Icons.Filled.Payments, contentDescription = null)
                            }
                        )
                    }

                    item {
                        PaddedSection {
                            PaymentOption(
                                icon = ButtonIcon.Resource(R.drawable.master_card_icon),
                                tintColor = MaterialTheme.colorScheme.scrim,
                                title = "Master Card",
                                isSelected = selectedPayment == "card",
                                onClick = { selectedPayment = "card" }
                            )
                            CustomSpacer()
                            PaymentOption(
                                icon = ButtonIcon.Resource(R.drawable.paypal_icon),
                                tintColor = MaterialTheme.colorScheme.scrim,
                                title = "PayPal",
                                isSelected = selectedPayment == "paypal",
                                onClick = { selectedPayment = "paypal" }
                            )
                            CustomSpacer()
                            PaymentOption(
                                icon = ButtonIcon.Resource(R.drawable.visa_icon),
                                tintColor = MaterialTheme.colorScheme.scrim,
                                title = "Visa",
                                isSelected = selectedPayment == "visa",
                                onClick = { selectedPayment = "visa" }
                            )
                            CustomSpacer()
                            PaymentOption(
                                icon = ButtonIcon.Resource(R.drawable.stripe_icon),
                                tintColor = MaterialTheme.colorScheme.scrim,
                                isSelected = selectedPayment == "stripe",
                                title = "Stripe",
                                onClick = { selectedPayment = "stripe" }
                            )
                        }
                    }

                    item {
                        PaddedSection {
                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
                                )
                            ) {
                                Column(
                                    modifier = Modifier.padding(spacing.base),
                                    verticalArrangement = Arrangement.spacedBy(spacing.medium)
                                ) {
                                    // Subtotal row
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text("Subtotal", style = MaterialTheme.typography.bodyLarge)
                                        Text("$299.99", style = MaterialTheme.typography.bodyLarge)
                                    }
                                    // Shipping row
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text("Shipping", style = MaterialTheme.typography.bodyLarge)
                                        Text(
                                            "Free",
                                            style = MaterialTheme.typography.bodyLarge,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                    // Tax row
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text("Tax", style = MaterialTheme.typography.bodyLarge)
                                        Text("$24.00", style = MaterialTheme.typography.bodyLarge)
                                    }

                                    // Divider
                                    HorizontalDivider(
                                        modifier = Modifier.padding(vertical = spacing.small),
                                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                                    )

                                    // Total row
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text("Total", style = MaterialTheme.typography.titleMedium)
                                        Text(
                                            "$323.99",
                                            style = MaterialTheme.typography.titleMedium,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}

/**
 * Payment option card for selecting a payment method.
 * Shows icon, title, and radio button for selection.
 */
@Composable
fun PaymentOption(
    icon: ButtonIcon? = null,
    isSelected: Boolean,
    onClick: () -> Unit,
    tintColor: Color = MaterialTheme.colorScheme.onPrimary,
    contentDescription: String = "",
    title: String
) {
    val windowSizeConstant = LocalWindowSizeConstant.current
    val spacing = MaterialTheme.customSpacing

    Card(
        modifier = windowSizeConstant.adaptiveWidthModifier
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected)
                MaterialTheme.colorScheme.primaryContainer
            else
                MaterialTheme.colorScheme.surfaceVariant
        ),
        border = if (isSelected)
            BorderStroke(spacing.border, MaterialTheme.colorScheme.primary)
        else null
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(windowSizeConstant.listCardPadding),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Payment method icon
            icon?.let { iconImage ->
                when (iconImage) {
                    is ButtonIcon.Resource -> {
                        Icon(
                            painter = painterResource(id = iconImage.drawableRes),
                            contentDescription = contentDescription,
                            tint = tintColor,
                            modifier = Modifier.size(windowSizeConstant.paymentOptionImagePadding)
                        )
                    }
                    is ButtonIcon.Vector -> {
                        Icon(
                            imageVector = iconImage.imageVector,
                            contentDescription = contentDescription,
                            tint = tintColor,
                            modifier = Modifier.size(windowSizeConstant.paymentOptionImagePadding)
                        )
                    }
                }
            } ?: Spacer(modifier = Modifier.width(spacing.boxMedium))

            // Payment method title
            Text(text = title, style = MaterialTheme.typography.titleMedium)

            // Radio button for selection
            RadioButton(
                selected = isSelected,
                onClick = { onClick() },
                colors = RadioButtonDefaults.colors(
                    selectedColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    }
}

/**
 * Preview function for the checkout screen in night mode.
 */
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CheckOutPreviewScreen() {
    MyAppTheme(
        dynamicColor = false,
        content = {
            CheckOutScreen(
                onBackNavigation = {},
            )
        }
    )
}