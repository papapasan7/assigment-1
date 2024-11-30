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

    private var emptyProduct: ProductAPI? = ProductAPI(XMLSerializer(File("empty-orderss.xml")))
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
        StoredProduct2 = Product(3, "lenovo", 516, 250.5, 3)

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
    inner class AddProductTest
    {
        @Test
        fun `adding a Product to a populated list adds to ArrayList`()
        {
            val newProduct = Product(4, "Lenovo", 128, 1000.0, -1)
            assertEquals(4, populateProduct!!.numberOfProduct())
            assertTrue(populateProduct!!.addProduct(newProduct))
            assertEquals(5, populateProduct!!.numberOfProduct())
            val result = populateProduct!!.SerchByCriteria("id", 4)
            assertEquals(result.first(), newProduct)
        }

        @Test
        fun `adding a Product to a empty list adds to ArrayList`()
        {
            val newProduct = Product(4, "Lenovo", 128, 1000.0, -1)
            assertEquals(0, emptyProduct!!.numberOfProduct())
            assertTrue(emptyProduct!!.addProduct(newProduct))
            assertEquals(1, emptyProduct!!.numberOfProduct())
            val result = emptyProduct!!.SerchByCriteria("id", 0)
            assertEquals(result.first(), newProduct)
        }

    }

    @Nested

    inner class ShowProductTest
    {
        @Test
        fun `is return no product if empty list`()
        {
            assertEquals(0, emptyProduct!!.numberOfProduct())
            assertTrue(emptyProduct!!.showProduct().contains("No product stored"))
        }

        @Test
        fun `check is showProduct gave list of product`()
        {

            assertEquals(4, populateProduct!!.numberOfProduct())

            assertFalse(populateProduct!!.showProduct().contains("No Orders stored"))
            assertTrue(populateProduct!!.showProduct().contains("iphone"))
            assertTrue(populateProduct!!.showProduct().contains("lenovo"))
            assertTrue(populateProduct!!.showProduct().contains("honor"))

        }

    }


    @Nested
    inner class ShowNotStoredProductsTest
    {
        @Test
        fun `is return No Inordered product if empty list`()
        {
            assertEquals(0, emptyProduct!!.numberOfNotOrderedProduct())
            assertTrue(emptyProduct!!.showNotOrderdProduct().contains("No Inordered product"))
        }

        @Test
        fun `check is showNotOrderdProduct gave list of not ordered product`()
        {

            assertEquals(2, populateProduct!!.numberOfNotOrderedProduct())


            assertTrue(populateProduct!!.showNotOrderdProduct().contains("-1"))
            assertFalse(populateProduct!!.showNotOrderdProduct().contains("3"))


        }

    }


    @Nested
    inner class showOrderdProduct
    {
        @Test
        fun `is return No Ordered product if empty list`()
        {
            assertEquals(0, emptyProduct!!.numberOfOrderedProduct())
            assertTrue(emptyProduct!!.showOrderdProduct().contains("No Ordered product"))
        }

        @Test
        fun `check is showOrderdProduct gave list of ordered product`()
        {

            assertEquals(2, populateProduct!!.numberOfOrderedProduct())


            assertTrue(populateProduct!!.showOrderdProduct().contains("1"))
            assertTrue(populateProduct!!.showOrderdProduct().contains("3"))
            assertFalse(populateProduct!!.showOrderdProduct().contains("-1"))


        }

    }

    @Nested
    inner class DeleteProductTest
    {
        @Test
        fun `is return false when u delete empty list`()
        {
            assertEquals(0, emptyProduct!!.numberOfProduct())
            assertFalse(emptyProduct!!.delatePtoduct(1))

        }

        @Test
        fun `check is deleteProduct Work`()
        {

            assertEquals(4, populateProduct!!.numberOfProduct())
            val result = populateProduct!!.delatePtoduct(3)

            assertTrue(result)
            assertEquals(3, populateProduct!!.numberOfProduct())
            val result2 = populateProduct!!.SerchByCriteria("id", 3)
            assertTrue(result2.isEmpty())


        }

        @Test
        fun `is return false when u  delete  product with wrong id `()
        {

            assertEquals(4, populateProduct!!.numberOfProduct())
            val result2 = populateProduct!!.SerchByCriteria("id", 4)
            assertTrue(result2.isEmpty())
            val result = populateProduct!!.delatePtoduct(4)
            assertFalse(result)


        }

    }

    @Nested
    inner class UpdateTest
    {
        @Test
        fun `is return false when u update empty list`()
        {
            assertEquals(0, emptyProduct!!.numberOfProduct())
            assertFalse(emptyProduct!!.updateProduct(1, "poko", 128, 233.6))

        }

        @Test
        fun `is return false when u  update  product with wrong id`()
        {
            val result1 = populateProduct!!.showByCriteria("id", 4)
            assertTrue(result1.contains("no product whith 4 element in id"))
            assertFalse(populateProduct!!.updateProduct(4, "poko", 128, 233.6))
            populateProduct!!.updateProduct(4, "poko", 128, 233.6)
            val result2 = populateProduct!!.showByCriteria("id", 4)

            assertTrue(result2.contains("no product whith 4 element in id"))


        }

        @Test
        fun `check is update order Work`()
        {
            val result1 = populateProduct!!.showByCriteria("id", 3)
            assertTrue(result1.contains("lenovo"))
            assertTrue(populateProduct!!.updateProduct(3, "poko", 128, 233.6))
            populateProduct!!.updateProduct(3, "poko", 128, 233.6)
            val result2 = populateProduct!!.showByCriteria("id", 3)

            assertTrue(result2.contains("poko"))


        }

    }


    @Nested
    inner class checkOfNumberTest
    {
        @Test
        fun `is return false when u checkOfNumber of  empty list`()
        {
            assertFalse(emptyProduct!!.checkOfNumberAllProduct())
            assertEquals(0, emptyProduct!!.numberOfProduct())
        }

        @Test
        fun `check is checkOfNumber order  Work`()
        {

            assertTrue(populateProduct!!.checkOfNumberAllProduct())
            assertEquals(4, populateProduct!!.numberOfProduct())

        }

    }

    @Nested
    inner class checkOfNumberNotStoredProduct
    {
        @Test
        fun `is return false when u run checkOfNumberNotStoredProduct of  empty list`()
        {
            assertFalse(emptyProduct!!.checkOfNumberNotStoredProduct())
            assertEquals(0, emptyProduct!!.numberOfNotOrderedProduct())
        }

        @Test
        fun `check is checkOfActiveNumber order  Work`()
        {

            assertTrue(populateProduct!!.checkOfNumberNotStoredProduct())
            assertEquals(2, populateProduct!!.numberOfNotOrderedProduct())

        }

    }

    @Nested
    inner class checkOfNumberStoredProductTest
    {
        @Test
        fun `is return false when u run checkOfNumberStoredProductTest of  empty list`()
        {
            assertFalse(emptyProduct!!.checkOfNumberStoredProduct())
            assertEquals(0, emptyProduct!!.numberOfProduct())
        }

        @Test
        fun `check is checkOfNumberStoredProductTest order  Work`()
        {

            assertTrue(populateProduct!!.checkOfNumberStoredProduct())
            assertEquals(2, populateProduct!!.numberOfOrderedProduct())

        }

    }


    @Nested
    inner class isValidIdAllTypeTest
    {
        @Test
        fun `is return false when u check Of IsValId of  empty list`()
        {
            assertFalse(emptyProduct!!.isValidID(1))

        }

        @Test
        fun `check  if checkOfIsValId product  Work`()
        {
            assertTrue(populateProduct!!.isValidID(1))
            val result1 = populateProduct!!.showByCriteria("id", 1)
            assertTrue(result1.contains("1"))

        }

        @Test
        fun `check if checkOfIsValId order  give false if u input wrong  id`()
        {
            assertFalse(populateProduct!!.isValidID(5))
            val result1 = populateProduct!!.SerchByCriteria("id", 5)
            assertTrue(result1.isEmpty())

        }


        @Test
        fun `is return false when u check isValidNotStoredProductID of  empty list`()
        {
            assertFalse(emptyProduct!!.isValidNotStoredProductID(1))

        }

        @Test
        fun `check is check Of isValidNotStoredProductID order  Work`()
        {
            assertTrue(populateProduct!!.isValidNotStoredProductID(1))
            val result1 = populateProduct!!.showByCriteria("id", 1)
            assertTrue(result1.contains("-1"))
        }

        @Test
        fun `check if isValidNotStoredProductID order  give false if u input wrong active id`()
        {
            assertFalse(populateProduct!!.isValidNotStoredProductID(3))
            val result1 = populateProduct!!.showByCriteria("id", 3)
            assertTrue(result1.contains("orderID=3"))

        }

    }

    @Nested
    inner class CheckIsValidIdAllTypeTest
    {
        @Test
        fun `is return false when u checkOfIsValId of  empty list`()
        {
            assertFalse(emptyProduct!!.checkIsValidProductID(1))

        }

        @Test
        fun `check is checkOfIsValId of product  Work`()
        {
            assertTrue(populateProduct!!.checkIsValidProductID(3))
            val result1 = populateProduct!!.showByCriteria("id", 3)
            assertTrue(result1.contains("3"))
        }

        @Test
        fun `check if checkOfIsValId of order give false when u ipunt  wrong id`()
        {
            assertFalse(populateProduct!!.checkIsValidProductID(5))
            val result1 = populateProduct!!.SerchByCriteria("id", 5)
            assertTrue(result1.isEmpty())
        }

        @Test
        fun `is return false when u checkIsValidNotStoredProductID of  empty list`()
        {
            assertFalse(emptyProduct!!.checkIsValidNotStoredProductID(1))

        }

        @Test
        fun `check if  checkIsValidNotStoredProductID of not stored product  Work`()
        {
            assertTrue(populateProduct!!.checkIsValidNotStoredProductID(1))
            val result1 = populateProduct!!.showByCriteria("id", 1)
            assertTrue(result1.contains("-1"))
        }

        @Test
        fun `check if checkIsValidNotStoredProductID of order give false when u ipunt  wrong  not stored product id`()
        {
            assertFalse(populateProduct!!.checkIsValidNotStoredProductID(3))
            val result1 = populateProduct!!.showByCriteria("id", 3)
            assertTrue(result1.contains("orderID=3"))
        }
    }

    @Nested
    inner class addProductToOrderTest
    {
        @Test
        fun `check is addProductToOrder work`()
        {
            assertFalse(populateProduct!!.checkIsOrderHasProducts(0))
            populateProduct!!.addProductToOrder(0,0)
            assertTrue(populateProduct!!.checkIsOrderHasProducts(0))
            val result1 = populateProduct!!.showByCriteria("id", 0)
            assertTrue(result1.contains("orderID=0"))
        }
        @Test
        fun `check is addProductToOrder give false if product already exist `()
        {


            val result1 = populateProduct!!.showByCriteria("id", 3)
            assertTrue(result1.contains("orderID=3"))
            assertFalse(populateProduct!!.addProductToOrder(3,0))
        }

    }

    @Nested
    inner class showProductByOrderTest{
        @Test
        fun `check is showProductByOrderTest work`()
        {
            assertTrue(populateProduct!!.checkIsOrderHasProducts(3))
            val result =populateProduct!!.showByCriteria("id", 3)
            assertTrue(result.contains("orderID=3"))
            assertTrue(result.contains("lenovo"))
            val result2 = populateProduct!!.showProductByOrder(3)
            assertTrue(result2.contains("lenovo"))
        }
        @Test
        fun `check is showProductByOrderTest give No stored product in order with id $seacrhOrderID if orderId wrong`()
        {
            assertFalse(populatedOrders!!.isValidID(5))

            val result2 = populateProduct!!.showProductByOrder(5)
            assertTrue(result2.contains("No stored product in order with id 5"))
        }
    }


    @Nested
    inner class countProductSumPriceInOrderTest{
        @Test
        fun `check is countProductSumPriceInOrder work`()
        {
            populateProduct!!.addProductToOrder(0,3)
            assertTrue(populateProduct!!.checkIsOrderHasProducts(3))
            val result =populateProduct!!.showByCriteria("id", 3)
            assertTrue(result.contains("orderID=3"))
            assertTrue(result.contains("lenovo"))
            assertTrue(result.contains("250.5"))
            val result3 =populateProduct!!.showByCriteria("id", 0)
            assertTrue(result3.contains("orderID=3"))
            assertTrue(result3.contains("iphone"))
            assertTrue(result.contains("250.5"))
            val result2 = populateProduct!!.showProductByOrder(3)
            assertTrue(result2.contains("lenovo"))
            assertTrue(result3.contains("iphone"))
            val result5 =populateProduct!!.countProductSumPriceInOrder(3)
            assertTrue(result5.contains("Sum of product price by order with id 3 = 501"))
        }

    }
}