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
    fun isValidCategoryTrueWhenCategoryExists(){
        assertTrue(utils.isValidCategoryForProductSearch("id"))
        assertTrue(utils.isValidCategoryForProductSearch("price"))
        assertTrue(utils.isValidCategoryForProductSearch("orderid"))
        assertTrue(utils.isValidCategoryForProductSearch("ID"))
    }

    @Test
    fun isValidCategoryFalseWhenCategoryDoesNotExist(){
        assertFalse(isValidCategoryForProductSearch("i"))
        assertFalse(isValidCategoryForProductSearch("oed"))
        assertFalse(isValidCategoryForProductSearch(""))
    }
}