package com.example.myapp.ui.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.example.myapp.view.utils.LocalWindowSizeClass
import com.example.myapp.view.utils.isCompact
import com.example.myapp.view.utils.isExpanded
import com.example.myapp.view.utils.isMedium

/**
 * Composable function that provides adaptive UI constants based on the current window size.
 * 
 * This function creates a responsive design system by adapting various UI elements
 * (typography, spacing, sizing, layout) based on the device's window size class.
 * It uses Material Design 3's window size classes: Compact, Medium, and Expanded.
 * 
 * The function wraps the provided content in a CompositionLocalProvider to make
 * these adaptive constants available throughout the composition tree.
 * 
 * @param content The composable content that will have access to the adaptive constants
 */
@Composable
fun ProvideAppConstants(
    content: @Composable () -> Unit
) {
    // Get the current window size class to determine which constants to use
    val windowSizeClass = LocalWindowSizeClass.current

    /**
     * TYPOGRAPHY STYLES
     * Adaptive text styles that scale based on screen size
     */
    
    // Price text style - used for displaying product prices
    val priceTextStyle = when{
        windowSizeClass.isCompact -> MaterialTheme.typography.bodyLarge
        windowSizeClass.isMedium -> MaterialTheme.typography.titleMedium
        windowSizeClass.isExpanded -> MaterialTheme.typography.titleMedium
        else -> MaterialTheme.typography.bodyLarge
    }
   
    // Main headline text style - used for primary headings
    val appHeadLineTextStyle = when {
        windowSizeClass.isCompact -> MaterialTheme.typography.titleLarge
        windowSizeClass.isMedium -> MaterialTheme.typography.headlineSmall
        windowSizeClass.isExpanded -> MaterialTheme.typography.headlineMedium
        else -> MaterialTheme.typography.titleLarge
    }

    // Label text style - used for form labels and small descriptive text
    val labelTextStyle = when{
        windowSizeClass.isCompact -> MaterialTheme.typography.bodySmall
        windowSizeClass.isMedium -> MaterialTheme.typography.bodyMedium
        windowSizeClass.isExpanded -> MaterialTheme.typography.titleMedium
        else -> MaterialTheme.typography.bodySmall
    }

    // Body text style - used for main content text
    val bodyTextStyle = when{
        windowSizeClass.isCompact -> MaterialTheme.typography.titleSmall
        windowSizeClass.isMedium -> MaterialTheme.typography.bodyLarge
        windowSizeClass.isExpanded -> MaterialTheme.typography.titleMedium
        else -> MaterialTheme.typography.bodyLarge
    }

    // Sub-headline text style - used for secondary headings
    val appSubHeadLineTextStyle = when {
        windowSizeClass.isCompact -> MaterialTheme.typography.bodyLarge
        windowSizeClass.isMedium -> MaterialTheme.typography.titleMedium
        windowSizeClass.isExpanded -> MaterialTheme.typography.titleLarge
        else -> MaterialTheme.typography.bodyLarge
    }

    /**
     * LAYOUT DIMENSIONS
     * Adaptive sizing for various UI components
     */
    
    // Card height - used for product cards and similar containers
    val cardHeight = when {
        windowSizeClass.isCompact -> 300.dp
        windowSizeClass.isMedium -> 320.dp
        windowSizeClass.isExpanded -> 340.dp
        else -> 300.dp
    }

    // Profile vertical spacer - spacing for profile-related layouts
    val adaptiveProfileVerticalSpacer = when {
        windowSizeClass.isCompact -> 80.dp
        windowSizeClass.isMedium -> 120.dp
        windowSizeClass.isExpanded -> 130.dp
        else -> 80.dp
    }

    // Profile card padding - dimensions for profile card containers
    val profileCardPadding = when {
        windowSizeClass.isCompact -> DpSize(360.dp, 180.dp)
        windowSizeClass.isMedium -> DpSize(420.dp, 200.dp)
        windowSizeClass.isExpanded -> DpSize(440.dp, 220.dp)
        else -> DpSize(360.dp, 180.dp)
    }

    // List image padding - sizing for images in list items
    val listImagePadding = when {
        windowSizeClass.isCompact -> DpSize(76.dp, 75.dp)
        windowSizeClass.isMedium -> DpSize(120.dp, 100.dp)
        windowSizeClass.isExpanded -> DpSize(130.dp, 120.dp)
        else -> DpSize(75.dp, 75.dp)
    }

    //logo image padding - sizing for logo image
    val logoPadding = when {
        windowSizeClass.isCompact -> DpSize(86.dp, 80.dp)
        windowSizeClass.isMedium -> DpSize(120.dp, 100.dp)
        windowSizeClass.isExpanded -> DpSize(130.dp, 120.dp)
        else -> DpSize(86.dp, 80.dp)
    }

    // List right padding - right margin for list items
    val listRightPadding = when {
        windowSizeClass.isCompact -> 12.dp
        windowSizeClass.isMedium -> 0.dp
        windowSizeClass.isExpanded -> 0.dp
        else -> 0.dp
    }

    /**
     * CAROUSEL STYLES
     * Adaptive styling for carousel/slider components
     */
    
    // Carousel page spacing - gap between carousel items
    val carouselPageSpacing = when {
        windowSizeClass.isCompact -> MaterialTheme.customSpacing.normal
        windowSizeClass.isMedium -> MaterialTheme.customSpacing.medium
        windowSizeClass.isExpanded -> MaterialTheme.customSpacing.large
        else -> MaterialTheme.customSpacing.normal
    }

    // Carousel image height - height of images in carousel
    val carouselImageHeight = when {
        windowSizeClass.isCompact -> 110.dp
        windowSizeClass.isMedium -> 120.dp
        windowSizeClass.isExpanded -> 130.dp
        else -> 110.dp
    }

    // Carousel image width - width of images in carousel
    val carouselImageWidth = when {
        windowSizeClass.isCompact -> 100.dp
        windowSizeClass.isMedium -> 110.dp
        windowSizeClass.isExpanded -> 120.dp
        else -> 100.dp
    }

    /**
     * CONTENT PADDING STYLES
     * Adaptive padding for content containers
     */
    
    // Content horizontal padding - horizontal spacing for main content
    val contentPadding = when {
        windowSizeClass.isCompact -> MaterialTheme.customSpacing.medium
        windowSizeClass.isMedium -> MaterialTheme.customSpacing.boxMedium
        windowSizeClass.isExpanded -> MaterialTheme.customSpacing.mediumLarge
        else -> MaterialTheme.customSpacing.medium
    }

    // top bar and bottom bar horizontal padding - horizontal spacing for top bar and bottom bar content
  val appBarPadding = when {
        windowSizeClass.isCompact -> MaterialTheme.customSpacing.none
        windowSizeClass.isMedium -> MaterialTheme.customSpacing.box
        windowSizeClass.isExpanded -> MaterialTheme.customSpacing.mediumLarge
        else -> MaterialTheme.customSpacing.medium
    }

    // Content vertical padding - vertical spacing for main content
    val contentVerticalPadding = when {
        windowSizeClass.isCompact -> MaterialTheme.customSpacing.medium
        windowSizeClass.isMedium -> MaterialTheme.customSpacing.normalLarge
        windowSizeClass.isExpanded -> MaterialTheme.customSpacing.base
        else -> MaterialTheme.customSpacing.medium
    }

    // Base vertical padding - fundamental vertical spacing
    val baseVerticalPadding = when {
        windowSizeClass.isCompact -> MaterialTheme.customSpacing.smaller
        windowSizeClass.isMedium -> MaterialTheme.customSpacing.small
        windowSizeClass.isExpanded -> MaterialTheme.customSpacing.normal
        else -> MaterialTheme.customSpacing.medium
    }
    
    /**
     * CATEGORY STYLES
     * Adaptive styling for category-related components
     */
    
    // Category padding - dimensions for category containers
    val categoryPadding = when {
        windowSizeClass.isCompact -> DpSize(MaterialTheme.customSpacing.mediumLarge, height = MaterialTheme.customSpacing.height)
        windowSizeClass.isMedium -> DpSize(MaterialTheme.customSpacing.xxLarge, height = MaterialTheme.customSpacing.boxLarge)
        windowSizeClass.isExpanded -> DpSize(MaterialTheme.customSpacing.boxLarge, height = MaterialTheme.customSpacing.xx)
        else -> DpSize(MaterialTheme.customSpacing.mediumLarge, height = MaterialTheme.customSpacing.height)
    }

    // OR divider padding - spacing for "OR" divider elements
    val orDividerPadding = when {
     windowSizeClass.isCompact -> 140.dp
     windowSizeClass.isMedium -> 180.dp
     windowSizeClass.isExpanded -> 220.dp
     else -> 140.dp
    }

    // Back button offset padding - positioning for back button elements
    val backButtonOffsetPadding = when {
     windowSizeClass.isCompact -> (-124).dp
     windowSizeClass.isMedium -> (-164).dp
     windowSizeClass.isExpanded -> (-184).dp
     else -> (-124).dp
    }

    /**
     * LAYOUT ALIGNMENT AND ARRANGEMENT
     * Adaptive layout positioning and spacing
     */
    
    // Horizontal alignment - how content is aligned horizontally
    val horizontalAlignment = when {
        windowSizeClass.isCompact -> Alignment.Start
        windowSizeClass.isMedium -> Alignment.CenterHorizontally
        windowSizeClass.isExpanded -> Alignment.CenterHorizontally
        else -> Alignment.Start
    }

    // Horizontal arrangement - how items are spaced horizontally
    val horizontalArrangement = when {
        windowSizeClass.isCompact -> Arrangement.SpaceBetween
        windowSizeClass.isMedium -> Arrangement.SpaceAround
        windowSizeClass.isExpanded -> Arrangement.SpaceAround
        else -> Arrangement.SpaceBetween
    }

    /**
     * PRODUCT STYLES
     * Adaptive styling for product-related components
     */
    
    // Product title text style - used for product names
    val titleTextStyle = when {
        windowSizeClass.isCompact -> MaterialTheme.typography.bodyMedium
        windowSizeClass.isMedium -> MaterialTheme.typography.bodyLarge
        windowSizeClass.isExpanded -> MaterialTheme.typography.headlineSmall
        else -> MaterialTheme.typography.bodyMedium
    }

    // Product subtitle text style - used for product descriptions
    val subtitleTextStyle = when {
        windowSizeClass.isCompact -> MaterialTheme.typography.bodySmall
        windowSizeClass.isMedium -> MaterialTheme.typography.bodyMedium
        windowSizeClass.isExpanded -> MaterialTheme.typography.bodyLarge
        else -> MaterialTheme.typography.bodySmall
    }

    /**
     * IMAGE STYLES
     * Adaptive sizing for various image components
     */
    
    // Main product image size - dimensions for primary product images
    val productImageSize = when {
        windowSizeClass.isCompact -> DpSize(360.dp, 160.dp)
        windowSizeClass.isMedium -> DpSize(620.dp, 200.dp)
        windowSizeClass.isExpanded -> DpSize(680.dp, 240.dp)
        else -> DpSize(360.dp, 140.dp)
    }

    // Badge padding - dimensions for badge/notification elements
    val badgePadding = when {
        windowSizeClass.isCompact -> DpSize(32.dp, 24.dp)
        windowSizeClass.isMedium -> DpSize(42.dp, 34.dp)
        windowSizeClass.isExpanded -> DpSize(52.dp, 44.dp)
        else -> DpSize(MaterialTheme.customSpacing.small, MaterialTheme.customSpacing.smaller)
    }
    
    // Onboarding image size - dimensions for onboarding screen images
    val onBoardingImageSize = when {
        windowSizeClass.isCompact -> DpSize(280.dp, 130.dp)
        windowSizeClass.isMedium -> DpSize(310.dp, 140.dp)
        windowSizeClass.isExpanded -> DpSize(330.dp, 150.dp)
        else -> DpSize(280.dp, 130.dp)
    }

    // Small carousel image height - height for smaller carousel images
    val smallCarouselImageHeight = when {
        windowSizeClass.isCompact -> 40.dp
        windowSizeClass.isMedium -> 60.dp
        windowSizeClass.isExpanded -> 80.dp
        else -> 40.dp
    }

    // Product card height - height for product card containers
    val productCardHeight = when {
        windowSizeClass.isCompact -> 60.dp
        windowSizeClass.isMedium -> 80.dp
        windowSizeClass.isExpanded -> 90.dp
        else -> 60.dp
    }

    // List card padding - padding for list item cards
    val listCardPadding = when {
        windowSizeClass.isCompact -> 10.dp
        windowSizeClass.isMedium -> 14.dp
        windowSizeClass.isExpanded -> 16.dp
        else -> 10.dp
    }

    // Payment option image padding - dimensions for payment method images
    val paymentOptionImagePadding = when {
        windowSizeClass.isCompact -> DpSize(80.dp, 35.dp)
        windowSizeClass.isMedium -> DpSize(100.dp, 45.dp)
        windowSizeClass.isExpanded -> DpSize(120.dp, 55.dp)
        else -> DpSize(100.dp, 40.dp)
    }

    // Product summary image padding - dimensions for product summary images
    val productSummaryImagePadding = when {
        windowSizeClass.isCompact -> DpSize(60.dp, 50.dp)
        windowSizeClass.isMedium -> DpSize(70.dp, 65.dp)
        windowSizeClass.isExpanded -> DpSize(90.dp, 70.dp)
        else -> DpSize(60.dp, 50.dp)
    }

    // Small carousel image width - width for smaller carousel images
    val smallCarouselImageWidth = when {
        windowSizeClass.isCompact -> 60.dp
        windowSizeClass.isMedium -> 70.dp
        windowSizeClass.isExpanded -> 80.dp
        else -> 60.dp
    }

    /**
     * ADAPTIVE DIMENSIONS
     * Responsive sizing that adapts to screen size
     */
    
    // Adaptive height - responsive height for various components
    val adaptiveHeight = when {
        windowSizeClass.isCompact -> 52.dp
        windowSizeClass.isMedium -> 53.dp
        windowSizeClass.isExpanded -> 53.dp
        else -> 52.dp
    }

    // Adaptive form height - responsive height for form elements
    val adaptiveFormHeight = when {
        windowSizeClass.isCompact -> 48.dp
        windowSizeClass.isMedium -> 50.dp
        windowSizeClass.isExpanded -> 51.dp
        else -> 48.dp
    }

    // Adaptive width modifier - responsive width modifier for containers
    val adaptiveWidthModifier = when {
        windowSizeClass.isCompact -> Modifier.fillMaxWidth()
        windowSizeClass.isMedium -> Modifier.fillMaxWidth()
        windowSizeClass.isExpanded -> Modifier.width(680.dp)
        else -> Modifier.fillMaxWidth()
    }

    // Adaptive form width modifier - responsive width modifier for forms
    val adaptiveFormWidthModifier = when {
        windowSizeClass.isCompact -> Modifier.fillMaxWidth()
        windowSizeClass.isMedium -> Modifier.width(420.dp)
        windowSizeClass.isExpanded -> Modifier.width(480.dp)
        else -> Modifier.fillMaxWidth()
    }

    // Grid columns - number of columns in grid layouts
    val gridCols = when {
        windowSizeClass.isCompact -> 2
        windowSizeClass.isMedium -> 3
        windowSizeClass.isExpanded -> 4
        else -> 2
    }

    // Icon size - responsive icon dimensions
    val iconSize = when {
       windowSizeClass.isCompact -> 24.dp
       windowSizeClass.isMedium -> 28.dp
       windowSizeClass.isExpanded -> 30.dp
       else -> 24.dp
   }

    /**
     * Create the WindowSizeAppConstants object with all the adaptive values
     * This object will be provided to the composition tree via CompositionLocal
     */
    val windowSizeConstants = WindowSizeAppConstants(
        logoPadding = logoPadding,
        appBarPadding = appBarPadding,
        priceTextStyle = priceTextStyle,
        cardHeight = cardHeight,
        listImagePadding = listImagePadding,
        contentPadding = contentPadding,
        titleTextStyle = titleTextStyle,
        subtitleTextStyle = subtitleTextStyle,
        carouselPageSpacing = carouselPageSpacing,
        horizontalAlignment = horizontalAlignment,
        horizontalArrangementStyle = horizontalArrangement,
        iconPadding = iconSize,
        baseVerticalPadding = baseVerticalPadding,
        labelTextStyle = labelTextStyle,
        bodyTextStyle =  bodyTextStyle,
        categoryPadding = categoryPadding,
        productImageSize = productImageSize,
        contentVerticalPadding = contentVerticalPadding,
        appHeadLineTextStyle = appHeadLineTextStyle,
        appSubHeadLineTextStyle = appSubHeadLineTextStyle,
        orDividerPadding = orDividerPadding,
        onBoardingImageSize = onBoardingImageSize,
        backButtonOffsetPadding = backButtonOffsetPadding,
        carouselImageHeight = carouselImageHeight,
        carouselImageWidth = carouselImageWidth,
        smallCarouselImageWidth = smallCarouselImageWidth,
        smallCarouselImageHeight = smallCarouselImageHeight,
        badgePadding = badgePadding,
        listRightPadding = listRightPadding,
        productCardHeight = productCardHeight,
        adaptiveWidthModifier = adaptiveWidthModifier,
        adaptiveHeight = adaptiveHeight,
        adaptiveFormWidthModifier = adaptiveFormWidthModifier,
        profileCardPadding = profileCardPadding,
        adaptiveProfileVerticalSpacer = adaptiveProfileVerticalSpacer,
        listCardPadding =  listCardPadding,
        productSummaryImagePadding = productSummaryImagePadding,
        paymentOptionImagePadding = paymentOptionImagePadding,
        gridCols = gridCols,
        adaptiveFormHeight = adaptiveFormHeight
    )

    /**
     * Provide the adaptive constants to the composition tree
     * This makes all the window-size-dependent constants available to child composable
     */
    CompositionLocalProvider(LocalWindowSizeConstant provides windowSizeConstants) {
        content()
    }
}
