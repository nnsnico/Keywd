package net.nns.keywd.model

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@JvmInline
value class Title private constructor(val value: String) {
    companion object {
        fun fromDate(date: Date): Title {
            return Title(
                SimpleDateFormat("yyyy-MM-dd", Locale.JAPAN).apply {
                    timeZone = TimeZone.getTimeZone("Asia/Tokyo")
                }.format(date),
            )
        }
    }
}
