package net.nns.keywd.ui.screen.adddiary

import android.Manifest
import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import net.nns.keywd.ui.theme.KeywdTheme

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AddDiary(
    modifier: Modifier = Modifier,
    viewModel: AddDiaryViewModel = viewModel(),
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
) {
    val speechContent by viewModel.speechContent.collectAsState()
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
        modifier = modifier,
        speechContent = speechContent,
        onChangedText = viewModel::setSpeechContent,
    )
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun AddDiaryLayout(
    status: PermissionStatus,
    modifier: Modifier = Modifier,
    speechContent: String = "",
    onChangedText: (String) -> Unit,
) {
    Scaffold(
        modifier = modifier,
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
fun AddDiaryPreview() {
    val status = PermissionStatus.Granted
    KeywdTheme {
        AddDiaryLayout(
            status,
            onChangedText = {},
            speechContent = "hogehogehogehogehogehogehogehogehogehogehogehogehogehogehogehogehogehoge",
        )
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Preview
@Composable
fun AudioVisualizerPreview() {
    val status = PermissionStatus.Granted
    KeywdTheme {
        AudioVisualizer(status = status)
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Preview
@Composable
fun AudioVisualizerDeniedPreview() {
    val status = PermissionStatus.Denied(false)
    KeywdTheme {
        AudioVisualizer(status = status)
    }
}
