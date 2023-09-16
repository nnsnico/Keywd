package net.nns.keywd.datasource.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import net.nns.keywd.datasource.dto.DiaryKeywordEntity

@Dao
interface DiaryKeywordDao {
    @Insert
    suspend fun add(keywords: List<DiaryKeywordEntity>)

    @Delete
    suspend fun delete(vararg keyword: DiaryKeywordEntity)
}
