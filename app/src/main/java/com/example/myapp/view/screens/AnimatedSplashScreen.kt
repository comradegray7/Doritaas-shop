package com.example.myapp.view.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
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
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.myapp.R
import com.example.myapp.ui.theme.LocalWindowSizeConstant
import com.example.myapp.ui.theme.colors
import com.example.myapp.ui.theme.constant
import com.example.myapp.ui.theme.customSpacing
import com.example.myapp.view.components.CustomImageContainer
import com.example.myapp.view.components.CustomLazyColumn
import com.example.myapp.view.components.CustomLazyRow
import com.example.myapp.view.components.CustomRow
import com.example.myapp.view.components.CustomScaffoldContainer
import com.example.myapp.view.components.CustomSpacer
import com.example.myapp.view.components.FormContainer
import com.example.myapp.view.components.HeadlineWidget
import com.example.myapp.view.components.Logo
import com.example.myapp.view.components.OrDivider
import com.example.myapp.view.components.PaddedSection
import com.example.myapp.view.components.ProductDescriptionShimmer
import com.example.myapp.view.components.custom.buttons.CustomButton
import com.example.myapp.view.components.custom.buttons.CustomOutlinedButton
import com.example.myapp.view.screens.product.SmallProductImages
import com.example.myapp.view.screens.product.SpecificationRow
import com.example.myapp.view.utils.ButtonIcon
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * AnimatedSplashScreen - Composable for displaying an animated splash screen.
 *
 * This composable provides a visually engaging splash experience using animations:
 * - The app logo scales and fades in with a breathing effect.
 * - The app name text fades in after the logo.
 * - A circular progress indicator appears last.
 * - After a set duration, [onSplashFinished] is called to transition to the main app.
 *
 * @param onSplashFinished Callback invoked when the splash animation completes.
 */
@Composable
fun AnimatedSplashScreen(onSplashFinished: () -> Unit) {
    val windowSizeConstant = LocalWindowSizeConstant.current

    // Animation states for logo, text, and progress indicator
    val logoScale = remember { Animatable(0.5f) }
    val logoAlpha = remember { Animatable(0f) }
    val textAlpha = remember { Animatable(0f) }
    val progressAlpha = remember { Animatable(0f) }

    // Gentle infinite breathing animation for the logo
    val infiniteTransition = rememberInfiniteTransition(label = "breathing")
    val breathingScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.08f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = MaterialTheme.constant.tween8,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "breathing"
    )

    // Launch entrance animations and splash duration
    LaunchedEffect(Unit) {
        // Animate logo fade-in
        launch {
            logoAlpha.animateTo(1f, tween(1000, easing = FastOutSlowInEasing))
        }
        // Animate logo scale
        launch {
            logoScale.animateTo(1f, spring(Spring.DampingRatioLowBouncy, Spring.StiffnessLow))
        }
        // Animate text fade-in after a delay
        launch {
            delay(800)
            textAlpha.animateTo(1f, tween(800, easing = FastOutSlowInEasing))
        }
        // Animate progress indicator fade-in after a further delay
        launch {
            delay(1200)
            progressAlpha.animateTo(1f, tween(600, easing = FastOutSlowInEasing))
        }
        // Wait for the full splash duration, then trigger the finish callback
        delay(3000)
        onSplashFinished()
    }

    // Main splash UI layout
    CustomScaffoldContainer(
        showTopBar = false, // No top bar for splash
        showBottomBar = false, // No bottom bar for splash
        showBackArrow = false, // No back arrow for splash
        onRefresh = {
            // Optional: Add refresh logic if needed
            println("Refreshing splash screen...")
        },
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    // Animated logo with scale and alpha
                    Box(
                        modifier = Modifier
                            .size(windowSizeConstant.logoPadding)
                            .scale(logoScale.value * breathingScale)
                            .alpha(logoAlpha.value),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.logo_icon),
                            contentDescription = "App Logo",
                            tint = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.size(windowSizeConstant.logoPadding)
                        )
                    }

                    CustomSpacer()

                    // Animated app name text
                    Text(
                        text = stringResource(R.string.app_name),
                        style = windowSizeConstant.appSubHeadLineTextStyle,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.alpha(textAlpha.value)
                    )

                    CustomSpacer(modifier = Modifier.height(MaterialTheme.customSpacing.baseMedium))

                    // Animated progress indicator
                    Box(modifier = Modifier.alpha(progressAlpha.value)) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.primary,
                            strokeWidth = 4.dp,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
            }
        }
    )
}
 