package net.nns.keywd.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.nns.keywd.ui.core.annotation.MultiThemePreviews
import net.nns.keywd.ui.core.theme.KeywdTheme

// TODO: material3 ライブラリが対応したら移行する
@Composable
fun TabIndicator(
    modifier: Modifier = Modifier,
) {
    Spacer(
        modifier = modifier
            .requiredSize(width = 64.dp, height = 6.dp)
            .offset(y = 3.dp)
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(3.0.dp),
            ),
    )
}

@MultiThemePreviews
@Composable
fun TabIndicatorPreview() {
    val tabs = listOf("hoge", "fuga")
    KeywdTheme {
        Scaffold(
            topBar = {
                TabRow(
                    selectedTabIndex = 0,
                    indicator = {
                        TabIndicator(Modifier.tabIndicatorOffset(it[0]))
                    },
                ) {
                    tabs.forEach {
                        Tab(
                            text = {
                                Text(it)
                            },
                            selected = true,
                            onClick = {},
                        )
                    }
                }
            },
        ) {
            Text(text = "this is preview", modifier = Modifier.padding(it))
        }
    }
}
