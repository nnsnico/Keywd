package net.nns.keywd

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import net.nns.keywd.calendar.Calendar
import net.nns.keywd.list.List

internal sealed class Screen(val route: String) {
    object List : Screen("List")
    object Calendar : Screen("Calender")
}

@Composable
fun AppNavigation(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(modifier = modifier, navController = navController, startDestination = Screen.List.route) {
        composable(Screen.List.route) { List() }
        composable(Screen.Calendar.route) { Calendar() }
    }
}