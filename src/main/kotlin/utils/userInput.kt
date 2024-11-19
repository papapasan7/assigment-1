package utils

fun readInt(prompt: String): Int {
    while (true) {
        println(prompt)
        val input = readLine()
        try {
            return input?.toInt() ?: throw NumberFormatException("Input is null")
        } catch (e: NumberFormatException) {
            println("Invalid input. Please enter a valid integer.")
        }
    }
}

fun readDouble(prompt: String): Double {
    while (true) {
        println(prompt)
        val input = readLine()
        try {
            return input?.toDouble() ?: throw NumberFormatException("Input is null")
        } catch (e: NumberFormatException) {
            println("Invalid input. Please enter a valid decimal number.")
        }
    }
}

fun readString(prompt: String): String {
    while (true) {
        println(prompt)
        val input = readLine()
        if (!input.isNullOrBlank()) {
            return input
        } else {
            println("Invalid input. Please enter a non-empty string.")
        }
    }
}