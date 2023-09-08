package net.nns.keywd.core

import arrow.core.Either
import arrow.core.Option
import arrow.core.left
import arrow.core.none
import arrow.core.right
import arrow.core.some

fun <L, R> Boolean.fold(
    isTrue: () -> R,
    isFalse: () -> L,
): Either<L, R> = if (this) {
    isTrue().right()
} else {
    isFalse().left()
}

fun <T> Boolean.fold(
    isTrue: () -> T,
): Option<T> = if (this) {
    isTrue().some()
} else {
    none()
}
