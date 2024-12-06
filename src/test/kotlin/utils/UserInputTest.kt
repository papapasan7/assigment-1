package utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.ByteArrayInputStream

class UserInputTest {
// https://docs.oracle.com/javase/8/docs/api/java/io/ByteArrayInputStream.html
    // https://www.baeldung.com/convert-input-stream-to-array-of-bytes
    @Test
    fun readIntGiveCorrectNumberTest() {
        val result = "33\n"
        System.setIn(ByteArrayInputStream(result.toByteArray()))
        val result2 = readInt("Enter a number: ")
        assertEquals(33, result2)
    }

    @Test
    fun readIntTestGiveInorrect() {
        val result = "abc\n55\n"
        System.setIn(ByteArrayInputStream(result.toByteArray()))
        val result2 = readInt("Enter a number: ")
        assertEquals(55, result2)
    }

    @Test
    fun readDoubleGiveCorrectNumberTest() {
        val result = "33.0\n"
        System.setIn(ByteArrayInputStream(result.toByteArray()))
        val result2 = readDouble("Enter a number: ")
        assertEquals(33.0, result2)
    }

    @Test
    fun readDoubleTestGiveInorrect() {
        val result = "abc\n55.5\n"
        System.setIn(ByteArrayInputStream(result.toByteArray()))
        val result2 = readDouble("Enter a number: ")
        assertEquals(55.5, result2)
    }

    @Test
    fun readStringTestGiveCorrectOut() {
        val result = "abc\n"
        System.setIn(ByteArrayInputStream(result.toByteArray()))
        val result2 = readString("Enter a number: ")
        assertEquals("abc", result2)
    }
}
