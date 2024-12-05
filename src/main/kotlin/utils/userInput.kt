package utils
/**
 * Reads an integer input from the user.
 * Continues to prompt until a valid integer is entered.
 *
 * @param prompt The message to display to the user before input.
 * @return The valid integer entered by the user.
 *
 * @throws NumberFormatException If the input cannot be parsed as an integer.
 */
fun readInt(prompt: String?): Int {
    while (true) {
        print(prompt)
        val input = readLine()
        try {
            return input?.toInt() ?: throw NumberFormatException("Input is null")
        } catch (e: NumberFormatException) {
            println("Invalid input. Please enter a valid integer.")
        }
    }
}
/**
 * Reads a double input from the user.
 * Continues to prompt until a valid decimal number is entered.
 *
 * @param prompt The message to display to the user before input.
 * @return The valid decimal number entered by the user.
 *
 * @throws NumberFormatException If the input cannot be parsed as a double.
 */
fun readDouble(prompt: String?): Double {
    while (true) {
        print(prompt)
        val input = readLine()
        try {
            return input?.toDouble() ?: throw NumberFormatException("Input is null")
        } catch (e: NumberFormatException) {
            println("Invalid input. Please enter a valid decimal number.")
        }
    }
}
/**
 * Reads a string input from the user.
 * Continues to prompt until a non-empty string is entered.
 *
 * @param prompt The message to display to the user before input.
 * @return The valid string entered by the user.
 *
 * @throws IllegalArgumentException If the input is null or blank.
 */
fun readString(prompt: String?): String {
    while (true) {
        print(prompt)
        val input = readLine()
        if (!input.isNullOrBlank()) {
            return input
        } else {
            println("Invalid input. Please enter a non-empty string.")
        }
    }
}