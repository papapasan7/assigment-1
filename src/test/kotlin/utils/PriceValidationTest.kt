package utils
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class PriceValidationTest {

    @Test
    fun priceValidationTestFiveFalse() {
        assertFalse(isValidPrice(null))
        assertFalse(isValidPrice(-3.6))
    }

    @Test
    fun priceValidationTestFiveTrue() {
        assertTrue(isValidPrice(3.6))
        assertTrue(isValidPrice(3.6))
    }
}
