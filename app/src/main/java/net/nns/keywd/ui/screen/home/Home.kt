package net.nns.keywd.ui.screen.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
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
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import net.nns.keywd.ui.AppNavigation
import net.nns.keywd.ui.AppState
import net.nns.keywd.ui.Screen
import net.nns.keywd.ui.Tab
import net.nns.keywd.ui.rememberAppState
import net.nns.keywd.ui.theme.KeywdTheme

@Composable
fun Home(
    modifier: Modifier = Modifier,
    appState: AppState = rememberAppState(),
) {
    Scaffold(
        modifier = modifier,
        bottomBar = {
            if (appState.shouldShowBottomBar) {
                HomeTabNavigation(
                    currentDestination = appState.currentDestination,
                    onSelectTab = appState::navigateToBottomBar,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        },
    ) {
        AppNavigation(
            appState = appState,
            modifier = Modifier.padding(it),
        )
    }
}

@Preview(showSystemUi = true)
@Preview(name = "Dark Mode", uiMode = UI_MODE_NIGHT_YES, showSystemUi = true)
@Composable
private fun HomePreviews() {
    KeywdTheme {
        val appState = rememberAppState()

        Scaffold(
            bottomBar = {
                HomeTabNavigation(
                    currentDestination = null,
                    onSelectTab = appState::navigateToBottomBar,
                    modifier = Modifier.fillMaxWidth(),
                )
            },
        ) {
            Column(modifier = Modifier.padding(it)) {
                AppNavigation(
                    appState = appState,
                )
            }
        }
    }
}

@Composable
private fun HomeTabNavigation(
    currentDestination: NavDestination?,
    onSelectTab: (Tab) -> Unit,
    modifier: Modifier = Modifier,
) {
    BottomNavigation(
        modifier = modifier,
    ) {
        Screen.Home.tabs.forEach { tab ->
            BottomNavigationItem(
                icon = { Icon(tab.icon, contentDescription = null) },
                label = { Text(text = tab.name) },
                selected = currentDestination.isTabDestinationInHierarchy(tab),
                onClick = { onSelectTab(tab) },
            )
        }
    }
}

private fun NavDestination?.isTabDestinationInHierarchy(tab: Tab): Boolean = this?.hierarchy?.any {
    it.route == tab.route
} ?: false
