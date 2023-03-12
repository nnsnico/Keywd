package net.nns.keywd.ui.screen.adddiary

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.nns.keywd.Greeting

@Composable
fun AddDiary() {
    Scaffold {
        Greeting(name = "Diary", modifier = Modifier.padding(it))
    }
}