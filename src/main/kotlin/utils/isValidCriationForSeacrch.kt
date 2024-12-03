package utils

val criterionOfProductSearch = setOf("memory", "name","id","price","orderid")


// You can also refactor this function to use Lambdas
fun isValidCriterionForProductSearch(categoryToCheck: String?): Boolean =

        criterionOfProductSearch.any { categoryToCheck?.lowercase()?.contains(it) ?: false }

