package org.keb.samples

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test

class LibraryTest {

    @Test
    fun testSomeLibraryMethod() {

        //when
        val result = Library().someLibraryMethod()

        //then
        assertThat(result).isTrue()
    }
}
