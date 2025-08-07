package com.example.myapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class Spacing(
    val none: Dp = 0.dp,
    val border: Dp = 1.5.dp,
    val baseSmall: Dp = 0.2.dp,
    val baseSpacer: Dp = 2.dp,
    val spacer: Dp = 2.5.dp,
    val extraSmall: Dp = 4.dp,
    val smaller: Dp = 6.dp,
    val small: Dp = 8.dp,
    val customNormal: Dp = 10.dp,
    val normal: Dp = 12.dp,
    val baseNormal: Dp = 14.dp,
    val medium: Dp = 16.dp,
    val normalLarge: Dp = 18.dp,
    val base: Dp = 20.dp,
    val large: Dp = 24.dp,
    val box: Dp = 28.dp,
    val outlineNormal: Dp = 30.dp,
    val baseMedium: Dp = 32.dp,
    val outlineMedium: Dp = 36.dp,
    val boxMedium: Dp = 40.dp,
    val outline: Dp = 48.dp,
    val baseLarge: Dp = 50.dp,
    val customMedium: Dp = 60.dp,
    val height: Dp = 65.dp,
    val outlineLarge: Dp = 70.dp,
    val mediumLarge: Dp = 80.dp,
    val xxLarge: Dp = 100.dp,
    val boxLarge: Dp = 120.dp,
    val xx: Dp = 150.dp,
    val customXXLarge: Dp = 180.dp,
    val xxxLarge: Dp = 260.dp,
    val drawerLarge: Dp = 240.dp,
    val veryLarge: Dp = 300.dp,
    val extraLarge: Dp = 400.dp
)

val LocalSpacing = staticCompositionLocalOf { Spacing() }

val MaterialTheme.customSpacing: Spacing
    @Composable
    @ReadOnlyComposable
    get() = LocalSpacing.current
