package net.nns.keywd.ui.screen.adddiary

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AddDiaryViewModel : ViewModel() {
    private val _speechContent = MutableStateFlow("")
    val speechContent = _speechContent.asStateFlow()

    fun setSpeechContent(text: String) {
        _speechContent.value = text
    }
}
