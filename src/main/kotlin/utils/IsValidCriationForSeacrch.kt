package utils
/**
 * A set containing valid criteria for searching products.
 *
 * @see isValidCriterionForProductSearch
 */
val criterionOfProductSearch = setOf("memory", "name", "id", "price", "orderid")

/**
 * Validates whether a given category is a valid criterion for product search.
 *
 * @param categoryToCheck The category string to validate.
 * @return `true` if the category is valid, `false` otherwise.
 *
 * @throws NullPointerException If the input is null.
 * @see criterionOfProductSearch
 */
fun isValidCriterionForProductSearch(categoryToCheck: String?): Boolean =

    criterionOfProductSearch.any { categoryToCheck?.lowercase()?.contains(it) ?: false }
