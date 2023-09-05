package net.nns.keywd.ui.home

import android.content.res.Configuration
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import net.nns.keywd.ui.core.Screen
import net.nns.keywd.ui.core.Tab
import net.nns.keywd.ui.core.theme.KeywdTheme

@Composable
fun Home(
    modifier: Modifier = Modifier,
    appState: AppState = rememberAppState(),
) {
    HomeLayout(
        currentDestination = appState.currentDestination,
        onSelectTab = appState::navigateToBottomBar,
        shouldShowBottomBar = appState.shouldShowBottomBar,
        modifier = modifier,
    ) {
        AppNavigation(
            appState = appState,
            modifier = Modifier.padding(it),
        )
    }
}

@Preview
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun HomeLayoutPreviews() {
    KeywdTheme {
        HomeLayout(
            currentDestination = null,
            shouldShowBottomBar = true,
            onSelectTab = {},
        ) {
            Text(text = "This is preview")
        }
    }
}

@Composable
private fun HomeLayout(
    currentDestination: NavDestination?,
    shouldShowBottomBar: Boolean,
    onSelectTab: (Tab) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        bottomBar = {
            if (shouldShowBottomBar) {
                HomeTabNavigation(
                    currentDestination = currentDestination,
                    onSelectTab = onSelectTab,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        },
    ) {
        content(it)
    }
}

@Composable
private fun HomeTabNavigation(
    currentDestination: NavDestination?,
    onSelectTab: (Tab) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationBar(
        modifier = modifier,
    ) {
        Screen.Home.tabs.forEach { tab ->
            NavigationBarItem(
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
