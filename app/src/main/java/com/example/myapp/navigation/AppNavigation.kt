package com.example.myapp.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapp.navigation.navigation_helper.NavigationAnimations
import com.example.myapp.view.components.CardItemData
import com.example.myapp.view.screens.SearchScreen
import com.example.myapp.view.screens.bottom_bar.AllProductsScreen
import com.example.myapp.view.screens.bottom_bar.ProfileScreen
import com.example.myapp.view.screens.bottom_bar.ShopScreen
import com.example.myapp.view.screens.bottom_bar.WishlistScreen
import com.example.myapp.view.screens.forms.EmailLoginScreen
import com.example.myapp.view.screens.forms.LoginScreen
import com.example.myapp.view.screens.forms.SignUpScreen
import com.example.myapp.view.screens.forms.password.ForgotPasswordScreen
import com.example.myapp.view.screens.forms.password.ResetCodeScreen
import com.example.myapp.view.screens.forms.password.SetPasswordScreen
import com.example.myapp.view.screens.onboarding.OnboardingScreen
import com.example.myapp.view.screens.product.CartScreen
import com.example.myapp.view.screens.product.CheckOutScreen
import com.example.myapp.view.screens.product.ProductDescriptionScreen
import com.example.myapp.view.screens.product.ProductSharedElementScreen
import com.example.myapp.view.screens.product.categories.FilterProducts

/**
 * AppNavigationGraph - Main navigation graph for the entire application.
 * 
 * This composable defines all the navigation routes and their associated screens,
 * including animations and navigation logic. It uses SharedTransitionLayout for
 * smooth transitions between screens and supports both light and dark themes.
 * 
 * The navigation graph is organized into several sections:
 * - Onboarding screens
 * - Bottom bar destinations (main app screens)
 * - Authentication screens
 * - Product-related screens
 * - Search and filter screens
 * 
 * @param modifier Optional modifier to apply to the navigation container
 * @param navController Navigation controller for managing navigation state
 * @param startDestination The initial destination route
 * 
 * Usage:
 * ```
 * AppNavigationGraph(
 *     startDestination = AppRoutes.ON_BOARDING
 * )
 * ```
 */
@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun AppNavigationGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String
) {

    // SharedTransitionLayout enables smooth transitions between screens
    SharedTransitionLayout {

        NavHost(
            navController = navController,
            startDestination = startDestination,
        ) {

            // First screen users see when opening the app
            // ONBOARDING SCREEN
            composable(
                route = AppRoutes.ON_BOARDING,
                enterTransition = {
                    NavigationAnimations.slideInFromLeft() // Slide in from left
                },
                exitTransition = {
                    NavigationAnimations.slideOutToLeft() // Slide out to left
                },
                popEnterTransition = { NavigationAnimations.slideInFromLeft() }, // Back navigation
                popExitTransition = { NavigationAnimations.slideOutToRight() } // Back navigation
            ) {
                OnboardingScreen(
                    onFinished = {
                        // Navigate directly to SHOP when onboarding is complete
                        navController.navigate(AppRoutes.SHOP) {
                            popUpTo(AppRoutes.ON_BOARDING) {
                                inclusive = true // Remove onboarding from back stack
                            }
                            launchSingleTop = true // Prevent multiple instances
                        }
                    }
                )
            }

            // BOTTOM BAR DESTINATIONS
            // Main app screens accessible from the bottom navigation bar

            // Shop Screen - Main shopping interface
            composable(
                route = AppRoutes.SHOP,
                enterTransition = {
                    NavigationAnimations.crossFade() // cross fade

                },
                exitTransition = {
                    NavigationAnimations.slideOutToLeft() // Slide out to left
                },
                popEnterTransition = { NavigationAnimations.slideInFromLeft() }, // Back navigation
                popExitTransition = { NavigationAnimations.slideOutToRight() } // Back navigation
            ) {
                ShopScreen(
                    onSearchClick = { navController.navigate(AppRoutes.SEARCH) }, // Navigate to search
                    onProductClick = { navController.navigate(AppRoutes.PRODUCT_DETAIL) }, // Navigate to product detail
                    onCartClick = { navController.navigate(AppRoutes.CART) }, // Navigate to cart
                    onAllProductsClick = { navController.navigate(AppRoutes.ALL_PRODUCTS) }, // Navigate to all products
                )
            }

            // All Products Screen - Browse all available products
            composable(
                route = AppRoutes.ALL_PRODUCTS,
                enterTransition = {
                    NavigationAnimations.crossFade() // cross fade
                },
                exitTransition = {
                    NavigationAnimations.slideOutToLeft() // Slide out to left
                },
                popEnterTransition = { NavigationAnimations.slideInFromLeft() }, // Back navigation
                popExitTransition = { NavigationAnimations.slideOutToRight() } // Back navigation
            ) {
                AllProductsScreen(
                    onSearchClick = { navController.navigate(AppRoutes.SEARCH) }, // Navigate to search
                    onCartClick = { navController.navigate(AppRoutes.CART) }, // Navigate to cart
                    onCategoryClick = {navController.navigate(AppRoutes.PRODUCT_FILTER)}, // Navigate to product filter
                    onAllProductsClick = { navController.navigate(AppRoutes.PRODUCT_FILTER) }, // Navigate to product filter
                )
            }

            // Wishlist Screen - User's saved items
            composable(
                route = AppRoutes.WISHLIST,
                enterTransition = {
                    NavigationAnimations.crossFade() // cross fade
                },
                exitTransition = {
                    NavigationAnimations.slideOutToLeft() // Slide out to left
                },
                popEnterTransition = { NavigationAnimations.slideInFromLeft() }, // Back navigation
                popExitTransition = { NavigationAnimations.slideOutToRight() } // Back navigation
            ) {
                    WishlistScreen(
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedContentScope = this@composable,
                        onSearchClick = { navController.navigate(AppRoutes.SEARCH) }, // Navigate to search
                        onCartClick = { navController.navigate(AppRoutes.CART) }, // Navigate to cart
                        onItemClick = { item ->
                            // Pass the item data to the details screen
                            val origin = AppRoutes.WISHLIST
                            navController.currentBackStackEntry?.savedStateHandle?.set("selected_item", item)
                            navController.navigate("details/${item.id}?from=$origin")
                        },
                        onAddToCart = { item ->
                            // Handle add to cart
                        },
                        onRemoveFromWishlist = { item ->
                            // Handle remove from wishlist
                        }
                )
            }

            // Profile Screen - User settings and authentication
            composable(
                route = AppRoutes.PROFILE,
                enterTransition = {
                    NavigationAnimations.crossFade() // cross fade
                },
                exitTransition = {
                    NavigationAnimations.slideOutToLeft() // Slide out to left
                },
                popEnterTransition = { NavigationAnimations.slideInFromLeft() }, // Back navigation
                popExitTransition = { NavigationAnimations.slideOutToRight() } // Back navigation
            ) {
                ProfileScreen(
                    onSignInClick = {
                        navController.navigate(AppRoutes.SIGN_IN) // Navigate to login
                    },
                )
            }

            // SEARCH SCREEN
            // Search functionality and results
            composable(
                route = AppRoutes.SEARCH,
                enterTransition = { NavigationAnimations.slideInFromRight() }, // Slide in from right
                exitTransition = { NavigationAnimations.slideOutToLeft() }, // Slide out to left
                popEnterTransition = { NavigationAnimations.slideInFromLeft() }, // Back navigation
                popExitTransition = { NavigationAnimations.slideOutToRight() } // Back navigation
            ) {
                SearchScreen(
                    onBackNavigation = { navController.popBackStack() }, // Go back
                    onResultClick = { result ->
                        navController.previousBackStackEntry
                            ?.savedStateHandle
                            ?.set("search_result", result)
                        navController.popBackStack()
                    }
                )
            }

            // PRODUCT DETAIL SCREEN
            // Detailed view of a single product
            composable(
                route = "${AppRoutes.PRODUCT_DETAIL}/{productId}",
                enterTransition = { NavigationAnimations.slideInFromRight() }, // Slide in from right
                exitTransition = { NavigationAnimations.slideOutToLeft() }, // Slide out to left
                popEnterTransition = { NavigationAnimations.slideInFromLeft() }, // Back navigation
                popExitTransition = { NavigationAnimations.slideOutToRight() } // Back navigation
            ) { backStackEntry ->
                val productId = backStackEntry.arguments?.getString("productId") ?: ""
                ProductDescriptionScreen(
                    onBackNavigation = { navController.popBackStack() }, // Go back
                    onBuyNowClick = { navController.navigate(AppRoutes.CHECK_OUT)}, // Navigate to checkout
                )
            }
            composable(
                route = AppRoutes.PRODUCT_DETAIL,
                enterTransition = { NavigationAnimations.slideInFromRight() }, // Slide in from right
                exitTransition = { NavigationAnimations.slideOutToLeft() }, // Slide out to left
                popEnterTransition = { NavigationAnimations.slideInFromLeft() }, // Back navigation
                popExitTransition = { NavigationAnimations.slideOutToRight() } // Back navigation
            ) {
                ProductDescriptionScreen(
                    //                productId = productId,
                    onBackNavigation = { navController.popBackStack() }, // Go back
                    onBuyNowClick = { navController.navigate(AppRoutes.CHECK_OUT)} // Navigate to checkout
                )
            }

            // CART SCREEN
            // User's shopping cart
            composable(
                route = AppRoutes.CART
            ) {
                CartScreen(
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedContentScope = this@composable,
                    onBackNavigation = { navController.popBackStack() }, // Go back
                    onSearchClick = { navController.navigate(AppRoutes.SEARCH) }, // Navigate to search
                    onItemClick = { item ->
                        // Pass the item data to the details screen
                        val origin = AppRoutes.CART
                        navController.currentBackStackEntry?.savedStateHandle?.set("selected_item", item)
                        navController.navigate("details/${item.id}?from=$origin")
                    },
                    onCheckOutClick = {
                        navController.navigate(AppRoutes.CHECK_OUT) // Navigate to checkout
                    }
                )
            }
          composable(
                route = AppRoutes.PRODUCT_FILTER
            ) {
              FilterProducts(
                  sharedTransitionScope = this@SharedTransitionLayout,
                  animatedContentScope = this@composable,
                  onBackNavigation = { navController.popBackStack() }, // Go back
              )
            }

            // CHECKOUT SCREEN
            // Final step for placing an order
            composable(
                route = AppRoutes.CHECK_OUT,
                enterTransition = { NavigationAnimations.slideInFromBottom() }, // Slide in from bottom
                exitTransition = { NavigationAnimations.slideOutToBottom() }, // Slide out to bottom
                popEnterTransition = { NavigationAnimations.slideInFromLeft() }, // Back navigation
                popExitTransition = { NavigationAnimations.slideOutToRight() } // Back navigation
            ) {
                CheckOutScreen(
                    onBackNavigation = { navController.popBackStack() }, // Go back
                )
            }
            composable(
                route = AppRoutes.SIGN_IN,
                enterTransition = { NavigationAnimations.slideInFromBottom() }, // Slide in from bottom
                exitTransition = { NavigationAnimations.slideOutToBottom() }, // Slide out to bottom
                popEnterTransition = { NavigationAnimations.slideInFromLeft() }, // Back navigation
                popExitTransition = { NavigationAnimations.slideOutToRight() } // Back navigation
            ) {
                LoginScreen(
                    onContinueWithGoogleClick = {}, // No action for now
                    onContinueWithAppleClick = {}, // No action for now
                    onContinueWithEmailClick = { navController.navigate(AppRoutes.EMAIL) }, // Navigate to email login
                    onSignUpClick = { navController.navigate(AppRoutes.SIGN_UP) } // Navigate to sign up
                )
            }
            composable(
                route = AppRoutes.SIGN_UP,
                enterTransition = { NavigationAnimations.slideInFromBottom() }, // Slide in from bottom
                exitTransition = { NavigationAnimations.slideOutToBottom() }, // Slide out to bottom
                popEnterTransition = { NavigationAnimations.slideInFromLeft() }, // Back navigation
                popExitTransition = { NavigationAnimations.slideOutToRight() } // Back navigation
            ) {
                SignUpScreen(
                    onSignInClick = { navController.navigate(AppRoutes.SIGN_IN)} // Navigate to sign in
                )
            }
            composable(
                route = AppRoutes.EMAIL,
                enterTransition = { NavigationAnimations.slideInFromBottom() }, // Slide in from bottom
                exitTransition = { NavigationAnimations.slideOutToBottom() }, // Slide out to bottom
                popEnterTransition = { NavigationAnimations.slideInFromLeft() }, // Back navigation
                popExitTransition = { NavigationAnimations.slideOutToRight() } // Back navigation
            ) {
                EmailLoginScreen(onForgetPasswordClick = { navController.navigate(AppRoutes.FORGET_PASSWORD)}) // Navigate to forgot password
            }
            composable(
                route = AppRoutes.FORGET_PASSWORD,
                enterTransition = { NavigationAnimations.slideInFromBottom() }, // Slide in from bottom
                exitTransition = { NavigationAnimations.slideOutToBottom() }, // Slide out to bottom
                popEnterTransition = { NavigationAnimations.slideInFromLeft() }, // Back navigation
                popExitTransition = { NavigationAnimations.slideOutToRight() } // Back navigation
            ) {
                ForgotPasswordScreen(
                    rememberPasswordClick = { navController.navigate(AppRoutes.SIGN_IN)}, // Navigate to sign in
                    resetPasswordClick = { navController.navigate(AppRoutes.PASSWORD_RESET_CODE)} // Navigate to reset code
                )
            }
            composable(
                route = AppRoutes.SET_NEW_PASSWORD,
                enterTransition = { NavigationAnimations.slideInFromBottom() }, // Slide in from bottom
                exitTransition = { NavigationAnimations.slideOutToBottom() }, // Slide out to bottom
                popEnterTransition = { NavigationAnimations.slideInFromLeft() }, // Back navigation
                popExitTransition = { NavigationAnimations.slideOutToRight() } // Back navigation
            ) {
                SetPasswordScreen(
                    onBackNavigation = { navController.popBackStack() }, // Go back
                    onResetPasswordClick = { navController.navigate(AppRoutes.SIGN_IN) }, // Navigate to sign in
                )
            }
            composable(
                route = AppRoutes.PASSWORD_RESET_CODE,
                enterTransition = { NavigationAnimations.slideInFromBottom() }, // Slide in from bottom
                exitTransition = { NavigationAnimations.slideOutToBottom() }, // Slide out to bottom
                popEnterTransition = { NavigationAnimations.slideInFromLeft() }, // Back navigation
                popExitTransition = { NavigationAnimations.slideOutToRight() } // Back navigation
            ) {
                ResetCodeScreen(
                    resendCode = { /** TO DO **/}, // No action for now
                    onVerifyCodeClick = { navController.navigate(AppRoutes.SET_NEW_PASSWORD)} // Navigate to set new password
                )
            }

            // DETAILS SCREEN (Fallback for shared element transitions)
            // Handles navigation for shared element transitions when item data is not directly available
            composable(
                "details/{itemId}?from={from}",
                arguments = listOf(
                    navArgument("itemId") { type = NavType.StringType },
                    navArgument("from") {
                        type = NavType.StringType
                        defaultValue = "main"
                    }
                ),
            ) { backStackEntry ->
                val itemId = backStackEntry.arguments?.getString("itemId") ?: ""
                val from = backStackEntry.arguments?.getString("from") ?: AppRoutes.SHOP
                val item = navController.previousBackStackEntry?.savedStateHandle?.get<CardItemData>("selected_item")

                if (item != null) {
                    ProductSharedElementScreen(
                        id = itemId,
                        item = item,
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedContentScope = this@composable,
                        onBackPressed = {
                            navController.popBackStack()
                        }
                    )
                } else {
                    // fallback to origin route if item is null
                    LaunchedEffect(Unit) {
                        navController.popBackStack()
                        when (from) {
                            AppRoutes.CART -> navController.navigate(AppRoutes.CART)
                            AppRoutes.WISHLIST -> navController.navigate(AppRoutes.WISHLIST)
                            else -> navController.navigate(AppRoutes.SHOP)
                        }
                    }
                }
            }
        }
    }
}

