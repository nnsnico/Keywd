package net.nns.keywd.repository

import arrow.core.Either
import arrow.core.getOrElse
import arrow.core.traverse
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import net.nns.keywd.datasource.dao.DiaryDao
import net.nns.keywd.model.repository.DiaryRepository
import net.nns.keywd.model.repository.dto.DiaryEntity
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.sql.SQLException

@RunWith(JUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class DefaultDiaryRepositoryTest {
    private lateinit var repository: DiaryRepository

    @MockK
    private lateinit var dao: DiaryDao

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        repository = DefaultDiaryRepository(dao)
    }

    @Test
    fun addDiary_isRight_whenDBInsertSucceed() = runTest {
        every { dao.add(any()) } returns Unit
        val data = DiaryEntity(
            id = 1,
            title = "2023-01-01",
            content = "hoge",
        )

        val expect = repository.addDiary(data)
        Assert.assertTrue(expect.isRight())
    }

    @Test
    fun addDiary_isLeft_whenDBInsertFail() = runTest {
        every { dao.add(any()) } throws SQLException()
        val data = DiaryEntity(
            id = 1,
            title = "hoge",
            content = "fuga",
        )

        val expect = repository.addDiary(data)
        Assert.assertTrue(expect.isLeft())
    }

    @Test
    fun deleteDiary_isRight_whenDBDeleteSucceed() = runTest {
        every { dao.delete(any()) } returns Unit
        val data = DiaryEntity(
            id = 1,
            title = "hoge",
            content = "fuga",
        )

        val expect = repository.deleteDiary(data)
        Assert.assertTrue(expect.isRight())
    }

    @Test
    fun deleteDiary_isLeft_whenDBDeleteFail() = runTest {
        every { dao.delete(any()) } throws SQLException()
        val data = DiaryEntity(
            id = 1,
            title = "hoge",
            content = "fuga",
        )

        val expect = repository.deleteDiary(data)
        Assert.assertTrue(expect.isLeft())
    }

    @Test
    fun getSavedDiaries_getAllDiariesCompletely() = runTest {
        val returnedList = listOf(
            DiaryEntity(
                id = 1,
                title = "hoge",
                content = "fuga",
            ),
            DiaryEntity(
                id = 2,
                title = "hoge",
                content = "fuga",
            ),
            DiaryEntity(
                id = 3,
                title = "hoge",
                content = "fuga",
            ),
        )
        every { dao.getAllDiaryContents() } returns returnedList

        val diaryList = returnedList.traverse { it.toDiary() }.getOrElse { emptyList() }

        when (val expect = repository.getSavedDiaries()) {
            is Either.Right -> Assert.assertEquals(expect.value, diaryList)
            is Either.Left -> Assert.fail(expect.value.toString())
        }
    }

    @Test
    fun getSavedDiaries_isLeft_whenDBSelectFail() = runTest {
        every { dao.getAllDiaryContents() } throws SQLException()

        val expect = repository.getSavedDiaries()
        Assert.assertTrue(expect.isLeft())
    }
}
