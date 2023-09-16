package net.nns.keywd.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import net.nns.keywd.datasource.dao.DiaryDao
import net.nns.keywd.datasource.dao.DiaryKeywordDao
import net.nns.keywd.datasource.dto.DiaryEntity
import net.nns.keywd.datasource.dto.DiaryKeywordEntity

@Database(
    entities = [
        DiaryEntity::class,
        DiaryKeywordEntity::class,
    ],
    version = 2,
)
abstract class DiaryDatabase : RoomDatabase() {
    abstract fun diaryDao(): DiaryDao
    abstract fun diaryKeywordDao(): DiaryKeywordDao
}
