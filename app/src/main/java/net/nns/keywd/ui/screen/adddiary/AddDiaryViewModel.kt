package net.nns.keywd.ui.screen.adddiary

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import arrow.core.continuations.either
import arrow.core.none
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import net.nns.keywd.model.Diary
import net.nns.keywd.model.Title
import net.nns.keywd.model.repository.DiaryRepository
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class AddDiaryViewModel @Inject constructor(
    private val repository: DiaryRepository,
) : ViewModel() {
    private val _addResult: MutableStateFlow<AddResult> = MutableStateFlow(AddResult.Initial)
    val addResult: StateFlow<AddResult> = _addResult.asStateFlow()

    fun addDiary(content: String) {
        viewModelScope.launch {
            // FIXME: Use LocalDate
            val result = either {
                val title = Title.fromDate(Calendar.getInstance().time).bind()
                val diary = Diary(
                    id = none(),
                    title = title,
                    content = content,
                )
                repository.addDiary(diary).bind()
            }

            Log.d("AddDiaryViewModel", result.toString())
            when (result) {
                is Either.Right -> _addResult.value = AddResult.Success
                is Either.Left -> _addResult.value = AddResult.Error(result.value.toString())
            }
        }
    }

    sealed class AddResult {
        object Initial : AddResult()
        object Success : AddResult()
        data class Error(val message: String) : AddResult()
    }
}
