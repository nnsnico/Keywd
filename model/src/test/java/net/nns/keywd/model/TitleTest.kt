package net.nns.keywd.model

import arrow.core.Either
import arrow.core.continuations.either
import arrow.core.getOrElse
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.time.LocalDate
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@RunWith(JUnit4::class)
class TitleTest {
    @Test
    fun fromDate_isSuccess_whenCorrectFormat() = runTest {
        val zoneJST = ZonedDateTime.parse(
            "2023/06/04 10:15:30 JST",
            DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss zzz"),
        )
        val zoneUTC = ZonedDateTime.parse(
            "2023/06/04 00:15:30 UTC",
            DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss zzz"),
        )
        val expectJST = Title.fromDate(LocalDate.from(zoneJST)).getOrElse { null }
        val expectUTC = Title.fromDate(LocalDate.from(zoneUTC)).getOrElse { null }

        Assert.assertEquals(expectJST?.value, "2023-06-04")
        Assert.assertEquals(expectUTC?.value, "2023-06-04")
    }

    @Test
    fun fromDate_isFailure_whenInvalidFormat() = runTest {
        val invalidDateFormat = Either.catch {
            ZonedDateTime.parse(
                "2023-06-04 10:15:30 JST",
                DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss zzz"),
            )
        }

        val expect = either {
            val instant = invalidDateFormat.map { it.toInstant() }.bind()
            Title.fromDate(LocalDate.from(instant)).bind()
        }.getOrElse { null }

        Assert.assertEquals(expect?.value, null)
    }

    @Test
    fun fromString_isSuccess_whenCorrectFormat() = runTest {
        val dateFormat = "2023-06-04"

        val expect = Title.fromString(dateFormat).getOrElse { null }

        Assert.assertEquals(expect?.value, dateFormat)
    }

    @Test
    fun fromString_isFailure_whenInvalidFormat() = runTest {
        val invalidDateFormat = "2023/06/04 00:00:00 JST"

        val expect = Title.fromString(invalidDateFormat).getOrElse { null }

        Assert.assertEquals(expect?.value, null)
    }

}
