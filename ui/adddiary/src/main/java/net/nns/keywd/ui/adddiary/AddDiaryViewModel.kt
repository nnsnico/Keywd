package net.nns.keywd.ui.adddiary

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
import net.nns.keywd.core.ext.NonEmptyList.toNonEmptyList
import net.nns.keywd.model.Diary
import net.nns.keywd.model.Keyword
import net.nns.keywd.model.Title
import net.nns.keywd.model.repository.DiaryRepository
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AddDiaryViewModel @Inject constructor(
    private val repository: DiaryRepository,
) : ViewModel() {
    private val _addResult: MutableStateFlow<AddResult> = MutableStateFlow(AddResult.Initial)
    val addResult: StateFlow<AddResult> = _addResult.asStateFlow()

    fun addDiary(keywords: List<Keyword>) {
        viewModelScope.launch {
            val result = either {
                val title = Title.fromDate(LocalDate.now()).bind()
                val nonEmptyKeywords = keywords.toNonEmptyList().bind {
                    IllegalStateException("keyword is empty.")
                }
                val diary = Diary(
                    id = none(),
                    title = title,
                    keywords = nonEmptyKeywords,
                )
                repository.addDiary(diary).bind()
            }

            Log.d("AddDiaryViewModel", result.toString())
            when (result) {
                is Either.Right -> _addResult.emit(AddResult.Success)
                is Either.Left -> _addResult.emit(
                    AddResult.Error(
                        key = UUID.randomUUID(),
                        result.value.toString(),
                    ),
                )
            }
        }
    }

    fun resetResult() {
        viewModelScope.launch {
            _addResult.emit(AddResult.Initial)
        }
    }

    sealed class AddResult {
        object Initial : AddResult()
        object Success : AddResult()

        // need `key` to emit state with same message
        data class Error(val key: UUID, val message: String) : AddResult()
    }
}
