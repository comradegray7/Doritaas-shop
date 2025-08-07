package com.example.myapp.view.screens.product

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myapp.ui.theme.customSpacing
import com.example.myapp.view.components.CustomLazyRow
import kotlinx.parcelize.Parcelize

/**
 * SmallProductImages - Composable function for displaying a horizontal list of small product images.
 * 
 * This composable creates a scrollable row of small product images that can be selected.
 * It's typically used in product detail screens to show multiple product images
 * in a thumbnail format, with the selected image highlighted.
 * 
 * @param productImages List of SmallImageItem objects containing image data
 * @param selectedIndex Index of the currently selected image
 * @param onImageSelected Callback function when an image is selected
 * 
 * Usage:
 * ```
 * SmallProductImages(
 *     productImages = product.images,
 *     selectedIndex = selectedImageIndex,
 *     onImageSelected = { index -> selectedImageIndex = index }
 * )
 * ```
 */
@Composable
fun SmallProductImages(
    productImages: List<SmallImageItem>,
    selectedIndex: Int,
    onImageSelected: (Int) -> Unit
) {
    CustomLazyRow {
        itemsIndexed(productImages) { index, item ->
            Card(
                modifier = Modifier
                    .padding(horizontal = MaterialTheme.customSpacing.extraSmall) // Small horizontal padding between images
                    .size(MaterialTheme.customSpacing.customMedium) // Fixed size for thumbnail images
                    .clip(MaterialTheme.shapes.large) // Rounded corners
                    .clickable { onImageSelected(index) }, // Handle image selection
            ) {
                Image(
                    painter = painterResource(id = item.imageResId), // Load image from resource
                    contentDescription = null, // No content description for decorative images
                    contentScale = ContentScale.Crop, // Crop image to fill the card
                    modifier = Modifier.fillMaxSize(), // Fill the entire card
                    colorFilter = if (index == selectedIndex)
                        ColorFilter.tint(
                            MaterialTheme.colorScheme.primary, // Primary color for selected image
                            blendMode = BlendMode.Hue
                        )
                    else
                        ColorFilter.tint(
                            MaterialTheme.colorScheme.surfaceVariant, // Surface variant for unselected images
                            blendMode = BlendMode.Dst
                        )
                )
            }
        }
    }
}

/**
 * SmallImageItem - Data class representing a small product image item.
 * 
 * This class holds the data for individual product images in the small image carousel.
 * It implements Parcelable for efficient data passing between composables.
 * 
 * @param id Unique identifier for the image
 * @param imageResId Drawable resource ID for the image
 * @param contentDescriptionResId String resource ID for accessibility description
 */
@Parcelize
data class SmallImageItem(
    val id: Int, // Unique identifier
    @DrawableRes val imageResId: Int, // Drawable resource ID
    val contentDescriptionResId: String, // Accessibility description
) : Parcelable