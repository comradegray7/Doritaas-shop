package com.example.myapp.view.screens.bottom_bar

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myapp.navigation.AppNavigationGraph
import com.example.myapp.navigation.AppRoutes
import com.example.myapp.navigation.Destination
import com.example.myapp.ui.theme.LocalWindowSizeConstant

/**
 * MainScreen - The main entry point for the app's bottom bar navigation.
 * 
 * This composable sets up the navigation controller and determines which screens
 * should display the bottom navigation bar. It delegates the actual layout to
 * BottomNavigationLayout.
 * 
 * The screen manages navigation state and routes users to the appropriate
 * destination based on the current navigation stack.
 */
@Composable
fun MainScreen() {
        val navController = rememberNavController() // Create navigation controller for the app
            val navBackStackEntry by navController.currentBackStackEntryAsState() // Get current back stack entry
            val currentDestination = navBackStackEntry?.destination // Extract current destination

            // Define which screens should show navigation bar
            val navigationScreens = listOf(
                AppRoutes.SHOP, // Main shop screen
                AppRoutes.ALL_PRODUCTS, // All products browsing screen
                AppRoutes.WISHLIST, // User's wishlist screen
                AppRoutes.PROFILE // User profile screen
            )

            // Check if current destination should show bottom navigation
            val shouldShowNavigation = currentDestination?.route in navigationScreens
                    BottomNavigationLayout(
                        navController = navController,
                        currentDestination = currentDestination,
                        shouldShowNavigation = shouldShowNavigation
                    )

        }

/**
 * BottomNavigationLayout - Layout for the bottom navigation bar and main content.
 * 
 * This composable creates the main scaffold with bottom navigation bar and
 * navigation graph content. It handles the display of navigation items and
 * manages navigation state transitions.
 * 
 * @param navController The navigation controller for the app
 * @param currentDestination The current navigation destination
 * @param shouldShowNavigation Whether to display the bottom navigation bar
 */
@Composable
private fun BottomNavigationLayout(
    navController: NavHostController,
    currentDestination: NavDestination?,
    shouldShowNavigation: Boolean = true
) {
    val windowSizeConstant = LocalWindowSizeConstant.current // Get adaptive window size constants

    Scaffold(
        bottomBar = {
            if (shouldShowNavigation) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer, // Use theme container color
                    windowInsets = NavigationBarDefaults.windowInsets // Apply default window insets
                ) {
                    // Iterate through all bottom bar destinations
                    Destination.entries.forEach { destination ->
                        NavigationBarItem(
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.primary, // Primary color for selected state
                                unselectedIconColor = MaterialTheme.colorScheme.outline, // Outline color for unselected state
                                selectedTextColor = MaterialTheme.colorScheme.primary, // Primary color for selected text
                                unselectedTextColor = MaterialTheme.colorScheme.outline, // Outline color for unselected text
                                indicatorColor = MaterialTheme.colorScheme.surfaceVariant // Surface variant for indicator
                            ),
                            icon = {
                                Icon(
                                    destination.icon, // Use destination's icon
                                    contentDescription = destination.contentDescription, // Accessibility description
                                    modifier = Modifier.size(windowSizeConstant.iconPadding) // Adaptive icon size
                                )
                            },
                            label = {
                                Text(
                                    destination.label, // Use destination's label
                                    style = windowSizeConstant.labelTextStyle, // Adaptive label style
                                    fontWeight = FontWeight.Bold // Bold font weight for emphasis
                                )
                            },
                            selected = currentDestination?.hierarchy?.any {
                                it.route == destination.route // Check if current destination matches
                            } == true,
                            onClick = {
                                // Navigate to selected destination with proper back stack management
                                navController.navigate(destination.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true // Save state when popping up
                                    }
                                    launchSingleTop = true // Prevent multiple instances
                                    restoreState = true // Restore state when navigating
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        // Main navigation graph content with proper padding
        AppNavigationGraph(
            navController = navController,
            startDestination = AppRoutes.ON_BOARDING, // Start with onboarding screen
            modifier = Modifier.padding(innerPadding) // Apply scaffold padding
        )
    }
}

