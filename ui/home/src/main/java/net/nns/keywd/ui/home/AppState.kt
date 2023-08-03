package net.nns.keywd.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import net.nns.keywd.ui.adddiary.navigateAddDiary
import net.nns.keywd.ui.calendar.navigateCalendar
import net.nns.keywd.ui.core.Screen
import net.nns.keywd.ui.core.Tab
import net.nns.keywd.ui.diarylist.navigateDiaryList

@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController(),
): AppState {
    return remember(
        navController,
    ) {
        AppState(navController)
    }
}

@Stable
class AppState(
    val navController: NavHostController,
) {
    private val bottomBarTabs = Screen.Home.tabs
    private val bottomBarRoutes = bottomBarTabs.map { it.route }

    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    val shouldShowBottomBar: Boolean
        @Composable get() = currentDestination?.route in bottomBarRoutes

    fun saveAddDiaryResult(value: Boolean) {
        navController.previousBackStackEntry?.savedStateHandle?.set(
            Screen.RESULT_KEY_IS_ADDED_DIARY,
            value,
        )
    }

    fun navigateToBottomBar(tab: Tab) {
        val navOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

        when (tab) {
            Screen.DiaryList -> navController.navigateDiaryList(navOptions)
            Screen.Calendar -> navController.navigateCalendar(navOptions)
        }
    }

    fun navigateAddDiary() {
        navController.navigateAddDiary()
    }
}
