package net.nns.keywd.datasource.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import net.nns.keywd.model.repository.dto.DiaryEntity

@Dao
interface DiaryDao {
    @Query("SELECT * FROM DiaryEntity")
    fun getAllDiaryContents(): List<DiaryEntity>

    @Insert
    fun add(diary: DiaryEntity)

    @Delete
    fun delete(diary: DiaryEntity)
}
