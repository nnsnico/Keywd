package net.nns.keywd.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController(),
) = remember(navController) {
    AppState(navController)
}

@Stable
class AppState(
    val navController: NavHostController,
) {
    val bottomBarTabs = Screen.Home.tabs
    private val bottomBarRoutes = bottomBarTabs.map { it.route }

    val shouldShowBottomBar: Boolean
        @Composable get() = navController
            .currentBackStackEntryAsState()
            .value
            ?.destination
            ?.route in bottomBarRoutes

    val currentDestination: NavDestination?
        get() = navController.currentDestination

    fun navigateToBottomBar(tab: Tab) {
        if (tab.route != currentDestination?.route) {
            navController.navigate(tab.route) {
                launchSingleTop = true
                restoreState = true
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
            }
        }
    }

    fun navigateToScreen(screen: Screen) {
        navController.navigate(screen.route)
    }
}
