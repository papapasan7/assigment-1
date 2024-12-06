package utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.ByteArrayInputStream

class ValidInputTest {
// https://docs.oracle.com/javase/8/docs/api/java/io/ByteArrayInputStream.html
// https://www.baeldung.com/convert-input-stream-to-array-of-bytes

    @Test
    fun readValidSizeTestGiveCorrectOut() {
        val result = "64\n"
        System.setIn(ByteArrayInputStream(result.toByteArray()))
        val result2 = readValidSize("Enter a number: ")
        assertEquals(64, result2)
    }

    @Test
    fun readValidSizeTestGiveCorrectOutwithWrongSize() {
        val result = "63\n64\n"
        System.setIn(ByteArrayInputStream(result.toByteArray()))
        val result2 = readValidSize("Enter a number: ")
        assertEquals(64, result2)
    }

    @Test
    fun readValidPriceTestGiveCorrectOut() {
        val result = "250.5\n"
        System.setIn(ByteArrayInputStream(result.toByteArray()))
        val result2 = readValidPrice("Enter a number: ")
        assertEquals(250.5, result2)
    }

    @Test
    fun readValidPriceTestGiveCorrectOutwithWrongPrice() {
        val result = "0\n64.5\n"
        System.setIn(ByteArrayInputStream(result.toByteArray()))
        val result2 = readValidPrice("Enter a number: ")
        assertEquals(64.5, result2)
    }

    @Test
    fun readValidCategoryForProductGiveCorrectOut() {
        val result = "id\n"
        System.setIn(ByteArrayInputStream(result.toByteArray()))
        val result2 = readValidCategoryForProduct("Enter a number: ")
        assertEquals("id", result2)
    }

    @Test
    fun readValidCategoryForProductGiveCorrectOutwithWrongCategory() {
        val result = "asda\nid\n"
        System.setIn(ByteArrayInputStream(result.toByteArray()))
        val result2 = readValidCategoryForProduct("Enter a number: ")
        assertEquals("id", result2)
    }

    @Test
    fun readValidCategoryForOrderGiveCorrectOut() {
        val result = "id\n"
        System.setIn(ByteArrayInputStream(result.toByteArray()))
        val result2 = readValidCategoryForOrder("Enter a number: ")
        assertEquals("id", result2)
    }

    @Test
    fun readValidCategoryForOrderGiveCorrectOutwithWrongCategory() {
        val result = "dsad\nid\n"
        System.setIn(ByteArrayInputStream(result.toByteArray()))
        val result2 = readValidCategoryForOrder("Enter a number: ")
        assertEquals("id", result2)
    }

    @Test
    fun readValidCriterionForProductSearchGiveCorrectOut() {
        val result = "id\n"
        System.setIn(ByteArrayInputStream(result.toByteArray()))
        val result2 = readValidCriterionForProductSearch("Enter a number: ")
        assertEquals("id", result2)
    }

    @Test
    fun readValidCriterionForProductSearchGiveCorrectOutWithWrongCategory() {
        val result = "sadad\nid\n"
        System.setIn(ByteArrayInputStream(result.toByteArray()))
        val result2 = readValidCriterionForProductSearch("Enter a number: ")
        assertEquals("id", result2)
    }

    @Test
    fun readValidCriterionForOrderSearch() {
        val result = "id\n"
        System.setIn(ByteArrayInputStream(result.toByteArray()))
        val result2 = readValidCriterionForOrderSearch("Enter a number: ")
        assertEquals("id", result2)
    }

    @Test
    fun readValidCriterionForOrderSearchWithWrongCategory() {
        val result = "sadas\nid\n"
        System.setIn(ByteArrayInputStream(result.toByteArray()))
        val result2 = readValidCriterionForOrderSearch("Enter a number: ")
        assertEquals("id", result2)
    }
}
