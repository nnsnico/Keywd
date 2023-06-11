package net.nns.keywd.repository

import arrow.core.Either
import net.nns.keywd.datasource.dao.DiaryDao
import net.nns.keywd.model.repository.DiaryRepository
import net.nns.keywd.model.repository.dto.DiaryEntity

class DefaultDiaryRepository(private val diaryDao: DiaryDao) : DiaryRepository {
    override suspend fun addDiary(entity: DiaryEntity): Either<Throwable, DiaryEntity> =
        Either.catch { diaryDao.add(entity) }.map { entity }


    override suspend fun deleteDiary(entity: DiaryEntity): Either<Throwable, DiaryEntity> =
        Either.catch { diaryDao.delete(entity) }.map { entity }

    // TODO: Make traverse as utility function because it is deprecated
    // @see: https://github.com/arrow-kt/arrow/blob/722a7c003db8c6612e190492cafbbc8f5e659851/arrow-libs/core/arrow-core/src/commonMain/kotlin/arrow/core/Iterable.kt#L299
    override suspend fun getSavedDiaries(): Either<Throwable, List<DiaryEntity>> =
        Either.catch { diaryDao.getAllDiaryContents() }
}
