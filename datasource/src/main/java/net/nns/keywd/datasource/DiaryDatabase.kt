package net.nns.keywd.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import net.nns.keywd.datasource.dao.DiaryDao
import net.nns.keywd.datasource.entity.DiaryEntity

@Database(entities = [DiaryEntity::class], version = 1)
abstract class DiaryDatabase : RoomDatabase() {
    abstract fun diaryDao(): DiaryDao
}
