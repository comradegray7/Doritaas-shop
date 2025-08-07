package com.example.myapp.ui.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

/**
 * WindowSizeAppConstants - Data class for adaptive UI constants based on window size.
 *
 * This class holds all the adaptive constants (dimensions, paddings, text styles, etc.)
 * used throughout the app for responsive layouts. It is provided via CompositionLocal
 * and allows the UI to adapt to different device sizes and orientations.
 *
 * @property priceTextStyle Text style for price labels
 * @property labelTextStyle Text style for labels
 * @property bodyTextStyle Text style for body text
 * @property iconPadding Padding for icons
 * @property cardHeight Height for cards
 * @property logoPadding Size for logo images
 * @property listImagePadding Padding for images in lists
 * @property appBarPadding Padding for the app bar
 * @property contentPadding General content padding
 * @property adaptiveFormHeight Height for adaptive forms
 * @property gridCols Number of columns in grid layouts
 * @property productImageSize Size for product images
 * @property onBoardingImageSize Size for onboarding images
 * @property badgePadding Padding for badges
 * @property titleTextStyle Text style for titles
 * @property appHeadLineTextStyle Text style for main headlines
 * @property appSubHeadLineTextStyle Text style for sub-headlines
 * @property subtitleTextStyle Text style for subtitles
 * @property carouselPageSpacing Spacing for carousel pages
 * @property horizontalAlignment Default horizontal alignment
 * @property orDividerPadding Padding for "OR" dividers
 * @property contentVerticalPadding Vertical padding for content
 * @property baseVerticalPadding Base vertical padding
 * @property categoryPadding Padding for category containers
 * @property profileCardPadding Padding for profile cards
 * @property productSummaryImagePadding Padding for product summary images
 * @property paymentOptionImagePadding Padding for payment option images
 * @property smallCarouselImageWidth Width for small carousel images
 * @property smallCarouselImageHeight Height for small carousel images
 * @property backButtonOffsetPadding Offset for back button
 * @property carouselImageHeight Height for carousel images
 * @property carouselImageWidth Width for carousel images
 * @property adaptiveHeight Adaptive height for components
 * @property listRightPadding Right padding for lists
 * @property productCardHeight Height for product cards
 * @property listCardPadding Padding for list cards
 * @property adaptiveProfileVerticalSpacer Vertical spacer for profiles
 * @property adaptiveWidthModifier Modifier for adaptive width
 * @property adaptiveFormWidthModifier Modifier for adaptive form width
 * @property horizontalArrangementStyle Default horizontal arrangement
 */
@Immutable // Marked as immutable for performance optimizations with CompositionLocal
data class WindowSizeAppConstants(
    val priceTextStyle: TextStyle,
    val labelTextStyle: TextStyle,
    val bodyTextStyle: TextStyle,
    val iconPadding: Dp,
    val cardHeight: Dp,
    val logoPadding:  DpSize,
    val listImagePadding: DpSize,
    val appBarPadding: Dp,
    val contentPadding: Dp,
    val adaptiveFormHeight: Dp,
    val gridCols: Int,
    val productImageSize: DpSize,
    val onBoardingImageSize: DpSize,
    val badgePadding: DpSize,
    val titleTextStyle: TextStyle,
    val appHeadLineTextStyle: TextStyle,
    val appSubHeadLineTextStyle: TextStyle,
    val subtitleTextStyle: TextStyle,
    val carouselPageSpacing: Dp,
    val horizontalAlignment: Alignment.Horizontal,
    val orDividerPadding: Dp,
    val contentVerticalPadding: Dp,
    val baseVerticalPadding: Dp,
    val categoryPadding: DpSize,
    val profileCardPadding: DpSize,
    val productSummaryImagePadding: DpSize,
    val paymentOptionImagePadding: DpSize,
    val smallCarouselImageWidth: Dp,
    val smallCarouselImageHeight: Dp,
    val backButtonOffsetPadding: Dp,
    val carouselImageHeight:Dp,
    val carouselImageWidth: Dp,
    val adaptiveHeight: Dp,
    val listRightPadding: Dp,
    val productCardHeight: Dp,
    val  listCardPadding: Dp,
    val adaptiveProfileVerticalSpacer: Dp,
    val adaptiveWidthModifier: Modifier,
    val  adaptiveFormWidthModifier: Modifier,
    val horizontalArrangementStyle: Arrangement.Horizontal,
)

/**
 * LocalWindowSizeConstant - CompositionLocal for providing [WindowSizeAppConstants].
 *
 * This CompositionLocal is used to provide adaptive UI constants throughout the app.
 * It is initialized with default values and should be overridden at the app level.
 */
val LocalWindowSizeConstant = staticCompositionLocalOf {
    WindowSizeAppConstants(
        priceTextStyle = TextStyle(),
        cardHeight = 0.dp,
        logoPadding = DpSize(0.dp, 0.dp),
        appBarPadding = 0.dp,
        contentVerticalPadding = 0.dp,
        listImagePadding = DpSize(0.dp, 0.dp),
        profileCardPadding = DpSize(0.dp, 0.dp),
        contentPadding = 0.dp,
        adaptiveFormHeight = 0.dp,
        productCardHeight = 0.dp,
        backButtonOffsetPadding = 0.dp,
        baseVerticalPadding = 0.dp,
        titleTextStyle = TextStyle(),
        subtitleTextStyle = TextStyle(),
        carouselPageSpacing = 0.0.dp,
        badgePadding = DpSize(0.dp, 0.dp),
        onBoardingImageSize = DpSize(0.dp, 0.dp),
        horizontalAlignment = Alignment.Start,
        horizontalArrangementStyle = Arrangement.SpaceBetween,
        iconPadding = 24.dp,
        labelTextStyle = TextStyle(),
        bodyTextStyle = TextStyle(),
        categoryPadding = DpSize(0.dp, 0.dp),
        productImageSize = DpSize(0.dp, 0.dp),
        paymentOptionImagePadding = DpSize(0.dp, 0.dp),
        appHeadLineTextStyle = TextStyle(),
        appSubHeadLineTextStyle = TextStyle(),
        orDividerPadding = 0.dp,
        carouselImageWidth = 0.dp,
        carouselImageHeight = 0.dp,
        smallCarouselImageWidth = 0.dp,
        smallCarouselImageHeight = 0.dp,
        listRightPadding = 0.dp,
        adaptiveHeight = 0.dp,
        gridCols = 0,
        listCardPadding = 0.dp,
        adaptiveWidthModifier = Modifier,
        adaptiveFormWidthModifier = Modifier,
        adaptiveProfileVerticalSpacer = 0.dp,
        productSummaryImagePadding = DpSize(0.dp, 0.dp),
    )
}

