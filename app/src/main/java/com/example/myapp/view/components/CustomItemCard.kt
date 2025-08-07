package com.example.myapp.view.components

import android.content.res.Configuration
import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.myapp.ui.theme.LocalWindowSizeConstant
import com.example.myapp.ui.theme.MyAppTheme
import com.example.myapp.ui.theme.customSpacing
import kotlinx.parcelize.Parcelize

@Parcelize
data class CardItemData(
    val id: String,
    val name: String,
    val price: Double,
    val rating: Float? = null,
    val originalPrice: Double? = null,
    val quantity: Int,
    val category: String,
    val inStock: Boolean = true,
    val description: String,
    val currentPrice: Double,
    @DrawableRes val imageRes: Int
) : Parcelable

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun CustomItemCard(
    modifier: Modifier = Modifier,
    item: CardItemData,
    onItemClick: () -> Unit = {},
    actions: @Composable () -> Unit = {},
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope
) {

    val windowSizeConstant = LocalWindowSizeConstant.current

    PaddedSection {
        Card(
            modifier = windowSizeConstant.adaptiveWidthModifier
                .clickable { onItemClick() },
            elevation = CardDefaults.cardElevation(
                defaultElevation = MaterialTheme.customSpacing.extraSmall
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(windowSizeConstant.listCardPadding),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    with(sharedTransitionScope) {
                        Box(
                            modifier = Modifier
                                .size(windowSizeConstant.listImagePadding)
                                .clip(MaterialTheme.shapes.medium)
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                        ) {
                            CustomImageContainer(
                                data = item.imageRes,
                                contentDescription = item.name,
                                modifier = Modifier.sharedBounds(
                                    sharedContentState = rememberSharedContentState(key = "image-${item.id}"),
                                    animatedVisibilityScope = animatedContentScope,
                                    boundsTransform = { _, _ ->
                                        tween(
                                            durationMillis = 500,
                                            easing = FastOutSlowInEasing
                                        )
                                    }
                                ),
                            )

                            // Discount Badge and Out of Stock Overlay
                            if (item.originalPrice != item.currentPrice) {
                                val discount =
                                    item.originalPrice?.let { ((item.originalPrice - item.currentPrice) / it * 100).toInt() }
                                Box(
                                    modifier = Modifier
                                        .align(Alignment.TopStart)
                                        .offset(x = (-2).dp, y = (-2).dp)
                                        .background(
                                            color = MaterialTheme.colorScheme.tertiaryContainer,
                                            shape = MaterialTheme.shapes.medium
                                        )
                                        .padding(
                                            horizontal = MaterialTheme.customSpacing.smaller,
                                            vertical = MaterialTheme.customSpacing.baseSpacer
                                        )
                                ) {
                                    Text(
                                        text = "${discount}% OFF",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onTertiaryContainer,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 9.sp
                                    )
                                }
                            }
                            if (!item.inStock) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(
                                            Color.Black.copy(alpha = 0.5f),
                                            MaterialTheme.shapes.medium
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "Out of Stock",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    }
                }
                // Product Details with sharedBounds for text
                Column(
                    modifier = Modifier
                        .padding(horizontal = windowSizeConstant.listRightPadding)
                        .weight(1f, fill = false)
                ) {
                    Text(
                        text = item.name,
                        style = windowSizeConstant.priceTextStyle,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(MaterialTheme.customSpacing.extraSmall))

                    Text(
                        text = item.category,
                        style = windowSizeConstant.labelTextStyle,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    CustomSpacer(modifier = Modifier.height(MaterialTheme.customSpacing.small))

                    Text(
                        text = "$${item.currentPrice}",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.customSpacing.normal)
                ) {
                    actions()
                }
            }
        }
    }
}
