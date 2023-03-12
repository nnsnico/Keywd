package net.nns.keywd.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarViewMonth
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import net.nns.keywd.ui.screen.calendar.Calendar
import net.nns.keywd.ui.screen.adddiary.AddDiary
import net.nns.keywd.ui.screen.diarylist.DiaryList

sealed interface Tab {
    val name: String
    val route: String
    val icon: ImageVector
}

fun interface TabSelector {
    fun select(tab: Tab): Unit
}

sealed class Screen(open val name: String, val route: String) {
    object Home : Screen("Home", "home") {
        val tabs = listOf<Tab>(DiaryList, Calendar)

        object DiaryList : Screen("List", "home/list"), Tab {
            override val icon: ImageVector = Icons.Filled.List
        }

        object Calendar : Screen("Calendar", "home/calender"), Tab {
            override val icon: ImageVector = Icons.Filled.CalendarViewMonth
        }
    }

    object AddDiary : Screen("Diary", "diary")
}

@Composable
fun AppNavigation(
    navController: NavHostController,
    onClickAddDiary: () -> Unit,
    modifier: Modifier = Modifier
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        navigation(
            route = Screen.Home.route,
            startDestination = Screen.Home.DiaryList.route,
        ) {
            composable(Screen.Home.DiaryList.route) {
                DiaryList(onClickAddDiary = onClickAddDiary)
            }
            composable(Screen.Home.Calendar.route) { Calendar() }
        }
        composable(Screen.AddDiary.route) { AddDiary() }
    }
}