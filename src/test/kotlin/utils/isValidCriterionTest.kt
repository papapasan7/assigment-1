package utils




import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested

import org.junit.jupiter.api.Test

class isValidCategoryTest {

    @Nested
    inner class isValidCriterioyForProductTest
    {
        @Test
        fun isValidCriterionForProductReturnsFullCriterionSet()
        {
            assertEquals(4, criterionOfProduct.size)
            assertTrue(criterionOfProduct.contains("memory"))
            assertTrue(criterionOfProduct.contains("name"))
            assertTrue(criterionOfProduct.contains("id"))
            assertTrue(criterionOfProduct.contains("price"))
            assertFalse(criterionOfProduct.contains(""))
        }

        @Test
        fun isValidCriterionForProductTrueWhenCategoryExists()
        {
            assertTrue(utils.isValidCriterionForProduct("id"))
            assertTrue(utils.isValidCriterionForProduct("price"))
            assertTrue(utils.isValidCriterionForProduct("orderid"))
            assertTrue(utils.isValidCriterionForProduct("ID"))
        }

        @Test
        fun isValidCriterionForProductFalseWhenCategoryDoesNotExist()
        {
            assertFalse(isValidCriterionForProduct("i"))
            assertFalse(isValidCriterionForProduct("oed"))
            assertFalse(isValidCriterionForProduct(""))
        }
    }

    @Nested
    inner class isValidCriterioForOrderTest
    {
        @Test
        fun isValidCriterionForOrderReturnsFullCriterionSet()
        {
            assertEquals(2, criterionOfOrder.size)

            assertTrue(criterionOfOrder.contains("name"))
            assertTrue(criterionOfOrder.contains("id"))

            assertFalse(criterionOfOrder.contains(""))
        }

        @Test
        fun isValidCriterionForOrderTrueWhenCategoryExists()
        {
            assertTrue(isValidCriterionForOrder("id"))
            assertTrue(isValidCriterionForOrder("ID"))
        }

        @Test
        fun isValidCriterionForOrderFalseWhenCategoryDoesNotExist()
        {
            assertFalse(isValidCriterionForOrder("i"))
            assertFalse(isValidCriterionForOrder("oed"))
            assertFalse(isValidCriterionForOrder(""))
        }
    }
}
