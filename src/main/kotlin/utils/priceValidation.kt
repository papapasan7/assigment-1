package utils




/**
 * Validates whether a given price is valid.
 *
 * A price is considered valid if it is not null and greater than 0.
 *
 * @param categoryToCheck The price value to validate.
 * @return `true` if the price is valid, `false` otherwise.
 *
 * @throws NullPointerException If the input is null.
 */
fun isValidPrice(categoryToCheck: Double?): Boolean =

        categoryToCheck!=null && categoryToCheck > 0