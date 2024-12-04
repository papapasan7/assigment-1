package utils

val criterionOfProduct = setOf("memory", "name","id","price")
val criterionOfOrder = setOf( "name","id")

// You can also refactor this function to use Lambdas
fun isValidCriterionForProduct(categoryToCheck: String?): Boolean =

        criterionOfProduct.any { categoryToCheck?.lowercase()?.contains(it) ?: false }

fun isValidCriterionForOrder(categoryToCheck: String?): Boolean =

        criterionOfOrder.any { categoryToCheck?.lowercase()?.contains(it) ?: false }