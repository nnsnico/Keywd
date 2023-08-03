package net.nns.keywd.ui.diarylist

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import net.nns.keywd.ui.core.Screen

fun NavController.navigateDiaryList(navOptions: NavOptions? = null) {
    this.navigate(
        route = Screen.DiaryList.route,
        navOptions = navOptions,
    )
}

fun NavGraphBuilder.diaryListGraph(
    onClickFab: () -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit,
) {
    navigation(
        route = Screen.DiaryList.routePattern,
        startDestination = Screen.DiaryList.route,
    ) {
        composable(Screen.DiaryList.route) {
            DiaryList(
                savedState = it.savedStateHandle,
                onClickAddDiary = onClickFab,
            )
        }
        nestedGraphs()
    }
}
