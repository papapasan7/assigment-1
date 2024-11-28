package utils

val categories = setOf(64,128,256,516)

// You can also refactor this function to use Lambdas
fun isValidSize(categoryToCheck: Int?): Boolean =

   categories.any{category-> categoryToCheck == category}
