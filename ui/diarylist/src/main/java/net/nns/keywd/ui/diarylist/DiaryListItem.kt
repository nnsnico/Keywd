package net.nns.keywd.ui.diarylist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import arrow.core.Option
import arrow.core.continuations.either
import arrow.core.nonEmptyListOf
import arrow.core.some
import arrow.core.toNonEmptyListOrNull
import kotlinx.coroutines.runBlocking
import net.nns.keywd.core.NonEmptyString
import net.nns.keywd.core.ext.NonEmptyList.traverse
import net.nns.keywd.model.Diary
import net.nns.keywd.model.Keyword
import net.nns.keywd.model.Title
import net.nns.keywd.ui.core.annotation.MultiThemePreviews
import net.nns.keywd.ui.core.components.KeywordChip
import net.nns.keywd.ui.core.theme.KeywdTheme
import net.nns.keywd.ui.core.theme.Shapes

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DiaryListItem(
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
                style = MaterialTheme.typography.headlineSmall,
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

@MultiThemePreviews
@Composable
private fun DiaryListItemPreview(
    @PreviewParameter(DiaryProvider::class) diary: Diary,
) {
    KeywdTheme {
        DiaryListItem(diary = diary)
    }
}

private class DiaryProvider : PreviewParameterProvider<Diary?> {
    override val values: Sequence<Diary?>
        get() = runBlocking {
            sequenceOf(
                either {
                    val title = Title.fromString("2023-06-01").bind()
                    val keywords = nonEmptyListOf(
                        NonEmptyString.init("hoge"),
                        NonEmptyString.init("fuga"),
                        NonEmptyString.init("piyo"),
                        NonEmptyString.init("foo"),
                        NonEmptyString.init("bar"),
                        NonEmptyString.init("baz"),
                        NonEmptyString.init("FooBarBaz"),
                    ).traverse { it.toKeyword() }.bind {
                        Exception("unexpected error")
                    }

                    Diary(
                        id = 1.some(),
                        title = title,
                        keywords = keywords,
                    )
                }.orNull(),
            )
        }
}

private fun Option<NonEmptyString>.toKeyword(): Option<Keyword> =
    map {
        Keyword(
            id = it.value,
            value = it,
        )
    }
