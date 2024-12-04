package utils


import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested

import org.junit.jupiter.api.Test

class sizeValidationTestTest
{

    @Test
    fun isValidSizeTestReturns()
    {
        assertEquals(4, categories.size)

    }
    @Test
    fun isValidSizeTestGiveFalse()
    {
        assertFalse(isValidSize(null))
        assertFalse(isValidSize(55))
    }
    @Test
    fun isValidSizeTestGiveTrue()
    {
        assertTrue(isValidSize(128))
        assertTrue(isValidSize(64))
    }

}