package com.example.myapp.view.screens.product

import android.content.res.Configuration
import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapp.R
import com.example.myapp.ui.theme.LocalWindowSizeConstant
import com.example.myapp.ui.theme.MyAppTheme
import com.example.myapp.ui.theme.customSpacing
import com.example.myapp.view.components.CustomImageContainer
//import com.example.myapp.view.components.movingShimmerEffect
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

/**
 * CarouselItem - Data class representing a single item in the product carousel.
 *
 * This data class contains the information needed to display a carousel item,
 * including the image resource and accessibility description.
 *
 * @param id Unique identifier for the carousel item
 * @param imageResId Drawable resource ID for the carousel image
 * @param contentDescriptionResId String resource for accessibility description
 */
@Parcelize
data class CarouselItem(
    val id: Int,
    @DrawableRes val imageResId: Int,
    val contentDescriptionResId: String,
) : Parcelable

/**
 * ProductCarousel - Horizontal scrolling carousel component for displaying promotional products.
 *
 * This composable creates an auto-scrolling horizontal pager that displays product images
 * in a carousel format. It features:
 * - Automatic scrolling with configurable interval
 * - Smooth animations with easing curves
 * - Infinite scrolling capability
 * - Responsive design with adaptive sizing
 *
 * @param autoScrollInterval Time interval between auto-scroll transitions (default: 6 seconds)
 */
@ExperimentalMaterial3Api
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductCarousel(
    autoScrollInterval: Long = 6000L // 6 seconds default interval
) {
    val windowSizeConstant = LocalWindowSizeConstant.current
    val coroutineScope = rememberCoroutineScope()

    // Sample carousel items - in real app this would come from a repository
    val items = listOf(
        CarouselItem(1, R.drawable.headline_promo_2, "one"),
        CarouselItem(2, R.drawable.galaxy_watch_ultra, "one"),
        CarouselItem(3, R.drawable.doritaas, "two"),
        CarouselItem(4, R.drawable.galaxy_buds3_pro, "four"),
        CarouselItem(5, R.drawable.galaxy_watch_ultra, "four"),
        CarouselItem(6, R.drawable.galaxy_buds3_pro, "four"),
    )

    // Initialize pager state with a large initial page for infinite scrolling
    val pagerState = rememberPagerState(initialPage = Int.MAX_VALUE / 2) { Int.MAX_VALUE }

    // Auto-scroll effect - continuously scrolls through carousel items
    LaunchedEffect(Unit) {
        while (true) {
            delay(autoScrollInterval)
            coroutineScope.launch {
                val nextPage = pagerState.currentPage + 1
                pagerState.animateScrollToPage(
                    page = nextPage,
                    animationSpec = tween(
                        durationMillis = 800, // Animation duration for smooth transitions
                        easing = FastOutSlowInEasing // Smooth easing curve for natural movement
                    )
                )
            }
        }
    }

    // Main carousel container
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Horizontal pager for carousel functionality
        HorizontalPager(
            state = pagerState,
            pageSpacing = windowSizeConstant.carouselPageSpacing,
            modifier = Modifier
                .fillMaxWidth()
                .height(windowSizeConstant.carouselImageHeight),
            contentPadding = PaddingValues(horizontal = windowSizeConstant.contentPadding),
            pageSize = PageSize.Fixed(windowSizeConstant.carouselImageWidth) // Fixed size for consistent multi-browse behavior
        ) { page ->
            // Get the carousel item for current page (using modulo for infinite scrolling)
            val item = items[page % items.size]
            // Display carousel item image
            CustomImageContainer(
                data = item.imageResId,
                width = windowSizeConstant.carouselImageWidth,
                height = windowSizeConstant.carouselImageHeight,
                shape = MaterialTheme.shapes.large
            )
        }
    }
}

/**
 * Preview function for the product carousel in night mode.
 * Used for development and testing the carousel appearance.
 */
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopScreenPreview() {
    MyAppTheme(
        dynamicColor = false,
        content = {
            ProductCarousel()
        }
    )
}
