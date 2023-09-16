package net.nns.keywd.repository.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.nns.keywd.datasource.dao.DiaryDao
import net.nns.keywd.datasource.dao.DiaryKeywordDao
import net.nns.keywd.model.repository.DiaryRepository
import net.nns.keywd.repository.DefaultDiaryRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideDiaryRepository(
        diaryDao: DiaryDao,
        diaryKeywordDao: DiaryKeywordDao,
    ): DiaryRepository = DefaultDiaryRepository(
        diaryDao = diaryDao,
        diaryKeywordDao = diaryKeywordDao,
    )
}
