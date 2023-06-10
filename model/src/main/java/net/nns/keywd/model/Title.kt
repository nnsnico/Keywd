package net.nns.keywd.model

import arrow.core.Either
import arrow.core.Option
import arrow.core.continuations.either
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@JvmInline
value class Title private constructor(val value: String) {
    companion object {
        private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.JAPAN).apply {
            timeZone = TimeZone.getTimeZone("Asia/Tokyo")
        }

        suspend fun fromDate(date: Date): Either<Throwable, Title> = either {
            val dateFormat = Either.catch { dateFormat.format(date) }.bind()

            Title(dateFormat)
        }

        suspend fun fromString(str: String): Either<Throwable, Title> = either {
            val maybeDate = Either.catch { dateFormat.parse(str) }.bind()
            val date = Option.fromNullable(maybeDate).toEither {
                Exception("Can't parse to Date")
            }.bind()

            fromDate(date).bind()
        }
    }
}
