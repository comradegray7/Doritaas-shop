package com.example.myapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person2
import androidx.compose.material.icons.filled.Store
import androidx.compose.material.icons.filled.Widgets
import androidx.compose.ui.graphics.vector.ImageVector

enum class Destination(
    val route: String,
    val label: String,
    val icon: ImageVector,
    val contentDescription: String
) {
    SHOP(route = AppRoutes.SHOP, label = "Shop", icon = Icons.Filled.Store, contentDescription = "Shop"),
    ALL_PRODUCTS(route = AppRoutes.ALL_PRODUCTS, label = "All Categories", icon = Icons.Filled.Widgets, contentDescription = "AllProducts"),
    WISHLIST(route = AppRoutes.WISHLIST, label = "WishList", icon = Icons.Filled.Favorite, contentDescription = "WishList"),
    PROFILE(route = AppRoutes.PROFILE, label = "Profile", icon = Icons.Filled.Person2, contentDescription = "Profile"),
}