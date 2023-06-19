package net.nns.keywd.ui.screen.diarylist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.getOrElse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import net.nns.keywd.model.Diary
import net.nns.keywd.model.repository.DiaryRepository
import javax.inject.Inject

@HiltViewModel
class DiaryListViewModel @Inject constructor(
    private val repository: DiaryRepository,
) : ViewModel() {
    private val _diaryList: MutableStateFlow<List<Diary>> = MutableStateFlow(listOf())
    val diaryList = _diaryList.asStateFlow()

    init {
        viewModelScope.launch {
            _diaryList.value = repository.getSavedDiaries().getOrElse { emptyList() }
        }
    }
}
