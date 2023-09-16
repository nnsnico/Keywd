package net.nns.keywd.ui.adddiary

import android.content.Context
import android.content.res.Configuration
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import arrow.core.traverse
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.runBlocking
import net.nns.keywd.core.NonEmptyString
import net.nns.keywd.core.endsWithBlankOrEnter
import net.nns.keywd.model.Keyword
import net.nns.keywd.ui.adddiary.AddDiaryViewModel.AddResult
import net.nns.keywd.ui.core.theme.KeywdTheme
import net.nns.keywd.ui.core.theme.Shapes
import java.util.UUID

@Composable
fun AddDiary(
    modifier: Modifier = Modifier,
    viewModel: AddDiaryViewModel = hiltViewModel(),
    context: Context = LocalContext.current,
    onConfirmDiary: () -> Unit,
) {
    var textFieldContent by rememberSaveable { mutableStateOf("") }
    var keywords by rememberSaveable { mutableStateOf(emptyList<Keyword>()) }
    val result by viewModel.addResult.collectAsState()

    when (result) {
        is AddResult.Success -> {
            ConfirmDialog(onConfirmDiary)
        }

        is AddResult.Error -> {
            Log.e("AddDiary", (result as AddResult.Error).message)
            Toast.makeText(context, "キーワードが入力されていません", Toast.LENGTH_SHORT).show()
            viewModel.resetResult()
        }

        else -> {}
    }

    AddDiaryLayout(
        keywords = keywords.toImmutableList(),
        onConfirmDiary = {
            viewModel.addDiary(keywords)
        },
        onChangedText = { text ->
            val nonEmptyString = NonEmptyString.init(text)
            textFieldContent = nonEmptyString.fold(
                { text },
                { nes ->
                    if (nes.value.endsWithBlankOrEnter()) {
                        keywords += Keyword(UUID.randomUUID().toString(), nes)
                        ""
                    } else {
                        nes.value
                    }
                },
            )
        },
        onChipClose = { index ->
            keywords = keywords.filter { v -> v.id != index }
        },
        modifier = modifier,
        textFieldContent = textFieldContent,
    )
}

@Composable
private fun ConfirmDialog(
    onConfirmDiary: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = {},
        title = { Text(text = "追加しました") },
        confirmButton = {
            TextButton(onClick = onConfirmDiary) {
                Text("OK")
            }
        },
    )
}

@Composable
private fun AddDiaryLayout(
    keywords: ImmutableList<Keyword>,
    onConfirmDiary: () -> Unit,
    onChangedText: (String) -> Unit,
    onChipClose: (String) -> Unit,
    modifier: Modifier = Modifier,
    textFieldContent: String = "",
) {
    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(onClick = onConfirmDiary) {
                Icon(Icons.Filled.Add, contentDescription = "Confirm a diary")
            }
        },
        topBar = {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
            ) {
                Spacer(modifier = Modifier.heightIn(min = 96.dp))
                Text(
                    text = "夢の中の出来事は\n何でしたか？",
                    style = MaterialTheme.typography.headlineMedium,
                )
                Text(
                    text = "思い出せるキーワードを入力してみましょう",
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        },
        contentWindowInsets = WindowInsets.safeDrawing,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(it)
                .fillMaxWidth(),
        ) {
            Spacer(modifier = Modifier.heightIn(min = 16.dp))
            DiaryMemoryEditor(
                chips = keywords,
                onChangedText = onChangedText,
                onChipClosed = onChipClose,
                textFieldContent = textFieldContent,
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalComposeUiApi::class)
@Composable
fun DiaryMemoryEditor(
    chips: ImmutableList<Keyword>,
    onChangedText: (String) -> Unit,
    onChipClosed: (String) -> Unit,
    modifier: Modifier = Modifier,
    textFieldContent: String = "",
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(8.dp),
    ) {
        val focusRequester = remember { FocusRequester() }
        val keyboardController = LocalSoftwareKeyboardController.current

        FlowRow(
            modifier = Modifier
                .clip(Shapes.extraLarge)
                .clickable(
                    interactionSource = remember {
                        MutableInteractionSource()
                    },
                    indication = null,
                ) {
                    keyboardController?.show()
                }
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.tertiaryContainer,
                    shape = Shapes.extraLarge,
                )
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(horizontal = 8.dp),
        ) {
            LaunchedEffect(Unit) {
                focusRequester.requestFocus()
            }

            chips.forEach {
                KeywordChip(keyword = it, onChipClosed = onChipClosed)
            }

            BasicTextField(
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .widthIn(min = 80.dp)
                    .padding(horizontal = 4.dp, vertical = 16.dp)
                    .align(Alignment.CenterVertically),
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    fontFamily = FontFamily.Default,
                    color = contentColorFor(MaterialTheme.colorScheme.background),
                ),
                singleLine = true,
                value = textFieldContent,
                cursorBrush = SolidColor(contentColorFor(backgroundColor = MaterialTheme.colorScheme.background)),
                onValueChange = { text -> onChangedText(text) },
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KeywordChip(
    keyword: Keyword,
    onChipClosed: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    InputChip(
        modifier = modifier.padding(horizontal = 4.dp),
        shape = Shapes.extraLarge,
        label = {
            Text(
                text = keyword.value.value.trim(),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                ),
            )
        },
        onClick = { onChipClosed(keyword.id) },
        selected = true,
        trailingIcon = {
            Icon(
                imageVector = Icons.Outlined.Close,
                modifier = Modifier
                    .width(16.dp)
                    .height(16.dp)
                    .clip(Shapes.extraLarge)
                    .clickable { onChipClosed(keyword.id) },
                contentDescription = null,
                tint = contentColorFor(backgroundColor = MaterialTheme.colorScheme.tertiaryContainer),
            )
        },
    )
}

@Preview(showSystemUi = false)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showSystemUi = false)
@Composable
private fun AddDiaryPreview(
    @PreviewParameter(ChipsProvider::class) chips: ImmutableList<Keyword>,
) {
    KeywdTheme {
        AddDiaryLayout(
            keywords = chips,
            onConfirmDiary = {},
            onChangedText = {},
            onChipClose = {},
            textFieldContent = "hoge",
        )
    }
}

@Preview(showSystemUi = false)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showSystemUi = false)
@Composable
private fun AddDiaryPreviewWithoutChip() {
    KeywdTheme {
        AddDiaryLayout(
            keywords = emptyList<Keyword>().toImmutableList(),
            onConfirmDiary = {},
            onChangedText = {},
            onChipClose = {},
            textFieldContent = "hoge",
        )
    }
}

@Preview
@Composable
private fun ConfirmDialogPreview() {
    KeywdTheme {
        ConfirmDialog {}
    }
}

@Preview
@Composable
private fun KeywordChipPreview(
    @PreviewParameter(ChipsProvider::class) chips: ImmutableList<Keyword>,
) {
    KeywdTheme {
        KeywordChip(keyword = chips[0], onChipClosed = {})
    }
}

private class ChipsProvider : PreviewParameterProvider<ImmutableList<Keyword>> {
    override val values: Sequence<ImmutableList<Keyword>>
        get() = runBlocking {
            sequenceOf(
                listOf(
                    NonEmptyString.init("hoge"),
                    NonEmptyString.init("fuga"),
                    NonEmptyString.init("piyo"),
                    NonEmptyString.init("foo"),
                    NonEmptyString.init("bar"),
                    NonEmptyString.init("baz"),
                ).traverse {
                    it.orNull()
                }?.mapIndexed { index, nes ->
                    Keyword(index.toString(), nes)
                }.orEmpty().toImmutableList(),
            )
        }
}
