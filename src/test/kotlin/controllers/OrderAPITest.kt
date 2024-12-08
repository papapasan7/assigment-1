package controllers

import ie.setu.models.Product
import models.Order
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import persistence.XMLSerializer
import java.io.File
class OrderAPITest {
    private var orderActive1: Order? = null
    private var orderActive2: Order? = null
    private var orderInactive1: Order? = null
    private var orderInactive2: Order? = null
    private var notStoredProduct1: Product? = null
    private var notStoredProduct2: Product? = null
    private var storedProduct1: Product? = null
    private var storedProduct2: Product? = null
    private var productPopulate: ProductAPI? = ProductAPI(XMLSerializer(File("product.xml")))
    private var populatedOrders: OrderAPI? = OrderAPI(productPopulate!!, XMLSerializer(File("orders.xml")))
    private var emptyOrders: OrderAPI? = OrderAPI(productPopulate!!, XMLSerializer(File("empty-orders.xml")))

    @BeforeEach
    fun setup() {
        orderActive1 = Order(0, "Anton", true)
        orderActive2 = Order(1, "Dima", true)
        orderInactive1 = Order(2, "Sergey", false)
        orderInactive2 = Order(3, "John", false)

        // adding 5 Note to the notes api
        populatedOrders!!.addOrder(orderActive1!!)
        populatedOrders!!.addOrder(orderActive2!!)
        populatedOrders!!.addOrder(orderInactive1!!)
        populatedOrders!!.addOrder(orderInactive2!!)

        notStoredProduct1 = Product(0, "iphone", 64, 250.5, -1)
        notStoredProduct2 = Product(1, "iphone", 64, 250.5, -1)
        storedProduct1 = Product(2, "honor", 128, 250.5, 1)
        storedProduct2 = Product(3, "iphone", 516, 250.5, 3)

        productPopulate!!.addProduct(notStoredProduct1!!)
        productPopulate!!.addProduct(notStoredProduct2!!)
        productPopulate!!.addProduct(storedProduct1!!)
        productPopulate!!.addProduct(storedProduct2!!)
    }

    @AfterEach
    fun tearDown() {
        orderActive1 = null
        orderActive2 = null
        orderInactive1 = null
        orderInactive2 = null

        populatedOrders = null
        emptyOrders = null

        notStoredProduct1 = null
        notStoredProduct2 = null
        storedProduct1 = null
        storedProduct2 = null
    }

    @Nested
    inner class AddOrder {
        @Test
        fun `adding a Order to a populated list adds to ArrayList`() {
            val newOrder = Order(4, "Tolic", true)
            assertEquals(4, populatedOrders!!.numberOfOrder())
            assertFalse(populatedOrders!!.validIdGen())
            assertTrue(populatedOrders!!.addOrder(newOrder))
            assertEquals(5, populatedOrders!!.numberOfOrder())
            val result = populatedOrders!!.searchByCriteria("id", 4)
            assertEquals(result.first(), newOrder)
        }

        @Test
        fun `adding a Order to a empty list adds to ArrayList`() {
            val newOrder = Order(0, "Tolic", true)
            assertEquals(0, emptyOrders!!.numberOfOrder())
            assertTrue(emptyOrders!!.addOrder(newOrder))
            assertEquals(1, emptyOrders!!.numberOfOrder())
            val result = emptyOrders!!.searchByCriteria("id", 0)
            assertEquals(result.first(), newOrder)
        }
    }

    @Nested

    inner class ShowOrders {
        @Test
        fun `is return no orders if empty list`() {
            assertEquals(0, emptyOrders!!.numberOfOrder())
            assertTrue(emptyOrders!!.showOrder().contains("No Orders stored"))
        }

        @Test
        fun `check is showOrders Work`() {
            assertEquals(4, populatedOrders!!.numberOfOrder())

            assertFalse(populatedOrders!!.showOrder().contains("No Orders stored"))
            assertTrue(populatedOrders!!.showOrder().contains("Anton"))
            assertTrue(populatedOrders!!.showOrder().contains("Dima"))
            assertTrue(populatedOrders!!.showOrder().contains("John"))
        }
    }

    @Nested
    inner class ShowActiveOrders {
        @Test
        fun `is return no active orders if empty list`() {
            assertEquals(0, emptyOrders!!.numberOfActiveOrder())
            assertTrue(emptyOrders!!.showOrderActive().contains("No Active Orders stored"))
        }

        @Test
        fun `check is showActiveOrders Work`() {
            assertEquals(2, populatedOrders!!.numberOfActiveOrder())

            assertTrue(populatedOrders!!.showOrderActive().contains("Anton"))
            assertTrue(populatedOrders!!.showOrderActive().contains("Dima"))
        }
    }

    @Nested
    inner class ShowInactiveOrders {
        @Test
        fun `is return no Inactive orders if empty list`() {
            assertEquals(0, emptyOrders!!.numberOfInactiveOrder())
            assertTrue(emptyOrders!!.showOrderInactive().contains("No Inactive Orders stored"))
        }

        @Test
        fun `check is showInactiveOrders Work`() {
            assertEquals(2, populatedOrders!!.numberOfInactiveOrder())

            assertTrue(populatedOrders!!.showOrderInactive().contains("Sergey"))
            assertTrue(populatedOrders!!.showOrderInactive().contains("John"))
        }
    }

    @Nested
    inner class DeleteOrdersTest {
        @Test
        fun `is return false when u delete empty list`() {
            assertEquals(0, emptyOrders!!.numberOfOrder())
            assertFalse(emptyOrders!!.deleteOrder(1))
        }

        @Test
        fun `check is delete order Work`() {
            assertEquals(4, populatedOrders!!.numberOfOrder())
            val result = populatedOrders!!.deleteOrder(3)

            assertTrue(result)
            assertEquals(3, populatedOrders!!.numberOfOrder())
            val result2 = populatedOrders!!.searchByCriteria("id", 3)
            assertTrue(result2.isEmpty())
        }

        @Test
        fun `is return false when u  delete  order with wrong id `() {
            assertEquals(4, populatedOrders!!.numberOfOrder())
            val result2 = populatedOrders!!.searchByCriteria("id", 4)
            assertTrue(result2.isEmpty())
            val result = populatedOrders!!.deleteOrder(4)
            assertFalse(result)
        }
    }

    @Nested
    inner class UpdateTest {
        @Test
        fun `is return false when u update empty list`() {
            assertEquals(0, emptyOrders!!.numberOfOrder())
            assertFalse(emptyOrders!!.updateOrder(1, "Sasha"))
        }

        @Test
        fun `is return false when u  update  order with wrong id`() {
            val result1 = populatedOrders!!.showByCriteria("id", 4)
            assertTrue(result1.contains("no order with 4 element in id"))
            assertFalse(populatedOrders!!.updateOrder(4, "Sasha"))
            populatedOrders!!.updateOrder(4, "Sasha")
            val result2 = populatedOrders!!.showByCriteria("id", 4)

            assertTrue(result2.contains("no order with 4 element in id"))
        }

        @Test
        fun `check is update order Work`() {
            val result1 = populatedOrders!!.showByCriteria("id", 3)
            assertTrue(result1.contains("John"))
            assertTrue(populatedOrders!!.updateOrder(3, "Sasha"))
            populatedOrders!!.updateOrder(3, "Sasha")
            val result2 = populatedOrders!!.showByCriteria("id", 3)

            assertTrue(result2.contains("Sasha"))
        }
    }

    @Nested
    inner class CheckOfNumberTest {
        @Test
        fun `is return false when u checkOfNumber of  empty list`() {
            assertFalse(emptyOrders!!.checkAreNumberOrder())
            assertEquals(0, emptyOrders!!.numberOfOrder())
        }

        @Test
        fun `check is checkOfNumber order  Work`() {
            assertTrue(populatedOrders!!.checkAreNumberOrder())
            assertEquals(4, populatedOrders!!.numberOfOrder())
        }
    }

    @Nested
    inner class CheckOfActiveNumberTest {
        @Test
        fun `is return false when u checkOfActiveNumber of  empty list`() {
            assertFalse(emptyOrders!!.checkOfNumberActiveOrder())
            assertEquals(0, emptyOrders!!.numberOfActiveOrder())
        }

        @Test
        fun `check is checkOfActiveNumber order  Work`() {
            assertTrue(populatedOrders!!.checkOfNumberActiveOrder())
            assertEquals(2, populatedOrders!!.numberOfActiveOrder())
        }
    }

    @Nested
    inner class CheckOfInactiveNumberTest {
        @Test
        fun `is return false when u checkOfInactiveNumber of  empty list`() {
            assertFalse(emptyOrders!!.checkOfNumberInactiveOrder())
            assertEquals(0, emptyOrders!!.numberOfInactiveOrder())
        }

        @Test
        fun `check is checkOfInactiveNumber order  Work`() {
            assertTrue(populatedOrders!!.checkOfNumberInactiveOrder())
            assertEquals(2, populatedOrders!!.numberOfInactiveOrder())
        }
    }

    @Nested
    inner class IsValidIdAllTypeTest {
        @Test
        fun `is return false when u check Of IsValId of  empty list`() {
            assertFalse(emptyOrders!!.isValidID(1))
        }

        @Test
        fun `check is  checkOfIsValId order  Work`() {
            assertTrue(populatedOrders!!.isValidID(1))
            val result1 = populatedOrders!!.showByCriteria("id", 1)
            assertTrue(result1.contains("1"))
        }

        @Test
        fun `check if isValId order  give false if u input wrong  id`() {
            assertFalse(populatedOrders!!.isValidID(5))
            val result1 = populatedOrders!!.searchByCriteria("id", 5)
            assertTrue(result1.isEmpty())
        }

        @Test
        fun `is return false when u check isValidActiveID of  empty list`() {
            assertFalse(emptyOrders!!.isValidActiveID(1))
        }

        @Test
        fun `check if isValidActiveID order  Work`() {
            assertTrue(populatedOrders!!.isValidActiveID(1))
            val result1 = populatedOrders!!.showByCriteria("id", 1)
            assertTrue(result1.contains("true"))
        }

        @Test
        fun `check if isValidActiveID order  give false if u input wrong active id`() {
            assertFalse(populatedOrders!!.isValidActiveID(3))
            val result1 = populatedOrders!!.showByCriteria("id", 3)
            assertTrue(result1.contains("false"))
        }

        @Test
        fun `is return false when u check isValidInactiveID of  empty list`() {
            assertFalse(emptyOrders!!.isValidInactiveID(1))
        }

        @Test
        fun `check if isValidInactiveID order  Work`() {
            assertTrue(populatedOrders!!.isValidInactiveID(3))
            val result1 = populatedOrders!!.showByCriteria("id", 3)
            assertTrue(result1.contains("false"))
        }

        @Test
        fun `check if isValidInactiveID order  give false if u input wrong Inactive id`() {
            assertFalse(populatedOrders!!.isValidInactiveID(1))
            val result1 = populatedOrders!!.showByCriteria("id", 1)
            assertTrue(result1.contains("true"))
        }
    }

    @Nested
    inner class CheckIsValidIdAllType {
        @Test
        fun `is return false when u checkOfIsValId of  empty list`() {
            assertFalse(emptyOrders!!.checkIsOrderValidID(1))
        }

        @Test
        fun `check is checkOfIsValId of order  Work`() {
            assertTrue(populatedOrders!!.checkIsOrderValidID(3))
            val result1 = populatedOrders!!.showByCriteria("id", 3)
            assertTrue(result1.contains("3"))
        }

        @Test
        fun `check if checkOfIsValId of order give false when u input  wrong id`() {
            assertFalse(populatedOrders!!.checkIsOrderValidID(5))
            val result1 = populatedOrders!!.searchByCriteria("id", 5)
            assertTrue(result1.isEmpty())
        }

        @Test
        fun `is return false when u checkIsValidActiveID of  empty list`() {
            assertFalse(emptyOrders!!.checkIsValidActiveID(1))
        }

        @Test
        fun `check if  checkIsValidActiveID of active order  Work`() {
            assertTrue(populatedOrders!!.checkIsValidActiveID(1))
            val result1 = populatedOrders!!.showByCriteria("id", 1)
            assertTrue(result1.contains("true"))
        }

        @Test
        fun `check if checkIsValidActiveID of order give false when u input  wrong active id`() {
            assertFalse(populatedOrders!!.checkIsValidActiveID(3))
            val result1 = populatedOrders!!.showByCriteria("id", 3)
            assertTrue(result1.contains("false"))
        }

        @Test
        fun `is return false when u checkIsValidInactiveID of  empty list`() {
            assertFalse(emptyOrders!!.checkIsValidInactiveID(1))
        }

        @Test
        fun `check is check Of checkIsValidInactiveID order  Work`() {
            assertTrue(populatedOrders!!.checkIsValidInactiveID(3))
            val result1 = populatedOrders!!.showByCriteria("id", 3)
            assertTrue(result1.contains("false"))
        }

        @Test
        fun `check if checkIsValidActiveID of order give false when u input  wrong inactive id`() {
            assertFalse(populatedOrders!!.checkIsValidInactiveID(1))
            val result1 = populatedOrders!!.showByCriteria("id", 1)
            assertTrue(result1.contains("true"))
        }
    }

    @Nested
    inner class SortOrderByCategory {
        @Test
        fun `check is sortOrderByCategory work`() {
            val result = populatedOrders!!.showOrder()
            populatedOrders!!.sortOrderByCategory("id")
            val result2 = populatedOrders!!.showOrder()
            assertEquals(result, result2)
            populatedOrders!!.sortOrderByCategory("name")
            val result3 = populatedOrders!!.showOrder()
            assertNotEquals(result, result3)
            populatedOrders!!.sortOrderByCategory("nadsadme")
            val result4 = populatedOrders!!.showOrder()
            assertEquals(result4, result)
        }

        @Test
        fun `check is sortOrderByCategory empty list`() {
            emptyOrders!!.sortOrderByCategory("id")
        }
    }

    @Nested
    inner class SearchByCriteriaTest {
        @Test
        fun `check is run SearchByCriteria for empty list`() {
            assertEquals(0, emptyOrders!!.numberOfOrder())
            val result = emptyOrders!!.searchByCriteria("id", 1)
            assertTrue(result.isEmpty())
        }

        @Test
        fun `check is run SearchByCriteria for with populatedOrders work correct`() {
            val result1 = populatedOrders!!.searchByCriteria("name", "Dima")
            assertTrue(result1.isNotEmpty())
            val result2 = populatedOrders!!.showByCriteria("name", "Dima")
            assertTrue(result2.contains("Dima"))
            val result3 = populatedOrders!!.searchByCriteria("id", 1)
            assertTrue(result3.isNotEmpty())
            val result4 = populatedOrders!!.showByCriteria("id", 1)
            assertTrue(result4.contains("Dima"))
        }

        @Test
        fun `check is run SearchByCriteria   with wrong searchElement give empty`() {
            val result = populatedOrders!!.searchByCriteria("id", "1")
            assertTrue(result.isEmpty())
            val result2 = populatedOrders!!.showByCriteria("id", "1")
            assertTrue(result2.contains("no order with 1 element in id"))
        }

        @Test
        fun `check is run SearchByCriteria   with wrong criteria give empty`() {
            val result = populatedOrders!!.searchByCriteria("isadasd", 1)
            assertTrue(result.isEmpty())
            val result2 = populatedOrders!!.showByCriteria("isadasd", 1)
            assertTrue(result2.contains("no order with 1 element in isadasd"))
        }
    }

    @Nested
    inner class ShowByCriteriaTest {
        @Test
        fun `check is run showByCriteriaTest for empty list`() {
            assertEquals(0, emptyOrders!!.numberOfOrder())
            val result = emptyOrders!!.showByCriteria("id", 1)
            assertTrue(result.contains("no order with 1 element in id"))
        }

        @Test
        fun `check is run showByCriteriaTest for  populatedOrders work correct`() {
            val result2 = populatedOrders!!.showByCriteria("name", "Dima")
            assertTrue(result2.contains("Dima"))

            val result4 = populatedOrders!!.showByCriteria("id", 1)
            assertTrue(result4.contains("Dima"))
        }

        @Test
        fun `check is run showByCriteriaTest   with wrong searchElement give empty`() {
            val result2 = populatedOrders!!.showByCriteria("id", "1")
            assertTrue(result2.contains("no order with 1 element in id"))
        }

        @Test
        fun `check is run showByCriteriaTest   with wrong criteria give empty`() {
            val result2 = populatedOrders!!.showByCriteria("isadasd", 1)
            assertTrue(result2.contains("no order with 1 element in isadasd"))
        }
    }

    @Nested
    inner class SwitchActiveStatusTest {
        @Test
        fun `check is run switchActiveStatus for empty list`() {
            assertEquals(0, emptyOrders!!.numberOfOrder())
            assertFalse(emptyOrders!!.switchActiveStatus(1))
        }

        @Test
        fun `check is run showByCriteriaTest for with populatedOrders work correct`() {
            assertTrue(populatedOrders!!.checkIsValidActiveID(1))
            assertTrue(populatedOrders!!.switchActiveStatus(1))
            assertFalse(populatedOrders!!.checkIsValidActiveID(1))
        }
    }

    @Nested
    inner class PersistenceTests {

        @Test
        fun `saving and loading an empty collection in XML doesn't crash app`() {
            // Saving an empty notes.XML file.
            val storingOrder = OrderAPI(productPopulate!!, XMLSerializer(File("orders.xml")))
            storingOrder.store()

            // Loading the empty notes.xml file into a new object
            val loadedOrder = OrderAPI(productPopulate!!, XMLSerializer(File("orders.xml")))
            loadedOrder.load()

            // Comparing the source of the notes (storingNotes) with the XML loaded notes (loadedNotes)
            assertEquals(0, storingOrder.numberOfOrder())
            assertEquals(0, loadedOrder.numberOfOrder())
            assertEquals(storingOrder.numberOfOrder(), loadedOrder.numberOfOrder())
        }
    }

    @Test
    fun `saving and loading an loaded collection in XML doesn't loose data`() {
        // Storing 3 notes to the notes.XML file.
        val storingOrder = OrderAPI(productPopulate!!, XMLSerializer(File("orders.xml")))
        assertTrue(storingOrder.validIdGen())
        storingOrder.addOrder(orderInactive1!!)
        storingOrder.addOrder(orderActive1!!)
        storingOrder.store()

        // Loading notes.xml into a different collection
        val loadedOrder = OrderAPI(productPopulate!!, XMLSerializer(File("orders.xml")))
        loadedOrder.load()

        // Comparing the source of the notes (storingNotes) with the XML loaded notes (loadedNotes)
        assertEquals(2, storingOrder.numberOfOrder())
        assertEquals(2, loadedOrder.numberOfOrder())
        assertEquals(storingOrder.numberOfOrder(), loadedOrder.numberOfOrder())
        assertEquals(storingOrder.searchByCriteria("id", 0), storingOrder.searchByCriteria("id", 0))
        assertEquals(storingOrder.searchByCriteria("id", 2), storingOrder.searchByCriteria("id", 2))
    }
}
