package utils

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