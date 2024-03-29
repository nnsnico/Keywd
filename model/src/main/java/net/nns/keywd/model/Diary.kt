package net.nns.keywd.model

import arrow.core.NonEmptyList
import arrow.core.Option

data class Diary(
    val id: Option<Int>,
    val title: Title,
    val keywords: NonEmptyList<Keyword>,
)
