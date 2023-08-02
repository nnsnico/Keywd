package net.nns.keywd.core

import arrow.core.Either
import arrow.core.identity
import arrow.core.right
import arrow.core.traverse
import kotlin.experimental.ExperimentalTypeInference

@PublishedApi
internal fun <T> Iterable<T>.collectionSizeOrDefault(default: Int): Int =
    if (this is Collection<*>) this.size else default

@Suppress("MagicNumber")
@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
inline fun <E, A, B> Iterable<A>.traverse(f: (A) -> Either<E, B>): Either<E, List<B>> {
    val destination = ArrayList<B>(collectionSizeOrDefault(10))
    for (item in this) {
        when (val res = f(item)) {
            is Either.Right -> destination.add(res.value)
            is Either.Left -> return res
        }
    }
    return destination.right()
}

@Suppress("MagicNumber")
@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
inline fun <A, B> Iterable<A>.traverse(f: (A) -> Result<B>): Result<List<B>> {
    val destination = ArrayList<B>(collectionSizeOrDefault(10))
    for (item in this) {
        f(item).fold(destination::add) { throwable ->
            return@traverse Result.failure(throwable)
        }
    }
    return Result.success(destination)
}

fun <E, A> Iterable<Either<E, A>>.sequence(): Either<E, List<A>> = traverse(::identity)

fun <A> Iterable<Result<A>>.sequence(): Result<List<A>> = traverse(::identity)
