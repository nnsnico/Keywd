package net.nns.keywd.repository

import arrow.core.Either
import arrow.core.continuations.either
import arrow.core.traverse
import net.nns.keywd.datasource.dao.DiaryDao
import net.nns.keywd.datasource.dto.DiaryEntity
import net.nns.keywd.model.Diary
import net.nns.keywd.model.repository.DiaryRepository

class DefaultDiaryRepository(private val diaryDao: DiaryDao) : DiaryRepository {
    override suspend fun addDiary(diary: Diary): Either<Throwable, Diary> =
        Either.catch { diaryDao.add(DiaryEntity.fromDiary(diary)) }.map { diary }


    override suspend fun deleteDiary(diary: Diary): Either<Throwable, Diary> =
        Either.catch { diaryDao.delete(DiaryEntity.fromDiary(diary)) }.map { diary }

    // TODO: Make traverse as utility function because it is deprecated
    // @see: https://github.com/arrow-kt/arrow/blob/722a7c003db8c6612e190492cafbbc8f5e659851/arrow-libs/core/arrow-core/src/commonMain/kotlin/arrow/core/Iterable.kt#L299
    override suspend fun getSavedDiaries(): Either<Throwable, List<Diary>> = either {
        val entities = Either.catch { diaryDao.getAllDiaryContents() }.bind()
        val diaryList = entities.traverse { it.toDiary() }.bind()
        diaryList
    }

}
