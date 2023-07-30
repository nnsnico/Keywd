package net.nns.keywd.ui.screen.home.tab.calendar

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.nns.keywd.Greeting

@Composable
fun Calendar(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier,
    ) {
        Greeting(name = "Calendar", modifier = Modifier.padding(it))
    }
}
