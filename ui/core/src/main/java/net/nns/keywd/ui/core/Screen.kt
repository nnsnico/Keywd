package net.nns.keywd.ui.core

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarViewMonth
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector

sealed interface Tab {
    val displayName: String
    val route: String
    val icon: ImageVector
}

sealed interface HasNestedNavigation {
    val routePattern: String
}

sealed class Screen(val displayName: String, val route: String) {
    data object Home : Screen("Home", "home") {
        val tabs = listOf<Tab>(DiaryList, Calendar)
    }

    data object DiaryList : Screen("List", "list"), Tab, HasNestedNavigation {
        override val icon: ImageVector = Icons.Filled.List
        override val routePattern: String = "list_graph"
    }

    data object Calendar : Screen("Calendar", "calender"), Tab {
        override val icon: ImageVector = Icons.Filled.CalendarViewMonth
    }

    data object AddDiary : Screen("Diary", "diary")

    companion object {
        const val RESULT_KEY_IS_ADDED_DIARY: String = "is_added_diary"
    }
}
