package utils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class IsValidCriterionTest {

    @Nested
    inner class IsValidCriterionTest {
        @Test
        fun isValidCriterionForProductReturnsFullCriterionSet() {
            assertEquals(4, criterionOfProduct.size)
            assertTrue(criterionOfProduct.contains("memory"))
            assertTrue(criterionOfProduct.contains("name"))
            assertTrue(criterionOfProduct.contains("id"))
            assertTrue(criterionOfProduct.contains("price"))
            assertFalse(criterionOfProduct.contains(""))
        }

        @Test
        fun isValidCriterionForProductTrueWhenCategoryExists() {
            assertTrue(isValidCriterionForProduct("id"))
            assertTrue(isValidCriterionForProduct("price"))
            assertTrue(isValidCriterionForProduct("orderid"))
            assertTrue(isValidCriterionForProduct("ID"))
        }

        @Test
        fun isValidCriterionForProductFalseWhenCategoryDoesNotExist() {
            assertFalse(isValidCriterionForProduct("i"))
            assertFalse(isValidCriterionForProduct("oed"))
            assertFalse(isValidCriterionForProduct(""))
        }
    }

    @Nested
    inner class IsValidCriterioForOrderTest {
        @Test
        fun isValidCriterionForOrderReturnsFullCriterionSet() {
            assertEquals(2, criterionOfOrder.size)

            assertTrue(criterionOfOrder.contains("name"))
            assertTrue(criterionOfOrder.contains("id"))

            assertFalse(criterionOfOrder.contains(""))
        }

        @Test
        fun isValidCriterionForOrderTrueWhenCategoryExists() {
            assertTrue(isValidCriterionForOrder("id"))
            assertTrue(isValidCriterionForOrder("ID"))
        }

        @Test
        fun isValidCriterionForOrderFalseWhenCategoryDoesNotExist() {
            assertFalse(isValidCriterionForOrder("i"))
            assertFalse(isValidCriterionForOrder("oed"))
            assertFalse(isValidCriterionForOrder(""))
        }
    }
}
