package net.nns.keywd.core

import android.os.Parcelable
import arrow.core.Option
import kotlinx.parcelize.Parcelize
import net.nns.keywd.core.ext.toOption

@Parcelize
@JvmInline
value class NonEmptyString private constructor(
    val value: String,
) : Parcelable {
    companion object {
        fun init(value: String): Option<NonEmptyString> {
            return value.isNotBlank().toOption { NonEmptyString(value) }
        }
    }
}
