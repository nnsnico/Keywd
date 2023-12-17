package net.nns.keywd.model

import arrow.core.Either
import arrow.core.Option
import arrow.core.continuations.either
import net.nns.keywd.core.NonEmptyString
import java.time.LocalDate

@JvmInline
value class Title private constructor(val value: String) {
    companion object {
        suspend fun fromDate(date: LocalDate): Either<Throwable, Title> = either {
            val maybeDate = NonEmptyString.init(date.toString())
            maybeDate.map {
                Title(it.value)
            }.bind {
                Exception("Title is empty")
            }
        }

        suspend fun fromString(str: String): Either<Throwable, Title> = either {
            val maybeDate = Either.catch { LocalDate.parse(str) }.bind()
            val date = Option.fromNullable(maybeDate).bind {
                Exception("Can't parse to Date")
            }

            fromDate(date).bind()
        }
    }
}
