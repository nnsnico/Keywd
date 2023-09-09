package net.nns.keywd.model

import arrow.core.Either
import arrow.core.Option
import arrow.core.continuations.either
import java.time.LocalDate

@JvmInline
value class Title private constructor(val value: String) {
    companion object {
        suspend fun fromDate(date: LocalDate): Either<Throwable, Title> = either {
            Title(date.toString())
        }

        suspend fun fromString(str: String): Either<Throwable, Title> = either {
            val maybeDate = Either.catch { LocalDate.parse(str) }.bind()
            val date = Option.fromNullable(maybeDate).toEither {
                Exception("Can't parse to Date")
            }.bind()

            fromDate(date).bind()
        }
    }
}
