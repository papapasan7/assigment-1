package utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class SizeValodationTest {

    @Test
    fun isValidSizeTestReturns() {
        assertEquals(4, categories.size)
    }

    @Test
    fun isValidSizeTestGiveFalse() {
        assertFalse(isValidSize(null))
        assertFalse(isValidSize(55))
    }

    @Test
    fun isValidSizeTestGiveTrue() {
        assertTrue(isValidSize(128))
        assertTrue(isValidSize(64))
    }
}
