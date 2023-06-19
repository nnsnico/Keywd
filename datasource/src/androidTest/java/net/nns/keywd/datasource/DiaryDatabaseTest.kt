package net.nns.keywd.datasource

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.nns.keywd.datasource.dao.DiaryDao
import net.nns.keywd.datasource.dto.DiaryEntity
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class DiaryDatabaseTest {
    private lateinit var diaryDao: DiaryDao
    private lateinit var database: DiaryDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, DiaryDatabase::class.java).build()
        diaryDao = database.diaryDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        database.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeDiaryAndReadInList() {
        val diary = DiaryEntity(
            id = 0,
            title = "2023-01-01",
            content = "fuga",
        )
        diaryDao.add(diary)
        val diaryList = diaryDao.getAllDiaryContents()
        assertThat(diaryList[0], equalTo(diary))
    }
}
