package net.nns.keywd.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarViewMonth
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.NoteAdd
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import net.nns.keywd.AppNavigation
import net.nns.keywd.Screen
import net.nns.keywd.ui.theme.KeywdTheme

@Composable
fun Home() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        bottomBar = {
            HomeBottomNavigation(
                destination = currentDestination,
                onNavigationSelected = { selected ->
                    navController.navigate(selected.route) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO Move diary screen */ }) {
                Icon(Icons.Filled.NoteAdd, contentDescription = "Add a diary")
            }
        },
    ) {
        AppNavigation(navController = navController, modifier = Modifier.padding(it))
    }
}

@Preview(showSystemUi = true)
@Preview(name = "Dark Mode", uiMode = UI_MODE_NIGHT_YES, showSystemUi = true)
@Composable
fun HomePreview() {
    KeywdTheme {
        Home()
    }
}

@Composable
internal fun HomeBottomNavigation(
    destination: NavDestination?,
    onNavigationSelected: (Screen) -> Unit,
    modifier: Modifier,
) {
    BottomNavigation(
        modifier = modifier
    ) {
        HomeNavigationItems.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(item.icon, contentDescription = null) },
                label = { Text(text = item.screen.route) },
                selected = destination?.hierarchy?.any { it.route == item.screen.route } == true,
                onClick = { onNavigationSelected(item.screen) }
            )
        }
    }
}

private class HomeNavigationItem(
    val screen: Screen,
    val icon: ImageVector,
)

private val HomeNavigationItems = listOf(
    HomeNavigationItem(screen = Screen.List, icon = Icons.Filled.List),
    HomeNavigationItem(screen = Screen.Calendar, icon = Icons.Filled.CalendarViewMonth),
)