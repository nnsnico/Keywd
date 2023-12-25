package net.nns.keywd.model.preview

import arrow.core.Either
import arrow.core.continuations.either
import arrow.core.sequence
import arrow.core.some
import net.nns.keywd.model.Diary
import net.nns.keywd.model.Title

object DiaryFixture {
    suspend fun create(): Either<Throwable, List<Diary>> = either {
        val titleWithIndex = listOf(
            Title.fromString("2023/06/01 00:00"),
            Title.fromString("2023/06/02 00:00"),
            Title.fromString("2023/06/03 00:00"),
            Title.fromString("2023/06/04 00:00"),
        ).sequence().bind().mapIndexed { i, t ->
            Pair(i, t)
        }
        val keywords = KeywordsFixture.create().bind {
            Exception("Unexpected error")
        }

        titleWithIndex.map { (i, t) ->
            Diary(
                id = i.some(),
                title = t,
                keywords = keywords,
            )
        }
    }
}
