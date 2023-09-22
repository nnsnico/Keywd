package net.nns.keywd.repository

import arrow.core.Either
import arrow.core.continuations.either
import net.nns.keywd.core.ext.traverse
import net.nns.keywd.datasource.dao.DiaryDao
import net.nns.keywd.datasource.dao.DiaryKeywordDao
import net.nns.keywd.datasource.dto.DiaryEntity
import net.nns.keywd.datasource.dto.DiaryKeywordEntity
import net.nns.keywd.model.Diary
import net.nns.keywd.model.repository.DiaryRepository

class DefaultDiaryRepository(
    private val diaryDao: DiaryDao,
    private val diaryKeywordDao: DiaryKeywordDao,
) : DiaryRepository {
    override suspend fun addDiary(diary: Diary): Either<Throwable, Unit> =
        Either.catch {
            val rowId = diaryDao.add(DiaryEntity.fromDiary(diary))
            val diaryId = diaryDao.getDiaryIdByRowId(rowId)
            diaryKeywordDao.add(DiaryKeywordEntity.create(diaryId, diary.keywords))
        }

    override suspend fun deleteDiary(diary: Diary): Either<Throwable, Unit> =
        Either.catch { diaryDao.delete(DiaryEntity.fromDiary(diary)) }

    override suspend fun deleteKeyword(diary: Diary): Either<Throwable, Unit> =
        Either.catch {
            diaryKeywordDao.delete(
                *(DiaryKeywordEntity.fromDiary(diary).toTypedArray()),
            )
        }

    override suspend fun getSavedDiaries(): Either<Throwable, List<Diary>> = either {
        val entities = Either.catch { diaryDao.getAllDiaryContents() }.bind()
        val diaryList = entities.traverse { it.toDiary() }.bind()
        diaryList
    }
}
