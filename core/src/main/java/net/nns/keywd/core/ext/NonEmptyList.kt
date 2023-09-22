package net.nns.keywd.core.ext

import arrow.core.Either
import arrow.core.NonEmptyList
import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import kotlin.experimental.ExperimentalTypeInference

object NonEmptyList {
    @OptIn(ExperimentalTypeInference::class)
    @OverloadResolutionByLambdaReturnType
    inline fun <A, B> NonEmptyList<A>.traverse(f: (A) -> Option<B>): Option<NonEmptyList<B>> =
        f(head).map { newHead ->
            val acc = mutableListOf<B>()
            tail.forEach { a ->
                when (val res = f(a)) {
                    is Some -> acc.add(res.value)
                    is None -> return@traverse res
                }
            }
            NonEmptyList(newHead, acc)
        }

    @OptIn(ExperimentalTypeInference::class)
    @OverloadResolutionByLambdaReturnType
    inline fun <E, A, B> NonEmptyList<A>.traverse(f: (A) -> Either<E, B>): Either<E, NonEmptyList<B>> =
        f(head).map { newHead ->
            val acc = mutableListOf<B>()
            tail.forEach { a ->
                when (val res = f(a)) {
                    is Either.Right -> acc.add(res.value)
                    is Either.Left -> return@traverse res
                }
            }
            NonEmptyList(newHead, acc)
        }
}
