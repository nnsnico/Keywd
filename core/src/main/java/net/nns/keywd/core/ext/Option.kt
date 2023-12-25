package net.nns.keywd.core.ext

import arrow.core.Option
import arrow.core.getOrElse

fun <T> Option<List<T>>.getOrEmpty(): List<T> {
    return this.getOrElse {
        emptyList()
    }
}
