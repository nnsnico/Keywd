package net.nns.keywd.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import net.nns.keywd.ui.adddiary.addDiaryGraph
import net.nns.keywd.ui.adddiary.navigateAddDiary
import net.nns.keywd.ui.calendar.calendarGraph
import net.nns.keywd.ui.core.Screen
import net.nns.keywd.ui.diarylist.diaryListGraph

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
