package net.nns.keywd.model

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Date

@RunWith(JUnit4::class)
class TitleTest {
    @Test
    fun fromDate_isCorrectFormat() {
        val zoneJST = ZonedDateTime.parse(
            "2023/06/04 10:15:30 JST",
            DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss zzz"),
        )
        val zoneUTC = ZonedDateTime.parse(
            "2023/06/04 00:15:30 UTC",
            DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss zzz"),
        )
        val expectJST = Title.fromDate(Date.from(zoneJST.toInstant()))
        val expectUTC = Title.fromDate(Date.from(zoneUTC.toInstant()))

        Assert.assertEquals(expectJST.value, "2023-06-04")
        Assert.assertEquals(expectUTC.value, "2023-06-04")
    }
}
