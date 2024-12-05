package utils
/**
 * A set containing valid memory size categories for products.
 *
 * @see isValidSize
 */
val categories = setOf(64,128,256,516)

/**
 * Validates whether a given memory size is valid.
 *
 * A memory size is considered valid if it matches one of the predefined categories.
 *
 * @param categoryToCheck The memory size value to validate.
 * @return `true` if the memory size is valid, `false` otherwise.
 *
 * @throws NullPointerException If the input is null.
 * @see categories
 */
fun isValidSize(categoryToCheck: Int?): Boolean =

   categories.any{category-> categoryToCheck == category}
