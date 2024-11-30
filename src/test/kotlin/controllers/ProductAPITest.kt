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
class ProductAPITest
{
    private var orderActive1: Order? = null
    private var orderActive2: Order? = null
    private var orderInactive1: Order? = null
    private var orderInactive2: Order? = null
    private var NotStoredProduct1: Product? = null
    private var NotStoredProduct2: Product? = null
    private var StoredProduct1: Product? = null
    private var StoredProduct2: Product? = null
    private var populateProduct: ProductAPI? = ProductAPI(XMLSerializer(File("notes.xml")))

    private var emptyProduct: ProductAPI? = ProductAPI( XMLSerializer(File("empty-orderss.xml")))
    private var populatedOrders: OrderAPI? = OrderAPI(populateProduct!!, XMLSerializer(File("prders.xml")))
    @BeforeEach
    fun setup()
    {
        orderActive1 = Order(0, "Anton", true)
        orderActive2 = Order(1, "Dima", true)
        orderInactive1 = Order(2, "Sergey", false)
        orderInactive2 = Order(3, "John", false)

        //adding 5 Note to the notes api
        populatedOrders!!.addOrder(orderActive1!!)
        populatedOrders!!.addOrder(orderActive2!!)
        populatedOrders!!.addOrder(orderInactive1!!)
        populatedOrders!!.addOrder(orderInactive2!!)

        NotStoredProduct1 = Product(0, "iphone", 64, 250.5, -1)
        NotStoredProduct2 = Product(1, "iphone", 64, 250.5, -1)
        StoredProduct1 = Product(2, "honor", 128, 255.5, 1)
        StoredProduct2 = Product(3, "iphone", 516, 250.5, 3)

        populateProduct!!.addProduct(NotStoredProduct1!!)
        populateProduct!!.addProduct(NotStoredProduct2!!)
        populateProduct!!.addProduct(StoredProduct1!!)
        populateProduct!!.addProduct(StoredProduct2!!)


    }

    @AfterEach
    fun tearDown()
    {
        orderActive1 = null
        orderActive2 = null
        orderInactive1 = null
        orderInactive2 = null

        populatedOrders = null
        emptyProduct = null

        NotStoredProduct1 = null
        NotStoredProduct2 = null
        StoredProduct1 = null
        StoredProduct2 = null

    }




    @Nested
    inner class AddProduct {
        @Test
        fun `adding a Order to a populated list adds to ArrayList`() {
            val newProduct = Product(4,"Lenovo",  128,1000.0,-1)
            assertEquals(4, populateProduct!!.numberOfProduct())
            assertTrue(populateProduct!!.addProduct(newProduct))
            assertEquals(5, populateProduct!!.numberOfProduct())
            val result = populateProduct!!.SerchByCriteria("id",4)
            assertEquals( result.first(),newProduct)
        }
        @Test
        fun `adding a Order to a empty list adds to ArrayList`() {
            val newProduct = Product(4,"Lenovo",  128,1000.0,-1)
            assertEquals(0, emptyProduct!!.numberOfProduct())
            assertTrue(emptyProduct!!.addProduct(newProduct))
            assertEquals(1, emptyProduct!!.numberOfProduct())
            val result = emptyProduct!!.SerchByCriteria("id",0)
            assertEquals( result.first(),newProduct)
        }


    }
}
