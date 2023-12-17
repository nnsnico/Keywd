package net.nns.keywd.model

import arrow.core.Either
import arrow.core.continuations.either
import net.nns.keywd.core.NonEmptyString
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@JvmInline
value class Title private constructor(val value: String) {
    companion object {
        private const val DATE_TIME_FORMAT = "yyyy/MM/dd HH:mm"

        suspend fun fromDate(date: LocalDateTime): Either<Throwable, Title> = either {
            val formattedDate: String = Either.catch {
                date.format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT))
            }.bind()

            NonEmptyString.init(formattedDate).map {
                Title(it.value)
            }.bind {
                Exception("Title is empty")
            }
        }

        suspend fun fromString(str: String): Either<Throwable, Title> = either {
            val date: LocalDateTime = Either.catch {
                LocalDateTime.parse(str, DateTimeFormatter.ofPattern(DATE_TIME_FORMAT))
            }.bind()

            fromDate(date).bind()
        }
    }
}
