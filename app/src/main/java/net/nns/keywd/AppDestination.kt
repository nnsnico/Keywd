package net.nns.keywd

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

internal sealed class Screen(val route: String) {
    object List : Screen("list")
    object Calendar : Screen("calender")
}

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.List.route) {
        composable(Screen.List.route) { net.nns.keywd.list.List() }
        composable(Screen.Calendar.route) { net.nns.keywd.calendar.Calendar() }
    }
}