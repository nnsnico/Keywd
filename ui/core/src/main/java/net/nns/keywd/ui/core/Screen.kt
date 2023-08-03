package net.nns.keywd.ui.core

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarViewMonth
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector

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
