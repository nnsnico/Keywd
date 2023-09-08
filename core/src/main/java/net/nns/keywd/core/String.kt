package net.nns.keywd.core

fun String.endsWithBlankOrEnter(): Boolean {
    return endsWith(" ") || endsWith("　") || endsWith("\n")
}
