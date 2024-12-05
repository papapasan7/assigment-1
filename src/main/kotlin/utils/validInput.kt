package utils
/**
 * Reads and validates a memory size input from the user.
 * Continues to prompt until the input matches one of the valid sizes.
 *
 * @param prompt The message to display to the user before input.
 * @return The valid memory size entered by the user.
 *
 * @see isValidSize
 * @see readInt
 */
fun readValidSize(prompt: String?): Int {
    var input = readInt(prompt)
    do {
        if (isValidSize(input))
            return input
        else {
            print("Invalid category $input. \n  Please try again: ")
            input = readInt(prompt)
        }
    } while (true)
}
/**
 * Reads and validates a price input from the user.
 * Continues to prompt until the input is greater than 0.
 *
 * @param prompt The message to display to the user before input.
 * @return The valid price entered by the user.
 *
 * @see isValidPrice
 * @see readDouble
 */
fun readValidPrice(prompt: String?): Double {
    var input = readDouble(prompt)
    do {
        if (isValidPrice(input))
            return input
        else {
            print("Error, price must be more then 0 $input. \n  Please try again: ")
            input = readDouble(prompt)
        }
    } while (true)
}
/**
 * Reads and validates a product criterion input from the user.
 * Continues to prompt until the input matches one of the valid product criteria.
 *
 * @param prompt The message to display to the user before input.
 * @return The valid product criterion entered by the user.
 *
 * @see isValidCriterionForProduct
 * @see readString
 */
fun readValidCategoryForProduct(prompt: String?): String {
    var input = readString(prompt)
    do {
        if (isValidCriterionForProduct(input))
            return input
        else {
            print("Invalid criterion $input.  Please try again: ")
            input = readString(prompt)
        }
    } while (true)
}
/**
 * Reads and validates an order criterion input from the user.
 * Continues to prompt until the input matches one of the valid order criteria.
 *
 * @param prompt The message to display to the user before input.
 * @return The valid order criterion entered by the user.
 *
 * @see isValidCriterionForOrder
 * @see readString
 */
fun readValidCategoryForOrder(prompt: String?): String {
    var input = readString(prompt)
    do {
        if (isValidCriterionForOrder(input))
            return input
        else {
            print("Invalid criterion $input.  Please try again: ")
            input = readString(prompt)
        }
    } while (true)
}
/**
 * Reads and validates a product search criterion input from the user.
 * Continues to prompt until the input matches one of the valid product search criteria.
 *
 * @param prompt The message to display to the user before input.
 * @return The valid product search criterion entered by the user.
 *
 * @see isValidCriterionForProductSearch
 * @see readString
 */
fun readValidCriterionForProductSearch(prompt: String?): String {
    var input = readString(prompt)
    do {
        if (isValidCriterionForProductSearch(input))
            return input
        else {
            print("Invalid criterion $input.  Please try again: ")
            input = readString(prompt)
        }
    } while (true)
}

/**
 * Reads and validates an order search criterion input from the user.
 * Continues to prompt until the input matches one of the valid order search criteria.
 *
 * @param prompt The message to display to the user before input.
 * @return The valid order search criterion entered by the user.
 *
 * @see isValidCriterionForOrder
 * @see readString
 */
fun readValidCriterionForOrderSearch(prompt: String?): String {
    var input = readString(prompt)
    do {
        if (isValidCriterionForOrder(input))
            return input
        else {
            print("Invalid criterion $input.  Please try again: ")
            input = readString(prompt)
        }
    } while (true)
}