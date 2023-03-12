package net.nns.keywd.ui.screen.diarylist

import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NoteAdd
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.nns.keywd.Greeting

@Composable
fun DiaryList(onClickAddDiary: () -> Unit) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onClickAddDiary) {
                Icon(Icons.Filled.NoteAdd, contentDescription = "Add a diary")
            }
        },
    ) {
        Greeting(name = "DiaryList", modifier = Modifier.padding(it))
    }
}