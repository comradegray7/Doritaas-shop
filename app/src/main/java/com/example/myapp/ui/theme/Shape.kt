package com.example.myapp.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

@Immutable
data class Shape(
    val extraSmall: RoundedCornerShape = RoundedCornerShape(4.dp),
    val small: RoundedCornerShape = RoundedCornerShape(8.dp),
    val medium: RoundedCornerShape = RoundedCornerShape(10.dp),
    val large: RoundedCornerShape = RoundedCornerShape(24.dp),
    val extraLarge: RoundedCornerShape = RoundedCornerShape(100.dp)
)

val LocalShape = staticCompositionLocalOf { Shape() }

val MaterialTheme.customShape: Shape
    @Composable
    @ReadOnlyComposable
    get() = LocalShape.current
