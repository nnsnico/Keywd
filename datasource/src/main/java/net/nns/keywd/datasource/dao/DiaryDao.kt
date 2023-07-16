package net.nns.keywd.datasource.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import net.nns.keywd.datasource.dto.DiaryEntity

@Dao
interface DiaryDao {
    @Query("SELECT * FROM DiaryEntity")
    suspend fun getAllDiaryContents(): List<DiaryEntity>

    @Insert
    suspend fun add(diary: DiaryEntity)

    @Delete
    suspend fun delete(diary: DiaryEntity)
}
