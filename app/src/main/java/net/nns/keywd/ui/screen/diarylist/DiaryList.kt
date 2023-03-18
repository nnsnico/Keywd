package net.nns.keywd.ui.screen.diarylist

import androidx.compose.foundation.layout.padding
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NoteAdd
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.nns.keywd.Greeting
import net.nns.keywd.ui.rememberAppState

@Composable
fun DiaryList(modifier: Modifier = Modifier, onClickAddDiary: () -> Unit) {
    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(onClick = onClickAddDiary) {
                Icon(Icons.Filled.NoteAdd, contentDescription = "Add a diary")
            }
        },
    ) {
        Greeting(name = "DiaryList", modifier = Modifier.padding(it))
    }
}