package net.nns.keywd.ui.adddiary

import android.Manifest
import android.content.Context
import android.speech.SpeechRecognizer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun rememberUiState(
    context: Context,
    speechTextContent: String = remember { "" },
    recognizer: SpeechRecognizer = remember {
        SpeechRecognizer.createSpeechRecognizer(context)
    },
    permissionState: PermissionState = rememberPermissionState(
        permission = Manifest.permission.RECORD_AUDIO,
    ),
): AddDiaryUiState {
    return remember(
        speechTextContent,
        recognizer,
        permissionState,
    ) {
        AddDiaryUiState(
            speechTextContent = speechTextContent,
            recognizer = recognizer,
            recordAudioPermissionState = permissionState,
        )
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Stable
class AddDiaryUiState(
    speechTextContent: String,
    recognizer: SpeechRecognizer,
    val recordAudioPermissionState: PermissionState,
) {
    var speechText by mutableStateOf(speechTextContent)
        private set
    val speechRecognizer = recognizer.apply {
        setRecognitionListener(
            SpeakDiaryContentRecognizer { result ->
                speechText += result
            },
        )
    }
    val permissionStatus by derivedStateOf { recordAudioPermissionState.status }

    fun updateTextContent(text: String) {
        speechText = text
    }
}
