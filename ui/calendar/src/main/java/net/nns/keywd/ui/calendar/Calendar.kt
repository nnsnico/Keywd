package net.nns.keywd.ui.calendar

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Calendar(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier,
    ) {
        Greeting(name = "Calendar", modifier = Modifier.padding(it))
    }
}

@Composable
internal fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(text = "Hello $name!", modifier = modifier)
}
