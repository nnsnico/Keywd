package net.nns.keywd.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarViewMonth
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.NavHost
import net.nns.keywd.ui.screen.adddiary.addDiaryGraph
import net.nns.keywd.ui.screen.adddiary.navigateAddDiary
import net.nns.keywd.ui.screen.home.tab.calendar.calendarGraph
import net.nns.keywd.ui.screen.home.tab.diarylist.diaryListGraph

sealed interface Tab {
    val name: String
    val route: String
    val icon: ImageVector
}

sealed interface HasNestedNavigation {
    val routePattern: String
}

sealed class Screen(val name: String, val route: String) {
    object Home : Screen("Home", "home") {
        val tabs = listOf<Tab>(DiaryList, Calendar)
    }

    object DiaryList : Screen("List", "list"), Tab, HasNestedNavigation {
        override val icon: ImageVector = Icons.Filled.List
        override val routePattern: String = "list_graph"
    }

    object Calendar : Screen("Calendar", "calender"), Tab {
        override val icon: ImageVector = Icons.Filled.CalendarViewMonth
    }

    object AddDiary : Screen("Diary", "diary")

    companion object {
        const val RESULT_KEY_IS_ADDED_DIARY: String = "is_added_diary"
    }
}

@Composable
fun AppNavigation(
    appState: AppState,
    modifier: Modifier = Modifier,
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = Screen.DiaryList.routePattern,
        modifier = modifier,
    ) {
        diaryListGraph(
            onClickFab = navController::navigateAddDiary,
        ) {
            addDiaryGraph(
                onConfirmDialog = {
                    appState.saveAddDiaryResult(true)
                    navController.popBackStack()
                },
            )
        }
        calendarGraph()
    }
}
