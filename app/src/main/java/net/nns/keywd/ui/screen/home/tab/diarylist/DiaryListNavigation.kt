package net.nns.keywd.ui.screen.home.tab.diarylist

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import net.nns.keywd.ui.Screen

fun NavController.navigateDiaryList(navOptions: NavOptions? = null) {
    this.navigate(
        route = Screen.DiaryList.route,
        navOptions = navOptions,
    )
}

internal fun NavGraphBuilder.diaryListGraph(
    onClickFab: () -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit,
) {
    navigation(
        route = Screen.DiaryList.routePattern,
        startDestination = Screen.DiaryList.route,
    ) {
        composable(Screen.DiaryList.route) {
            val isAddedDiary = it.savedStateHandle[Screen.RESULT_KEY_IS_ADDED_DIARY] ?: false
            DiaryList(
                isAddedDiary = isAddedDiary,
                onClickAddDiary = onClickFab,
            )
        }
        nestedGraphs()
    }
}
