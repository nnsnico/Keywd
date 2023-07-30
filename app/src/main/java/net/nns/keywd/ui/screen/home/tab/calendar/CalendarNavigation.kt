package net.nns.keywd.ui.screen.home.tab.calendar

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import net.nns.keywd.ui.Screen

fun NavController.navigateCalendar(navOptions: NavOptions? = null) {
    this.navigate(
        route = Screen.Calendar.route,
        navOptions = navOptions,
    )
}

internal fun NavGraphBuilder.calendarGraph() {
    composable(Screen.Calendar.route) {
        Calendar()
    }
}
