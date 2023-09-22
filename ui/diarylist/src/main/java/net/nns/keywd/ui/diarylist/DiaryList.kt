package net.nns.keywd.ui.diarylist

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NoteAdd
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import arrow.core.continuations.option
import arrow.core.getOrElse
import arrow.core.nonEmptyListOf
import arrow.core.sequence
import arrow.core.some
import arrow.core.traverse
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.runBlocking
import net.nns.keywd.core.NonEmptyString
import net.nns.keywd.model.Diary
import net.nns.keywd.model.Keyword
import net.nns.keywd.model.Title
import net.nns.keywd.ui.core.Screen
import net.nns.keywd.ui.core.components.KeywordChip
import net.nns.keywd.ui.core.ext.zero
import net.nns.keywd.ui.core.theme.KeywdTheme
import net.nns.keywd.ui.core.theme.Shapes

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiaryListLayout(
    diaryList: ImmutableList<Diary>,
    modifier: Modifier = Modifier,
    onClickAddDiary: () -> Unit,
) {
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        floatingActionButton = {
            FloatingActionButton(onClick = onClickAddDiary) {
                Icon(Icons.Filled.NoteAdd, contentDescription = "Add a diary")
            }
        },
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(text = "夢日記")
                },
                scrollBehavior = scrollBehavior,
            )
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
            ListItem(diary)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ListItem(
    diary: Diary,
    modifier: Modifier = Modifier,
) {
    ElevatedCard(
        modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = Shapes.extraLarge,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        ) {
            Text(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .padding(horizontal = 4.dp),
                text = diary.title.value.replace("-", "/"),
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            )
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
            ) {
                diary.keywords.forEach {
                    KeywordChip(keyword = it)
                }
            }
        }
    }
}

@Composable
internal fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(text = "Hello $name!", modifier = modifier.padding(8.dp))
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
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

@Preview(showSystemUi = false)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DiaryListLayoutWhenEmptyPreview() {
    KeywdTheme {
        DiaryListLayout(diaryList = emptyList<Diary>().toImmutableList()) {}
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
                ).traverse { titleOrErr ->
                    option {
                        val title = titleOrErr.orNone().bind()
                        val keywords = nonEmptyListOf(
                            NonEmptyString.init("hoge"),
                            NonEmptyString.init("fuga"),
                            NonEmptyString.init("piyo"),
                            NonEmptyString.init("foo"),
                            NonEmptyString.init("bar"),
                            NonEmptyString.init("baz"),
                            NonEmptyString.init("baz"),
                            NonEmptyString.init("baz"),
                        ).sequence().bind()
                        Pair(title, keywords)
                    }.orNull()
                }?.mapIndexed { i, (t, nesList) ->
                    Diary(
                        id = i.some(),
                        title = t,
                        keywords = nesList.map { Keyword(id = "1", value = it) },
                    )
                }.orEmpty().toImmutableList(),
            )
        }
}
