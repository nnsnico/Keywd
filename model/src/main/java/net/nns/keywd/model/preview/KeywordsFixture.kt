package net.nns.keywd.model.preview

import arrow.core.NonEmptyList
import arrow.core.Option
import arrow.core.continuations.option
import arrow.core.nonEmptyListOf
import arrow.core.sequence
import net.nns.keywd.core.NonEmptyString
import net.nns.keywd.model.Keyword

object KeywordsFixture {
    suspend fun create(): Option<NonEmptyList<Keyword>> = option {
        val words = nonEmptyListOf(
            NonEmptyString.init("hoge"),
            NonEmptyString.init("fuga"),
            NonEmptyString.init("piyo"),
            NonEmptyString.init("foo"),
            NonEmptyString.init("bar"),
            NonEmptyString.init("baz"),
            NonEmptyString.init("baz"),
            NonEmptyString.init("baz"),
        ).sequence().bind()
        words.map {
            Keyword(
                id = it.value,
                value = it,
            )
        }
    }
}
