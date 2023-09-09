package net.nns.keywd.ui.adddiary

import android.content.Context
import android.content.res.Configuration
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import net.nns.keywd.core.NonEmptyString
import net.nns.keywd.core.endsWithBlankOrEnter
import net.nns.keywd.ui.adddiary.AddDiaryViewModel.AddResult
import net.nns.keywd.ui.core.theme.KeywdTheme

@Composable
fun AddDiary(
    modifier: Modifier = Modifier,
    viewModel: AddDiaryViewModel = hiltViewModel(),
    context: Context = LocalContext.current,
    onConfirmDiary: () -> Unit,
) {
    var textFieldContent by rememberSaveable { mutableStateOf("") }
    var chips by rememberSaveable { mutableStateOf(emptyList<String>()) }
    val result by viewModel.addResult.collectAsState()

    when (result) {
        is AddResult.Success -> {
            ConfirmDialog(onConfirmDiary)
        }

        is AddResult.Error -> {
            Log.e("AddDiary", (result as AddResult.Error).message)
            Toast.makeText(context, "Failed to add a diary", Toast.LENGTH_SHORT).show()
        }

        else -> {}
    }

    AddDiaryLayout(
        chips = chips.toImmutableList(),
        onConfirmDiary = {
            viewModel.addDiary(textFieldContent)
        },
        onChangedText = { text ->
            val nonEmptyString = NonEmptyString.init(text)
            textFieldContent = nonEmptyString.fold(
                { text },
                { nes ->
                    if (nes.value.endsWithBlankOrEnter()) {
                        chips = chips + nes.value.trim()
                        ""
                    } else {
                        nes.value
                    }
                },
            )
        },
        onChipClose = { index ->
            chips = chips.filterIndexed { i, _ -> i != index }
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

@Preview
@Composable
private fun ConfirmDialogPreview() {
    KeywdTheme {
        ConfirmDialog {}
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddDiaryLayout(
    chips: ImmutableList<String>,
    onConfirmDiary: () -> Unit,
    onChangedText: (String) -> Unit,
    onChipClose: (Int) -> Unit,
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
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "夢の中の出来事は何でしたか？",
                            style = MaterialTheme.typography.headlineSmall,
                        )
                        Text(
                            text = "思い出せるキーワードを入力してみましょう。",
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }
                },
            )
        },
    ) {
        DiaryMemoryEditor(
            chips = chips,
            modifier = Modifier.padding(it),
            onChangedText = onChangedText,
            onChipClose = onChipClose,
            textFieldContent = textFieldContent,
        )
    }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DiaryMemoryEditor(
    chips: ImmutableList<String>,
    onChangedText: (String) -> Unit,
    onChipClose: (Int) -> Unit,
    modifier: Modifier = Modifier,
    textFieldContent: String = "",
) {
    val focusRequester = remember { FocusRequester() }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(),
    ) {
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }

        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        ) {
            chips.forEachIndexed { index, chip ->
                InputChip(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    label = {
                        Text(text = chip)
                    },
                    onClick = { onChipClose(index) },
                    selected = true,
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Close,
                            modifier = Modifier
                                .width(16.dp)
                                .height(16.dp)
                                .clickable { onChipClose(index) },
                            contentDescription = null,
                            tint = contentColorFor(backgroundColor = MaterialTheme.colorScheme.tertiaryContainer),
                        )
                    },
                )
            }

            BasicTextField(
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .widthIn(min = 80.dp)
                    .padding(4.dp)
                    .align(Alignment.CenterVertically),
                textStyle = MaterialTheme.typography.bodyLarge.copy(
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

@Preview(showSystemUi = false)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showSystemUi = false)
@Composable
private fun AddDiaryPreview() {
    KeywdTheme {
        AddDiaryLayout(
            chips = listOf("hoge", "fuga", "piyo").toImmutableList(),
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
            chips = emptyList<String>().toImmutableList(),
            onConfirmDiary = {},
            onChangedText = {},
            onChipClose = {},
            textFieldContent = "hoge",
        )
    }
}
