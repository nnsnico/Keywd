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
import net.nns.keywd.datasource.dao.DiaryKeywordDao
import net.nns.keywd.datasource.dto.DiaryEntity
import net.nns.keywd.datasource.dto.DiaryKeywordEntity
import net.nns.keywd.datasource.dto.DiaryWithKeywordsEntity
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
    private lateinit var diaryDao: DiaryDao

    @MockK
    private lateinit var diaryKeywordDao: DiaryKeywordDao

    private val entity
        get() = run {
            val diaryEntity = DiaryEntity(
                id = 1,
                title = "2023/01/01 00:00",
            )
            val keywordEntities = listOf(
                DiaryKeywordEntity(
                    id = "1",
                    diaryId = 1,
                    keyword = "hoge",
                ),
                DiaryKeywordEntity(
                    id = "2",
                    diaryId = 1,
                    keyword = "fuga",
                ),
                DiaryKeywordEntity(
                    id = "3",
                    diaryId = 1,
                    keyword = "piyo",
                ),
            )
            DiaryWithKeywordsEntity(
                diary = diaryEntity,
                keywords = keywordEntities,
            )
        }

    @Before
    fun setup() {
        repository = DefaultDiaryRepository(
            diaryDao = diaryDao,
            diaryKeywordDao = diaryKeywordDao,
        )
    }

    @Test
    fun addDiary_isRight_whenDBInsertSucceed() = runTest {
        coEvery {
            diaryDao.add(any())
        } returns 1
        coEvery {
            diaryDao.getDiaryIdByRowId(any())
        } returns 1
        coEvery {
            diaryKeywordDao.add(any())
        } just Runs

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
            diaryDao.add(any())
        } throws SQLException()

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
            diaryDao.delete(any())
        } just Runs

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
            diaryDao.delete(any())
        } throws SQLException()

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
        val entities = listOf(entity)

        coEvery {
            diaryDao.getAllDiaryContents()
        } returns entities

        val diaryList = entities.traverse { it.toDiary() }.getOrElse { emptyList() }

        when (val expect = repository.getSavedDiaries()) {
            is Either.Right -> Assert.assertEquals(expect.value, diaryList)
            is Either.Left -> Assert.fail(expect.value.toString())
        }
    }

    @Test
    fun getSavedDiaries_isLeft_whenDBSelectFail() = runTest {
        coEvery { diaryDao.getAllDiaryContents() } throws SQLException()

        val expect = repository.getSavedDiaries()
        Assert.assertTrue(expect.isLeft())
    }
}
