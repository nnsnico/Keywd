package net.nns.keywd.list

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.nns.keywd.Greeting

@Composable
fun List() {
    Scaffold {
        Greeting(name = "List", modifier = Modifier.padding(it))
    }
}