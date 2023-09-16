package net.nns.keywd.datasource.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.nns.keywd.datasource.DiaryDatabase
import net.nns.keywd.datasource.dao.DiaryDao
import net.nns.keywd.datasource.dao.DiaryKeywordDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDiaryDatabase(
        @ApplicationContext context: Context,
    ): DiaryDatabase = Room.databaseBuilder(
        context = context,
        klass = DiaryDatabase::class.java,
        name = "diary",
    ).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideDiaryDao(database: DiaryDatabase): DiaryDao = database.diaryDao()

    @Singleton
    @Provides
    fun provideDiaryKeywordDao(database: DiaryDatabase): DiaryKeywordDao =
        database.diaryKeywordDao()
}
