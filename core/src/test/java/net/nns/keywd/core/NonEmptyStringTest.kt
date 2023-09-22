package net.nns.keywd.core

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class NonEmptyStringTest {

    @Test
    fun init_isSome_whenValueIsNonEmpty() {
        val value = NonEmptyString.init("test")

        Assert.assertEquals(value.isNotEmpty(), true)
    }


    @Test
    fun init_isNone_whenValueIsEmpty() {
        val value = NonEmptyString.init("")

        Assert.assertEquals(value.isEmpty(), true)
    }
}
