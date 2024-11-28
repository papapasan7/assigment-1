package utils

val criterionOfProduct = setOf("memory", "name","id","price")
val criterionOfOrder = setOf( "name","id")

// You can also refactor this function to use Lambdas
fun isValidCategoryForProduct(categoryToCheck: String?): Boolean =

        criterionOfProduct.any { categoryToCheck?.contains(it) ?: false }

fun isValidCategoryForOrder(categoryToCheck: String?): Boolean =

        criterionOfOrder.any { categoryToCheck?.contains(it) ?: false }