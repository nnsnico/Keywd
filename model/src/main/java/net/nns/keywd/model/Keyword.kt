package net.nns.keywd.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import net.nns.keywd.core.NonEmptyString

@Parcelize
data class Keyword(
    val id: String,
    val value: NonEmptyString,
) : Parcelable
