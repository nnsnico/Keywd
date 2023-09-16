package net.nns.keywd.model

import net.nns.keywd.core.NonEmptyString

data class Keyword(
    val id: String,
    val value: NonEmptyString,
)
