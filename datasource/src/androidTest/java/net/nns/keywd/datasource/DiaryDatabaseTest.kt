package net.nns.keywd.datasource

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import net.nns.keywd.datasource.dao.DiaryDao
import net.nns.keywd.datasource.dao.DiaryKeywordDao
import net.nns.keywd.datasource.dto.DiaryEntity
import net.nns.keywd.datasource.dto.DiaryKeywordEntity
import net.nns.keywd.datasource.dto.DiaryWithKeywordsEntity
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class DiaryDatabaseTest {
    private lateinit var diaryDao: DiaryDao
    private lateinit var diaryKeywordDao: DiaryKeywordDao
    private lateinit var database: DiaryDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, DiaryDatabase::class.java).build()
        diaryDao = database.diaryDao()
        diaryKeywordDao = database.diaryKeywordDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        database.close()
    }

    @Test
    @Throws(Exception::class)
    fun diaryDaoIsSucceedWriteDiaryAndReadInList(): Unit = runBlocking {
        val diary = DiaryEntity(
            id = 1,
            title = "2023-01-01",
        )
        diaryDao.add(diary)
        diaryDao.getAllDiaryContents()
    }

    @Test
    @Throws(Exception::class)
    fun diaryKeywordDaoIsSucceedWriteDiaryAndReadInList() = runBlocking {
        val diary = DiaryEntity(
            id = 100,
            title = "2023-01-01",
        )
        val keyword = listOf(
            DiaryKeywordEntity(
                id = "1",
                diaryId = 100,
                keyword = "hoge",
            ),
            DiaryKeywordEntity(
                id = "2",
                diaryId = 100,
                keyword = "fuga",
            ),
        )

        diaryDao.add(diary)
        diaryKeywordDao.add(keyword)
        val diaryList = diaryDao.getAllDiaryContents()

        val expect = listOf(
            DiaryWithKeywordsEntity(
                diary = diary,
                keywords = keyword,
            ),
        )

        Assert.assertEquals(diaryList.size, expect.size)
        Assert.assertEquals(diaryList[0].diary, diary)
        Assert.assertEquals(diaryList[0].keywords, keyword)
    }
}
