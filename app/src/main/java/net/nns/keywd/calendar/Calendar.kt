package net.nns.keywd.calendar

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.nns.keywd.Greeting

@Composable
fun Calendar() {
    Scaffold {
        Greeting(name = "Calendar", modifier = Modifier.padding(it))
    }
}