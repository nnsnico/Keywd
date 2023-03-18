package net.nns.keywd.ui.screen.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import net.nns.keywd.ui.AppNavigation
import net.nns.keywd.ui.AppState
import net.nns.keywd.ui.Screen
import net.nns.keywd.ui.TabSelector
import net.nns.keywd.ui.rememberAppState
import net.nns.keywd.ui.theme.KeywdTheme

@Composable
fun Home(modifier: Modifier = Modifier) {
    val appState = rememberAppState()

    Scaffold(
        modifier = modifier,
        bottomBar = {
            if (appState.shouldShowBottomBar) {
                HomeTabNavigation(
                    appState = appState,
                    selector = appState::navigateToBottomBar,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        },
    ) {
        AppNavigation(
            navController = appState.navController,
            onClickAddDiary = { appState.navigateToScreen(Screen.AddDiary) },
            modifier = Modifier.padding(it),
        )
    }
}

@Preview(showSystemUi = true)
@Preview(name = "Dark Mode", uiMode = UI_MODE_NIGHT_YES, showSystemUi = true)
@Composable
private fun HomePreview() {
    KeywdTheme {
        Home()
    }
}

@Composable
private fun HomeTabNavigation(
    appState: AppState,
    selector: TabSelector,
    modifier: Modifier = Modifier,
) {
    BottomNavigation(modifier = modifier) {
        Screen.Home.tabs.forEach { tab ->
            BottomNavigationItem(
                icon = { Icon(tab.icon, contentDescription = null) },
                label = { Text(text = tab.name) },
                selected = appState.currentDestination?.hierarchy?.any { it.route == tab.route } == true,
                onClick = { selector.select(tab) },
            )
        }
    }
}