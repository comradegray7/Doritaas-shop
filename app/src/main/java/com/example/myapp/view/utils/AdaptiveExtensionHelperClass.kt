package com.example.myapp.view.utils

import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass

/**
 * WINDOW SIZE EXTENSION PROPERTIES
 * 
 * These extension properties provide convenient boolean checks for window size classes,
 * making it easier to implement responsive design patterns throughout the app.
 * They follow Material Design 3's window size class system.
 */

// Width-based size class extensions
val WindowSizeClass.isCompact: Boolean
    get() = this.widthSizeClass == WindowWidthSizeClass.Compact // Check if width is compact (phone portrait)

val WindowSizeClass.isMedium: Boolean
    get() = this.widthSizeClass == WindowWidthSizeClass.Medium // Check if width is medium (tablet portrait)

val WindowSizeClass.isExpanded: Boolean
    get() = this.widthSizeClass == WindowWidthSizeClass.Expanded // Check if width is expanded (tablet landscape/desktop)

// Height-based size class extensions
val WindowSizeClass.isHeightCompact: Boolean
    get() = this.heightSizeClass == WindowHeightSizeClass.Compact // Check if height is compact (short screen)

val WindowSizeClass.isHeightMedium: Boolean
    get() = this.heightSizeClass == WindowHeightSizeClass.Medium // Check if height is medium (standard screen)

val WindowSizeClass.isHeightExpanded: Boolean
    get() = this.heightSizeClass == WindowHeightSizeClass.Expanded // Check if height is expanded (tall screen)
