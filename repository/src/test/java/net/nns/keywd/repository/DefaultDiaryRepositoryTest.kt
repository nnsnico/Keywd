package net.nns.keywd.repository

import arrow.core.Either
import arrow.core.getOrElse
import arrow.core.traverse
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.mockk.just
import kotlinx.coroutines.test.runTest
import net.nns.keywd.datasource.dao.DiaryDao
import net.nns.keywd.datasource.dto.DiaryEntity
import net.nns.keywd.model.repository.DiaryRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.sql.SQLException

@RunWith(JUnit4::class)
class DefaultDiaryRepositoryTest {
    private lateinit var repository: DiaryRepository

    @get:Rule
    val rule = MockKRule(this)

    @MockK
    private lateinit var dao: DiaryDao

    @Before
    fun setup() {
        repository = DefaultDiaryRepository(dao)
    }

    @Test
    fun addDiary_isRight_whenDBInsertSucceed() = runTest {
        coEvery {
            dao.add(any())
        } just Runs

        val entity = DiaryEntity(
            id = 1,
            title = "2023-01-01",
            content = "hoge",
        )

        when (val data = entity.toDiary()) {
            is Either.Right -> {
                val expect = repository.addDiary(data.value)
                Assert.assertTrue(expect.isRight())
            }

            is Either.Left -> {
                Assert.fail("data throws exception.")
            }
        }
    }

    @Test
    fun addDiary_isLeft_whenDBInsertFail() = runTest {
        coEvery {
            dao.add(any())
        } throws SQLException()

        val entity = DiaryEntity(
            id = 1,
            title = "2023-06-01",
            content = "fuga",
        )

        when (val data = entity.toDiary()) {
            is Either.Right -> {
                val expect = repository.addDiary(data.value)
                Assert.assertTrue(expect.isLeft())
            }

            is Either.Left -> {
                Assert.fail("data throws exception.")
            }
        }
    }

    @Test
    fun deleteDiary_isRight_whenDBDeleteSucceed() = runTest {
        coEvery {
            dao.delete(any())
        } just Runs

        val entity = DiaryEntity(
            id = 1,
            title = "2023-06-01",
            content = "fuga",
        )

        when (val data = entity.toDiary()) {
            is Either.Right -> {
                val expect = repository.deleteDiary(data.value)
                Assert.assertTrue(expect.isRight())
            }

            is Either.Left -> {
                Assert.fail("data throws exception.")
            }
        }
    }

    @Test
    fun deleteDiary_isLeft_whenDBDeleteFail() = runTest {
        coEvery {
            dao.delete(any())
        } throws SQLException()

        val entity = DiaryEntity(
            id = 1,
            title = "2023-06-01",
            content = "fuga",
        )

        when (val data = entity.toDiary()) {
            is Either.Right -> {
                val expect = repository.deleteDiary(data.value)
                Assert.assertTrue(expect.isLeft())
            }

            is Either.Left -> {
                Assert.fail("data throws exception.")
            }
        }
    }

    @Test
    fun getSavedDiaries_getAllDiariesCompletely() = runTest {
        val returnedList = listOf(
            DiaryEntity(
                id = 1,
                title = "2023-06-01",
                content = "fuga",
            ),
            DiaryEntity(
                id = 2,
                title = "2023-06-02",
                content = "fuga",
            ),
            DiaryEntity(
                id = 3,
                title = "2023-06-03",
                content = "fuga",
            ),
        )

        coEvery {
            dao.getAllDiaryContents()
        } returns returnedList

        val diaryList = returnedList.traverse { it.toDiary() }.getOrElse { emptyList() }

        when (val expect = repository.getSavedDiaries()) {
            is Either.Right -> Assert.assertEquals(expect.value, diaryList)
            is Either.Left -> Assert.fail(expect.value.toString())
        }
    }

    @Test
    fun getSavedDiaries_isLeft_whenDBSelectFail() = runTest {
        coEvery { dao.getAllDiaryContents() } throws SQLException()

        val expect = repository.getSavedDiaries()
        Assert.assertTrue(expect.isLeft())
    }
}
