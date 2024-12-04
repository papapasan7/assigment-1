package utils

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested

import org.junit.jupiter.api.Test

class priceValidationTestTest
{


        @Test
        fun priceValidationTestFiveFalse()
        {
           assertFalse(isValidPrice(null))
            assertFalse(isValidPrice(-3.6))
        }
    @Test
    fun priceValidationTestFiveTrue()
    {
        assertTrue(isValidPrice(3.6))
        assertTrue(isValidPrice(3.6))
    }

}