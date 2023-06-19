package net.nns.keywd.model.repository

import arrow.core.Either
import net.nns.keywd.model.Diary

interface DiaryRepository {
    suspend fun addDiary(diary: Diary): Either<Throwable, Diary>
    suspend fun deleteDiary(diary: Diary): Either<Throwable, Diary>
    suspend fun getSavedDiaries(): Either<Throwable, List<Diary>>
}
