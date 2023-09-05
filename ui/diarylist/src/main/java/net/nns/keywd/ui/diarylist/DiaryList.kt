package net.nns.keywd.ui.diarylist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NoteAdd
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import arrow.core.getOrElse
import arrow.core.some
import arrow.core.traverse
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.runBlocking
import net.nns.keywd.model.Diary
import net.nns.keywd.model.Title
import net.nns.keywd.ui.core.Screen

@Composable
fun DiaryList(
    savedState: SavedStateHandle,
    modifier: Modifier = Modifier,
    viewModel: DiaryListViewModel = hiltViewModel(),
    onClickAddDiary: () -> Unit,
) {
    val diaryList = viewModel.diaryList.collectAsState()

    LaunchedEffect(savedState) {
        if (savedState.get<Boolean>(Screen.RESULT_KEY_IS_ADDED_DIARY) == true) {
            viewModel.getAllDiary()
        }
        savedState.remove<Boolean>(Screen.RESULT_KEY_IS_ADDED_DIARY)
    }

    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(onClick = onClickAddDiary) {
                Icon(Icons.Filled.NoteAdd, contentDescription = "Add a diary")
            }
        },
    ) {
        if (diaryList.value.isEmpty()) {
            // TODO: Replace Text to indicate that there is no diary
            Greeting(name = "DiaryList", modifier = Modifier.padding(it))
        } else {
            DiaryListColumn(diaryList.value.toImmutableList())
        }
    }
}

@Composable
private fun DiaryListColumn(
    diaryList: ImmutableList<Diary>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
    ) {
        items(
            items = diaryList,
            key = { it.id.getOrElse { -1 } },
        ) { diary ->
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .padding(horizontal = 4.dp),
                    text = diary.title.value,
                )
                Text(
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .padding(horizontal = 4.dp),
                    text = diary.content,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Divider()
            }
        }
    }
}

@Composable
internal fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(text = "Hello $name!", modifier = modifier)
}

@Preview
@Composable
private fun DiaryListColumnPreview(
    @PreviewParameter(DiaryListProvider::class) diaries: ImmutableList<Diary>,
) {
    Scaffold {
        DiaryListColumn(
            diaryList = diaries,
            modifier = Modifier.padding(it),
        )
    }
}

private class DiaryListProvider : PreviewParameterProvider<ImmutableList<Diary>> {
    override val values: Sequence<ImmutableList<Diary>>
        get() = runBlocking {
            sequenceOf(
                listOf(
                    Title.fromString("2023-06-01"),
                    Title.fromString("2023-06-02"),
                    Title.fromString("2023-06-03"),
                    Title.fromString("2023-06-04"),
                ).traverse {
                    it.orNull()
                }?.mapIndexed { i, t ->
                    Diary(i.some(), t, "hoge".repeat(80))
                }.orEmpty().toImmutableList(),
            )
        }
}
