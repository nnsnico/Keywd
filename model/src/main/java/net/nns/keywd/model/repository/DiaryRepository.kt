package net.nns.keywd.model.repository

import arrow.core.Either
import net.nns.keywd.model.Diary
import net.nns.keywd.model.repository.dto.DiaryEntity

interface DiaryRepository {
    suspend fun addDiary(entity: DiaryEntity): Either<Throwable, Diary>
    suspend fun deleteDiary(entity: DiaryEntity): Either<Throwable, Diary>
    suspend fun getSavedDiaries(): Either<Throwable, List<Diary>>
}
