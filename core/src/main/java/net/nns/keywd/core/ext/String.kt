package net.nns.keywd.core.ext

fun String.endsWithBlankOrEnter(): Boolean {
    return endsWith(" ") || endsWith("　") || endsWith("\n")
}
