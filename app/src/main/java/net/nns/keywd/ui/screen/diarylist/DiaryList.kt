package net.nns.keywd.ui.screen.diarylist

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NoteAdd
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import arrow.core.traverse
import kotlinx.coroutines.runBlocking
import net.nns.keywd.Greeting
import net.nns.keywd.model.Diary
import net.nns.keywd.model.Title

@Composable
fun DiaryListColumn(
    modifier: Modifier = Modifier,
    viewModel: DiaryListViewModel = hiltViewModel(),
    onClickAddDiary: () -> Unit,
) {
    val diaryList = viewModel.diaryList.collectAsState()

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
            DiaryListColumn(diaryList.value)
        }
    }
}

@Composable
private fun DiaryListColumn(
    @SuppressLint("ComposeUnstableCollections") diaryList: List<Diary>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier) {
        items(diaryList) { diary ->
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

@Preview
@Composable
private fun DiaryListColumnPreview(
    @PreviewParameter(DiaryListProvider::class) diaryList: List<Diary>,
) {
    Scaffold {
        DiaryListColumn(
            diaryList = diaryList,
            modifier = Modifier.padding(it),
        )
    }
}

private class DiaryListProvider : PreviewParameterProvider<List<Diary>> {
    override val values: Sequence<List<Diary>>
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
                    Diary(i, t, "hoge".repeat(80))
                }.orEmpty(),
            )
        }
}
