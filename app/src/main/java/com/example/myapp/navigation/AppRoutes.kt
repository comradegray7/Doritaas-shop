package com.example.myapp.navigation

import kotlinx.serialization.Serializable

/**
 * Serializable object for navigation arguments or destinations.
 * This can be extended to pass data between navigation destinations.
 */
@Serializable
object People

/**
 * AppRoutes - Centralized definition of all navigation routes used in the app.
 * 
 * This object contains string constants for each navigation destination,
 * making it easy to manage and reference routes throughout the codebase.
 * 
 * Grouped by feature/section for clarity.
 */
object AppRoutes {

   // Onboarding routing
    const val ON_BOARDING = "on_boarding"

    // Bottom bar destinations
    // const val SHOP = "shop" // (commented out duplicate, kept for reference)
    const val SHOP = "shop"
    const val ALL_PRODUCTS = "allProducts"
    const val WISHLIST = "wishlist"
    const val PROFILE = "profile"
    const val PRODUCT_FILTER = "filter"

    // App-level destinations (from your AppNavigationGraph)
    const val SEARCH = "search"
    const val PRODUCT_DETAIL = "product_detail"
    const val CART = "cart"
    const val CHECK_OUT = "check_out"

    // Forms
    const val SIGN_UP = "sign_up"
    const val SIGN_IN = "sign_in"
    const val EMAIL = "email"
    const val FORGET_PASSWORD = "forget_password"
    const val PASSWORD_RESET_CODE = "password_reset_code"
    const val SET_NEW_PASSWORD = "set_new_password"
}