package utils


import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test

class criterionOfProductSearchTest {

    @Test
    fun criterionOfProductSearchReturnsFullCategoriesSet(){
        assertEquals(5, criterionOfProductSearch.size)
        assertTrue(criterionOfProductSearch.contains("memory"))
        assertTrue(criterionOfProductSearch.contains("name"))
        assertFalse(criterionOfProductSearch.contains(""))
    }

    @Test
    fun isValidCriterionForProductSearchTrueWhenCategoryExists(){
        assertTrue(utils.isValidCriterionForProductSearch("id"))
        assertTrue(utils.isValidCriterionForProductSearch("price"))
        assertTrue(utils.isValidCriterionForProductSearch("orderid"))
        assertTrue(utils.isValidCriterionForProductSearch("ID"))
    }

    @Test
    fun isValidCriterionForProductSearchForProductSearchFalseWhenCategoryDoesNotExist(){
        assertFalse(isValidCriterionForProductSearch("i"))
        assertFalse(isValidCriterionForProductSearch("oed"))
        assertFalse(isValidCriterionForProductSearch(""))
    }
}