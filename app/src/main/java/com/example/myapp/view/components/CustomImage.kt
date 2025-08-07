
package com.example.myapp.view.components

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.myapp.ui.theme.colors
import com.example.myapp.ui.theme.customSpacing


/**
 * A versatile image component that can handle:
 * - Network URLs (http/https)
 * - Local file paths
 * - Resource IDs
 * - File objects
 * - Any object that Coil can load
 */

@Composable
fun CustomImageContainer(
    modifier: Modifier = Modifier,
    data: Any?, // Can be null
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Crop,
    shape: Shape = RoundedCornerShape(MaterialTheme.customSpacing.medium),
    placeholder: Painter? = null,
    error: Painter? = null,
    fallback: Painter? = null,
    colorFilter: ColorFilter? = null,
    width: Dp = 0.dp,
    height: Dp = 0.dp,
    size: DpSize = DpSize(0.dp, 0.dp),
    showLoading: Boolean = true,
    crossFade: Boolean = true,
    onLoading: ((AsyncImagePainter.State.Loading) -> Unit)? = null,
    onSuccess: ((AsyncImagePainter.State.Success) -> Unit)? = null,
    onError: ((AsyncImagePainter.State.Error) -> Unit)? = null,
    imageLoader: ImageLoader? = null
) {
    val context = LocalContext.current

    val loader = imageLoader ?: ImageLoader(context)

    val imageRequest = remember(data) {
        data?.let {
            ImageRequest.Builder(context)
                .data(it)
                .crossfade(crossFade)
                .build()
        }
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        if (imageRequest != null) {
            AsyncImage(
                model = imageRequest,
                colorFilter = colorFilter,
                contentDescription = contentDescription,
                modifier = Modifier
                    .fillMaxSize()
                    .size(size)
                    .width(width)
                    .height(height)
                    .clip(shape),
                contentScale = contentScale,
                placeholder = placeholder,
                error = error,
                fallback = fallback,
                imageLoader = loader,
                onLoading = onLoading,
                onSuccess = onSuccess,
                onError = onError
            )
        } else {
            // Optional: fallback visual when data is null
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(shape) // Apply same shape to fallback
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                fallback?.let {
                    Icon(
                        painter = it,
                        contentDescription = contentDescription,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                    )
                } ?: run {
                    Icon(
                        imageVector = Icons.Default.Image,
                        contentDescription = "Placeholder",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
                    )
                }
            }
        }
    }
}
