package controllers

import ie.setu.controllers.OrderAPI
import ie.setu.controllers.ProductAPI
import ie.setu.models.Order
import ie.setu.models.Product
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import persistence.XMLSerializer
import java.io.File
class OrderAPITest
{
    private var orderActive1: Order? = null
    private var orderActive2: Order? = null
    private var orderInactive1: Order? = null
    private var orderInactive2: Order? = null
    private var NotStoredProduct1: Product? = null
    private var NotStoredProduct2: Product? = null
    private var StoredProduct1: Product? = null
    private var StoredProduct2: Product? = null
    private var productPopulate: ProductAPI? = ProductAPI(XMLSerializer(File("notes.xml")))
    private var populatedOrders: OrderAPI? = OrderAPI(productPopulate!!,XMLSerializer(File("prders.xml")))
    private var emptyOrders: OrderAPI? = OrderAPI(productPopulate!!,XMLSerializer(File("empty-orderss.xml")))

    @BeforeEach
    fun setup(){
        orderActive1 = Order(0, "Anton",  true)
        orderActive2 = Order(1, "Dima",  true)
        orderInactive1 = Order(2, "Sergey",  false)
        orderInactive2 = Order(3, "John",  false)

        //adding 5 Note to the notes api
        populatedOrders!!.addOrder(orderActive1!!)
        populatedOrders!!.addOrder(orderActive2!!)
        populatedOrders!!.addOrder(orderInactive1!!)
        populatedOrders!!.addOrder(orderInactive2!!)

        NotStoredProduct1 =Product(0,"iphone",64,250.5,-1)
        NotStoredProduct2 =Product(1,"iphone",64,250.5,-1)
        StoredProduct1 =Product(2,"honor",128,250.5,1)
        StoredProduct2 =Product(3,"iphone",516,250.5,3)

        productPopulate!!.addProduct(NotStoredProduct1!!)
        productPopulate!!.addProduct(NotStoredProduct2!!)
        productPopulate!!.addProduct(StoredProduct1!!)
        productPopulate!!.addProduct(StoredProduct2!!)


    }

    @AfterEach
    fun tearDown(){
        orderActive1 = null
        orderActive2 = null
        orderInactive1 = null
        orderInactive2 = null

        populatedOrders = null
        emptyOrders = null

        NotStoredProduct1 = null
        NotStoredProduct2 = null
        StoredProduct1 = null
        StoredProduct2 = null

    }

    @Nested
    inner class AddOrder {
        @Test
        fun `adding a Order to a populated list adds to ArrayList`() {
            val newOrder = Order(4,"Tolic",  true)
            assertEquals(4, populatedOrders!!.numberOfOrder())
            assertTrue(populatedOrders!!.addOrder(newOrder))
            assertEquals(5, populatedOrders!!.numberOfOrder())
            val result = populatedOrders!!.SerchByCriteria("id",4)
            assertEquals( result.first(),newOrder)
        }
        @Test
        fun `adding a Order to a empty list adds to ArrayList`() {
            val newOrder = Order(0,"Tolic",  true)
            assertEquals(0, emptyOrders!!.numberOfOrder())
            assertTrue(emptyOrders!!.addOrder(newOrder))
            assertEquals(1, emptyOrders!!.numberOfOrder())
            val result = emptyOrders!!.SerchByCriteria("id",0)
            assertEquals( result.first(),newOrder)
        }


    }

    @Nested

    inner class ShowOrders {
        @Test
        fun `is return no orders if empty list`(){
            assertEquals(0,emptyOrders!!.numberOfOrder())
            assertTrue(emptyOrders!!.showOrder().contains("No Orders stored"))
        }

        @Test
        fun `check is showOrders Work`(){

            assertEquals(4, populatedOrders!!.numberOfOrder())

            assertFalse(populatedOrders!!.showOrder().contains("No Orders stored"))
            assertTrue( populatedOrders!!.showOrder().contains("Anton"))
            assertTrue( populatedOrders!!.showOrder().contains("Dima"))
            assertTrue( populatedOrders!!.showOrder().contains("John"))

        }

    }

    @Nested
    inner class ShowActiceOrders {
        @Test
        fun `is return no active orders if empty list`(){
            assertEquals(0,emptyOrders!!.numberOfActiveOrder())
            assertTrue(emptyOrders!!.showOrderActive().contains("No Active Orders stored"))
        }

        @Test
        fun `check is showActiveOrders Work`(){

            assertEquals(2, populatedOrders!!.numberOfActiveOrder())


            assertTrue( populatedOrders!!.showOrderActive().contains("Anton"))
            assertTrue( populatedOrders!!.showOrderActive().contains("Dima"))



        }

    }


    @Nested
    inner class ShowInactiveOrders {
        @Test
        fun `is return no Inactive orders if empty list`(){
            assertEquals(0,emptyOrders!!.numberOfINactiveOrder())
            assertTrue(emptyOrders!!.showOrderINactive().contains("No Inactive Orders stored"))
        }

        @Test
        fun `check is showInactiveOrders Work`(){

            assertEquals(2, populatedOrders!!.numberOfINactiveOrder())


            assertTrue( populatedOrders!!.showOrderINactive().contains("Sergey"))
            assertTrue( populatedOrders!!.showOrderINactive().contains("John"))



        }

    }

    @Nested
    inner class DeleteOrders {
        @Test
        fun `is return false when u delete empty list`(){
            assertFalse(emptyOrders!!.delateOrder(1))

        }

        @Test
        fun `check is delete order Work`(){

            assertEquals(4, populatedOrders!!.numberOfOrder())
            val result = populatedOrders!!.delateOrder(3)

            assertTrue(result)
            assertEquals(3, populatedOrders!!.numberOfOrder())
            val result2 = populatedOrders!!.SerchByCriteria("id",3)
            assertTrue( result2.isEmpty())


        }


    }

    @Nested
    inner class Update {
        @Test
        fun `is return false when u update empty list`(){
            assertFalse(emptyOrders!!.updateOrder(1,"Sasha"))

        }

        @Test
        fun `check is update order Work`(){
            val result1 = populatedOrders!!.showByCriteria("id",3)
            assertTrue( result1.contains("John"))
            assertTrue(populatedOrders!!.updateOrder(3,"Sasha"))
            populatedOrders!!.updateOrder(3,"Sasha")
            val result2 = populatedOrders!!.showByCriteria("id",3)

            assertTrue( result2.contains("Sasha"))


        }

    }


    @Nested
    inner class checkOfNumber {
        @Test
        fun `is return false when u checkOfNumber of  empty list`(){
            assertFalse(emptyOrders!!.checkAreNumberOrder())
            assertEquals(0,emptyOrders!!.numberOfOrder())
        }

        @Test
        fun `check is checkOfNumber order  Work`(){

            assertTrue(populatedOrders!!.checkAreNumberOrder())
            assertEquals(4,populatedOrders!!.numberOfOrder())

        }

    }
    @Nested
    inner class checkOfActiveNumber {
        @Test
        fun `is return false when u checkOfActiveNumber of  empty list`(){
            assertFalse(emptyOrders!!.checkOfNumberActiveOrder())
            assertEquals(0,emptyOrders!!.numberOfActiveOrder())
        }

        @Test
        fun `check is checkOfActiveNumber order  Work`(){

            assertTrue(populatedOrders!!.checkOfNumberActiveOrder())
            assertEquals(2,populatedOrders!!.numberOfActiveOrder())

        }

    }

    @Nested
    inner class checkOfInactiveNumber {
        @Test
        fun `is return false when u checkOfInactiveNumber of  empty list`(){
            assertFalse(emptyOrders!!.checkOfNumberInactiveOrder())
            assertEquals(0,emptyOrders!!.numberOfINactiveOrder())
        }

        @Test
        fun `check is checkOfInactiveNumber order  Work`(){

            assertTrue(populatedOrders!!.checkOfNumberInactiveOrder())
            assertEquals(2,populatedOrders!!.numberOfINactiveOrder())

        }

    }

    @Nested
    inner class isValidIdAllType {
        @Test
        fun `is return false when u check Of IsValId of  empty list`(){
            assertFalse(emptyOrders!!.isValidID(1))

        }

        @Test
        fun `check is check Of checkOfIsValId order  Work`(){
            assertTrue(populatedOrders!!.isValidID(1))
            val result1 = populatedOrders!!.showByCriteria("id",1)
            assertTrue( result1.contains("1"))

        }

        @Test
        fun `is return false when u check isValidActiveID of  empty list`(){
            assertFalse(emptyOrders!!.isValidActiveID(1))

        }

        @Test
        fun `check is check Of isValidActiveID order  Work`(){
            assertTrue(populatedOrders!!.isValidActiveID(1))
            val result1 = populatedOrders!!.showByCriteria("id",1)
            assertTrue( result1.contains("true"))
        }

        @Test
        fun `is return false when u check isValidInactiveID of  empty list`(){
            assertFalse(emptyOrders!!.isValidInactiveID(1))

        }

        @Test
        fun `check is check Of isValidInactiveID order  Work`(){
            assertTrue(populatedOrders!!.isValidInactiveID(3))
            val result1 = populatedOrders!!.showByCriteria("id",3)
            assertTrue( result1.contains("false"))

        }

    }
    @Nested
    inner class CheckIsValidIdAllType {
        @Test
        fun `is return false when u checkOfIsValId of  empty list`(){
            assertFalse(emptyOrders!!.checkIsOrderValidID(1))

        }
        @Test
        fun `check is check Of checkOfIsValId order  Work`(){
            assertTrue(populatedOrders!!.checkIsOrderValidID(3))
            val result1 = populatedOrders!!.showByCriteria("id",3)
            assertTrue( result1.contains("3"))
        }

        @Test
        fun `is return false when u checkIsValidActiveID of  empty list`(){
            assertFalse(emptyOrders!!.checkIsValidActiveID(1))

        }
        @Test
        fun `check is check Of checkIsValidActiveID order  Work`(){
            assertTrue(populatedOrders!!.checkIsValidActiveID(1))
            val result1 = populatedOrders!!.showByCriteria("id",1)
            assertTrue( result1.contains("true"))
        }
        @Test
        fun `is return false when u checkIsValidInactiveID of  empty list`(){
            assertFalse(emptyOrders!!.checkIsValidInactiveID(1))

        }
        @Test
        fun `check is check Of checkIsValidInactiveID order  Work`(){
            assertTrue(populatedOrders!!.checkIsValidInactiveID(3))
            val result1 = populatedOrders!!.showByCriteria("id",3)
            assertTrue( result1.contains("false"))
        }

    }

    @Nested
    inner class sortOrderByCategory {
        @Test
        fun `check is sortOrderByCategory work`(){
            populatedOrders!!.sortOrderByCategory("id")
            populatedOrders!!.sortOrderByCategory("name")
            populatedOrders!!.sortOrderByCategory("nadsadme")
        }
        @Test
        fun `check is sortOrderByCategory empty list`(){
            emptyOrders!!.sortOrderByCategory("id")

        }
    }

    @Nested
    inner class SerchByCriteriaTest {
        @Test
        fun `check is run SerchByCriteria for empty list`(){
            assertEquals(0,emptyOrders!!.numberOfOrder())
            val result =emptyOrders!!.SerchByCriteria("id",1)
            assertTrue(result.isEmpty())
        }
        @Test
        fun `check is run SearchByCriteria for with populatedOrders work correct`(){

            val result1 =populatedOrders!!.SerchByCriteria("name","Dima")
            assertTrue(result1.isNotEmpty())
            val result2 =populatedOrders!!.showByCriteria("name","Dima")
            assertTrue(result2.contains("Dima"))
            val result3 =populatedOrders!!.SerchByCriteria("id",1)
            assertTrue(result3.isNotEmpty())
            val result4 =populatedOrders!!.showByCriteria("id",1)
            assertTrue(result4.contains("Dima"))

        }
        @Test
        fun `check is run SearchByCriteria for with populatedOrders with wrong criteria give empty`(){

            val result1 =populatedOrders!!.SerchByCriteria("asdas","Dima")
            assertTrue(result1.isEmpty())
            val result2 =populatedOrders!!.SerchByCriteria("id","adsd")
            assertTrue(result2.isEmpty())


        }
    }


    @Nested
    inner class showByCriteriaTest {
        @Test
        fun `check is run showByCriteriaTest for empty list`(){
            assertEquals(0,emptyOrders!!.numberOfOrder())
            val result =emptyOrders!!.SerchByCriteria("id",1)
            assertTrue(result.isEmpty())
        }
        @Test
        fun `check is run showByCriteriaTest for with populatedOrders work correct`(){


            val result2 =populatedOrders!!.showByCriteria("name","Dima")
            assertTrue(result2.contains("Dima"))

            val result4 =populatedOrders!!.showByCriteria("id",1)
            assertTrue(result4.contains("Dima"))

        }
        @Test
        fun `check is run SearchByCriteria for with showByCriteriaTest with wrong criteria give empty`(){
            val result =populatedOrders!!.showByCriteria("id","1")


        }
    }

    @Nested
    inner class switchActiveStatusTest {
        @Test
        fun `check is run switchActiveStatus for empty list`(){
            assertEquals(0,emptyOrders!!.numberOfOrder())
            assertFalse(emptyOrders!!.swithcActiveStatus(1))
        }
        @Test
        fun `check is run showByCriteriaTest for with populatedOrders work correct`(){
            assertTrue(populatedOrders!!.checkIsValidActiveID(1))
            assertTrue(populatedOrders!!.swithcActiveStatus(1))
            assertFalse(populatedOrders!!.checkIsValidActiveID(1))

        }



    }



    @Nested
    inner class PersistenceTests
    {

        @Test
        fun `saving and loading an empty collection in XML doesn't crash app`()
        {
            // Saving an empty notes.XML file.
            val storingOrder = OrderAPI(productPopulate!!,XMLSerializer(File("orders.xml")))
            storingOrder.store()

            //Loading the empty notes.xml file into a new object
            val loadedOrder = OrderAPI(productPopulate!!,XMLSerializer(File("orders.xml")))
            loadedOrder.load()

            //Comparing the source of the notes (storingNotes) with the XML loaded notes (loadedNotes)
            assertEquals(0, storingOrder.numberOfOrder())
            assertEquals(0, loadedOrder.numberOfOrder())
            assertEquals(storingOrder.numberOfOrder(), loadedOrder.numberOfOrder())
        }
    }
    @Test
    fun `saving and loading an loaded collection in XML doesn't loose data`() {
        // Storing 3 notes to the notes.XML file.
        val storingOrder = OrderAPI(productPopulate!!,XMLSerializer(File("orders.xml")))
        storingOrder.addOrder(orderInactive1!!)
        storingOrder.addOrder(orderActive1!!)
        storingOrder.store()

        //Loading notes.xml into a different collection
        val loadedOrder = OrderAPI(productPopulate!!,XMLSerializer(File("orders.xml")))
        loadedOrder.load()

        //Comparing the source of the notes (storingNotes) with the XML loaded notes (loadedNotes)
        assertEquals(2, storingOrder.numberOfOrder())
        assertEquals(2, loadedOrder.numberOfOrder())
        assertEquals(storingOrder.numberOfOrder(), loadedOrder.numberOfOrder())
        assertEquals(storingOrder.SerchByCriteria("id",0), storingOrder.SerchByCriteria("id",0))
        assertEquals(storingOrder.SerchByCriteria("id",2), storingOrder.SerchByCriteria("id",2))
    }

    }