package net.nns.keywd.core

import arrow.core.Either
import arrow.core.left
import arrow.core.right

fun <L, R> Boolean.fold(
    isTrue: () -> R,
    isFalse: () -> L,
): Either<L, R> = if (this) {
    isTrue().right()
} else {
    isFalse().left()
}
