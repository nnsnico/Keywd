package net.nns.keywd.ui.screen.adddiary

import android.Manifest
import android.content.res.Configuration
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import net.nns.keywd.ui.screen.adddiary.AddDiaryViewModel.AddResult
import net.nns.keywd.ui.theme.KeywdTheme

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AddDiary(
    onConfirmDiary: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AddDiaryViewModel = hiltViewModel(),
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
) {
    var speechContent by rememberSaveable { mutableStateOf("") }
    val result by viewModel.addResult.collectAsState()

    val permissionState = rememberPermissionState(permission = Manifest.permission.RECORD_AUDIO)

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_CREATE) {
                // check permission for the first time
                if (!permissionState.status.isGranted) {
                    permissionState.launchPermissionRequest()
                }
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    AddDiaryLayout(
        status = permissionState.status,
        onConfirmDiary = { viewModel.addDiary(speechContent) },
        onChangedText = { text -> speechContent = text },
        modifier = modifier,
        speechContent = speechContent,
    )

    when (result) {
        is AddResult.Success -> {
            ConfirmDialog(onConfirmDiary)
        }

        is AddResult.Error -> {
            val context = LocalContext.current
            Log.e("AddDiary", (result as AddResult.Error).message)
            Toast.makeText(context, "Failed to add a diary", Toast.LENGTH_SHORT).show()
        }

        else -> {}
    }
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
    ConfirmDialog {}
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun AddDiaryLayout(
    status: PermissionStatus,
    onConfirmDiary: () -> Unit,
    onChangedText: (String) -> Unit,
    modifier: Modifier = Modifier,
    speechContent: String = "",
) {
    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(onClick = onConfirmDiary) {
                Icon(Icons.Filled.Add, contentDescription = "Confirm a diary")
            }
        },
    ) {
        Column(
            modifier = Modifier.padding(it),
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f),
                value = speechContent,
                onValueChange = { text -> onChangedText(text) },
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                contentAlignment = Alignment.Center,
            ) {
                AudioVisualizer(status = status)
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun AudioVisualizer(status: PermissionStatus) {
    if (status.isGranted) {
        Text(
            modifier = Modifier.padding(horizontal = 8.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            text = "ここにビジュアライザー(波形)が入ってほしい想定",
        )
    } else {
        Text(
            modifier = Modifier.padding(horizontal = 8.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            text = "音声の権限が許可されていません",
        )
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Preview(showSystemUi = true)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showSystemUi = true)
@Composable
private fun AddDiaryPreview() {
    val status = PermissionStatus.Granted
    KeywdTheme {
        AddDiaryLayout(
            status,
            onConfirmDiary = {},
            onChangedText = {},
            speechContent = "hoge".repeat(80),
        )
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Preview
@Composable
private fun AudioVisualizerPreview() {
    val status = PermissionStatus.Granted
    KeywdTheme {
        AudioVisualizer(status = status)
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Preview
@Composable
private fun AudioVisualizerDeniedPreview() {
    val status = PermissionStatus.Denied(false)
    KeywdTheme {
        AudioVisualizer(status = status)
    }
}
