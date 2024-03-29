package net.nns.keywd.ui.adddiary

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import net.nns.keywd.ui.core.Screen

fun NavController.navigateAddDiary() {
    this.navigate(Screen.AddDiary.route) {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.addDiaryGraph(
    onConfirmDialog: () -> Unit,
    onCloseScreen: () -> Unit,
) {
    composable(route = Screen.AddDiary.route) {
        AddDiary(
            onConfirmDiary = onConfirmDialog,
            onCloseScreen = onCloseScreen,
        )
    }
}
