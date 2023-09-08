package net.nns.keywd.ui.core

import android.os.Parcelable
import arrow.core.Option
import kotlinx.parcelize.Parcelize
import net.nns.keywd.core.fold

@Parcelize
class NonEmptyString private constructor(
    val value: String,
) : Parcelable {
    companion object {
        fun init(value: String): Option<NonEmptyString> {
            return value.isNotBlank().fold { NonEmptyString(value) }
        }
    }
}
