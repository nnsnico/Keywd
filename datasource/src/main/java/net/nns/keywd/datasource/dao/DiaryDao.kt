package net.nns.keywd.datasource.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import net.nns.keywd.datasource.dto.DiaryEntity
import net.nns.keywd.datasource.dto.DiaryWithKeywordsEntity

@Dao
interface DiaryDao {
    @Transaction
    @Query("SELECT * FROM DiaryEntity")
    suspend fun getAllDiaryContents(): List<DiaryWithKeywordsEntity>

    @Query("SELECT id FROM DiaryEntity where rowId = :rowId limit 1")
    suspend fun getDiaryIdByRowId(rowId: Long): Int

    @Insert
    suspend fun add(diary: DiaryEntity): Long

    @Delete
    suspend fun delete(diary: DiaryEntity)
}
