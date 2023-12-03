package net.nns.keywd.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import kotlinx.coroutines.launch
import net.nns.keywd.ui.calendar.Calendar
import net.nns.keywd.ui.core.Screen
import net.nns.keywd.ui.core.annotation.MultiThemePreviews
import net.nns.keywd.ui.core.theme.KeywdTheme

@Composable
fun Home(
    modifier: Modifier = Modifier,
    appState: AppState = rememberAppState(),
) {
    HomeLayout(
        appState.shouldShowTabItems,
        modifier,
    ) {
        AppNavigation(
            appState = appState,
        )
    }
}

@MultiThemePreviews
@Composable
private fun HomeLayoutPreviews() {
    KeywdTheme {
        HomeLayout(
            shouldShowTab = true,
        ) {
            Text(text = "This is preview")
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun HomeLayout(
    shouldShowTab: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { Screen.Home.tabs.size },
    )
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            if (shouldShowTab) {
                TopAppBar(
                    title = {
                        Text(text = "Keywd")
                    },
                    scrollBehavior = scrollBehavior,
                    colors = TopAppBarDefaults.topAppBarColors(
                        scrolledContainerColor = MaterialTheme.colorScheme.surface,
                    ),
                )
            }
        },
        contentWindowInsets = WindowInsets.safeDrawing,
    ) { padding ->
        if (shouldShowTab) {
            Column(modifier = Modifier.padding(padding)) {
                TabRow(
                    modifier = Modifier.fillMaxWidth(),
                    selectedTabIndex = pagerState.currentPage,
                    indicator = { tabPositions ->
                        // TODO: material3 ライブラリが対応したら移行する
                        TabIndicator(
                            modifier = Modifier.tabIndicatorOffset(
                                tabPositions[pagerState.currentPage],
                            ),
                        )
                    },
                ) {
                    Screen.Home.tabs.forEachIndexed { index, tab ->
                        Tab(
                            text = {
                                Text(text = tab.displayName)
                            },
                            icon = {
                                Icon(imageVector = tab.icon, contentDescription = null)
                            },
                            selected = pagerState.currentPage == index,
                            unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            onClick = {
                                coroutineScope.launch {
                                    pagerState.scrollToPage(page = index)
                                }
                            },
                        )
                    }
                }
                HorizontalPager(state = pagerState) { index ->
                    when (index) {
                        0 -> content()
                        1 -> Calendar()
                    }
                }
            }
        } else {
            content()
        }
    }
}
