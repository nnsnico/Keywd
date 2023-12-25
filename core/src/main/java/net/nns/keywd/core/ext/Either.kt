package net.nns.keywd.core.ext

import arrow.core.Either
import arrow.core.getOrHandle

fun <L, R> Either<L, List<R>>.getOrEmpty(): List<R> {
    return this.getOrHandle {
        emptyList()
    }
}
