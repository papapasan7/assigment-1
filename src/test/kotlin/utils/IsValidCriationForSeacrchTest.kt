package utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class IsValidCriationForSeacrchTest {

    @Test
    fun criterionOfProductSearchReturnsFullCategoriesSet() {
        assertEquals(5, criterionOfProductSearch.size)
        assertTrue(criterionOfProductSearch.contains("memory"))
        assertTrue(criterionOfProductSearch.contains("name"))
        assertFalse(criterionOfProductSearch.contains(""))
    }

    @Test
    fun isValidCriterionForProductSearchTrueWhenCategoryExists() {
        assertTrue(isValidCriterionForProductSearch("id"))
        assertTrue(isValidCriterionForProductSearch("price"))
        assertTrue(isValidCriterionForProductSearch("orderid"))
        assertTrue(isValidCriterionForProductSearch("ID"))
    }

    @Test
    fun isValidCriterionForProductSearchForProductSearchFalseWhenCategoryDoesNotExist() {
        assertFalse(isValidCriterionForProductSearch("i"))
        assertFalse(isValidCriterionForProductSearch("oed"))
        assertFalse(isValidCriterionForProductSearch(""))
        assertFalse(isValidCriterionForProductSearch(" "))
    }
}
