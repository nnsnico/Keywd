package net.nns.keywd.ui.screen.adddiary

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import net.nns.keywd.model.repository.DiaryRepository
import javax.inject.Inject

@HiltViewModel
class AddDiaryViewModel @Inject constructor(
    private val repository: DiaryRepository,
) : ViewModel() {
    private val _speechContent = MutableStateFlow("")
    val speechContent = _speechContent.asStateFlow()

    fun setSpeechContent(text: String) {
        _speechContent.value = text
    }

    fun addDiary() {
        viewModelScope.launch {
            Log.d("AddDiaryViewModel", repository.getSavedDiaries().toString())
        }
    }
}
