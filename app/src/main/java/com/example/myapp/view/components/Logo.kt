package com.example.myapp.view.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.DpSize
import com.example.myapp.R
import com.example.myapp.ui.theme.LocalWindowSizeConstant
import com.example.myapp.ui.theme.customSpacing
import com.example.myapp.view.utils.ButtonIcon

/**
 * Logo - Composable function for displaying the app logo.
 * 
 * This composable renders the app's logo using the app_icon drawable resource.
 * It uses a consistent size based on the app's custom spacing system and
 * fits the content appropriately within the container.
 * 
 * The logo is displayed with ContentScale.Fit to ensure it maintains
 * its aspect ratio while fitting within the specified dimensions.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Logo(modifier: Modifier = Modifier) {

    val windowSizeConstant = LocalWindowSizeConstant.current

    Icon(
        imageVector = ImageVector.vectorResource(R.drawable.app_logo),
        contentDescription = "App Logo",
        tint = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier.size(windowSizeConstant.logoPadding)
    )
}
