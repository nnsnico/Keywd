package net.nns.keywd.ui.core.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.nns.keywd.core.NonEmptyString
import net.nns.keywd.model.Keyword
import net.nns.keywd.ui.core.theme.KeywdTheme
import net.nns.keywd.ui.core.theme.Shapes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KeywordChip(
    keyword: Keyword,
    modifier: Modifier = Modifier,
    onChipClosed: ((String) -> Unit)? = null,
) {
    InputChip(
        modifier = modifier
            .padding(horizontal = 4.dp, vertical = 4.dp)
            .selectable(
                enabled = false,
                selected = false,
                interactionSource = remember {
                    MutableInteractionSource()
                },
                indication = null,
            ) {},
        shape = Shapes.extraLarge,
        label = {
            Text(
                text = keyword.value.value.trim(),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                ),
            )
        },
        onClick = { onChipClosed?.invoke(keyword.id) },
        selected = true,
        trailingIcon = onChipClosed?.let {
            {
                Icon(
                    imageVector = Icons.Outlined.Close,
                    modifier = Modifier
                        .width(16.dp)
                        .height(16.dp)
                        .clip(Shapes.extraLarge)
                        .clickable { onChipClosed?.invoke(keyword.id) },
                    contentDescription = null,
                    tint = contentColorFor(backgroundColor = MaterialTheme.colorScheme.tertiaryContainer),
                )
            }
        },
    )
}

@Preview
@Composable
private fun KeywordChipPreview() {
    KeywdTheme {
        KeywordChip(
            keyword = Keyword(
                id = "1",
                value = NonEmptyString.init("hoge").orNull()!!,
            ),
            onChipClosed = {},
        )
    }
}

@Preview
@Composable
private fun KeywordChipWithoutCallbackPreview() {
    KeywdTheme {
        KeywordChip(
            keyword = Keyword(
                id = "1",
                value = NonEmptyString.init("hoge").orNull()!!,
            ),
        )
    }
}
