package net.nns.keywd.ui.diarylist

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NoteAdd
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import arrow.core.getOrElse
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.runBlocking
import net.nns.keywd.core.ext.getOrEmpty
import net.nns.keywd.model.Diary
import net.nns.keywd.model.preview.DiaryFixture
import net.nns.keywd.ui.core.Screen
import net.nns.keywd.ui.core.annotation.MultiThemePreviews
import net.nns.keywd.ui.core.ext.zero
import net.nns.keywd.ui.core.theme.KeywdTheme

@Composable
fun DiaryList(
    modifier: Modifier = Modifier,
    savedState: SavedStateHandle? = null,
    viewModel: DiaryListViewModel = hiltViewModel(),
    onClickAddDiary: () -> Unit,
) {
    val diaryList = viewModel.diaryList.collectAsState()

    LaunchedEffect(savedState) {
        if (savedState?.get<Boolean>(Screen.RESULT_KEY_IS_ADDED_DIARY) == true) {
            viewModel.getAllDiary()
        }
        savedState?.remove<Boolean>(Screen.RESULT_KEY_IS_ADDED_DIARY)
    }

    DiaryListLayout(
        modifier = modifier,
        diaryList = diaryList.value.toImmutableList(),
        onClickAddDiary = onClickAddDiary,
    )
}

@Composable
fun DiaryListLayout(
    diaryList: ImmutableList<Diary>,
    modifier: Modifier = Modifier,
    onClickAddDiary: () -> Unit,
) {
    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(onClick = onClickAddDiary) {
                Icon(Icons.Filled.NoteAdd, contentDescription = "Add a diary")
            }
        },
        contentWindowInsets = WindowInsets.zero(),
    ) {
        if (diaryList.isEmpty()) {
            // TODO: Replace Text to indicate that there is no diary
            Greeting(name = "DiaryList", modifier = Modifier.padding(it))
        } else {
            DiaryListColumn(
                diaryList.toImmutableList(),
                contentPadding = it,
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun DiaryListColumn(
    diaryList: ImmutableList<Diary>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    LazyColumn(
        modifier = modifier.consumeWindowInsets(contentPadding),
        contentPadding = contentPadding,
    ) {
        items(
            items = diaryList,
            key = { it.id.getOrElse { -1 } },
        ) { diary ->
            DiaryListItem(diary)
        }
    }
}

@Composable
internal fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(text = "Hello $name!", modifier = modifier.padding(8.dp))
}

@MultiThemePreviews
@Composable
private fun DiaryListLayoutPreview(
    @PreviewParameter(DiaryListProvider::class) diaries: ImmutableList<Diary>,
) {
    KeywdTheme {
        DiaryListLayout(
            diaryList = diaries,
            onClickAddDiary = {},
        )
    }
}

private class DiaryListProvider : PreviewParameterProvider<ImmutableList<Diary>> {
    override val values: Sequence<ImmutableList<Diary>>
        get() = runBlocking {
            sequenceOf(
                DiaryFixture.create().getOrEmpty().toImmutableList(),
                emptyList<Diary>().toImmutableList(),
            )
        }
}
