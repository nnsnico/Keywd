package net.nns.keywd.ui.adddiary

import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.SpeechRecognizer

class SpeakDiaryContentRecognizer(val onSpeakAnything: (String) -> Unit) : RecognitionListener {
    override fun onReadyForSpeech(params: Bundle?) = Unit
    override fun onBeginningOfSpeech() = Unit
    override fun onRmsChanged(rmsdB: Float) = Unit
    override fun onBufferReceived(buffer: ByteArray?) = Unit
    override fun onEndOfSpeech() = Unit
    override fun onError(error: Int) = Unit
    override fun onResults(results: Bundle?) = Unit
    override fun onPartialResults(partialResults: Bundle?) {
        onSpeakAnything(
            partialResults
                ?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                ?.joinToString(" ")
                .orEmpty(),
        )
    }

    override fun onEvent(eventType: Int, params: Bundle?) = Unit
}
