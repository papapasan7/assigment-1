package utils

/**
 * A set containing valid criteria for sorting or filtering products.
 *
 * @see isValidCriterionForProduct
 */
val criterionOfProduct = setOf("memory", "name","id","price")
/**
 * A set containing valid criteria for sorting or filtering orders.
 *
 * @see isValidCriterionForOrder
 */
val criterionOfOrder = setOf( "name","id")

/**
 * Validates whether a given category is a valid criterion for products.
 *
 * @param categoryToCheck The category string to validate.
 * @return `true` if the category is valid for products, `false` otherwise.
 *
 * @throws NullPointerException If the input is null.
 * @see criterionOfProduct
 */
fun isValidCriterionForProduct(categoryToCheck: String?): Boolean =

        criterionOfProduct.any { categoryToCheck?.lowercase()?.contains(it) ?: false }
/**
 * Validates whether a given category is a valid criterion for orders.
 *
 * @param categoryToCheck The category string to validate.
 * @return `true` if the category is valid for orders, `false` otherwise.
 *
 * @throws NullPointerException If the input is null.
 * @see criterionOfOrder
 */

fun isValidCriterionForOrder(categoryToCheck: String?): Boolean =

        criterionOfOrder.any { categoryToCheck?.lowercase()?.contains(it) ?: false }